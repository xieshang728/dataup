package util;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.ExportInformation;
import entity.ImportInformation;

/**
 * @author xieshang
 * @date 2017/11/7
 */
public class MetaDataUtil {
    private static Connection conn;
    private static DatabaseMetaData dataMeta;
    private static boolean pause=true;

    static {
        try {
            conn = JdbcUtil.getConnection();
            dataMeta = conn.getMetaData();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取表中的总列数
     */
    public static int getColumns(String tableName) {
        try {
            ResultSet rs = dataMeta.getColumns(null, null, tableName, "%");
            String sql2 = new String("select *from " + tableName);

            int counts = 0;
            while (rs.next()) {
                counts++;
            }
            return counts;
        } catch (Exception e) {
            System.out.println("出现异常，无法获取列数");
        }
        return -1;
    }

    /**
     * 获取表中的总行数
     */
    public static int getRows(String tableName) {
        long startTime = System.currentTimeMillis();
        int count = -1;
        try {
            PreparedStatement ps = conn.prepareStatement("select count(id) from " + tableName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("获取表中总记录花费时间为: " + (endTime - startTime)/1000.0+"秒");
        return count;

    }

    /**
     * 获取表的sql前缀
     */
    public static String getPrefix(String tableName, int counts) {
        String sql = "insert into " + tableName + "(";
        ResultSet rs = null;
        try {
            rs = dataMeta.getColumns(null, null, tableName, "%");
            int count = 0;
            while (rs.next()) {
                count++;
                sql += rs.getObject("column_name") + "";
                if (count < counts) {
                    sql += ",";
                }
            }
            sql += ") values";
            return sql;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param tableName
     * @param type
     */
    public static List<Integer> exportSql(String tableName,int type,int pageSize) {
        int counts=pageSize;
        int count=MetaDataUtil.getColumns(tableName);
        int total=counts;
        pause=((type==1)?true:false);
        Connection conn = null;
        FileWriter fileWriter = null;
        ResultSet rs2 = null;
        ResultSetMetaData rsd = null;
        Statement statement=null;
        int pauseId=-1;
        StringBuffer sb = new StringBuffer("123");
        //File file = new File("D:/" + tableName + ".sql");
        List<Integer> list = new ArrayList<Integer>();
        //获取结果集，创建文件流
        try {
            conn = JdbcUtil.getConnection();
            statement=conn.createStatement();
            fileWriter=new FileWriter("D:/"+tableName+".sql",true);
            ResultSet rs = dataMeta.getColumns(null, null, tableName, "%");
            String ps = new String("select *from "+tableName+" order by id limit "+pageSize+" offset 0");
            PreparedStatement pre = conn.prepareStatement(ps);
            rs2 = pre.executeQuery();
            rsd = rs2.getMetaData();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        //数据的导出,sql数据的格式化
        while (counts > 0 && pause) {
            try {
                sb.replace(0, sb.length(), "");
                while (rs2.next()) {
                    sb.append("(");
                    for (int i = 1; i <= count; i++) {
                        String str = rsd.getColumnTypeName(i);
                        if (rs2.getString(i) == null) {
                            sb.append("NULL,");
                        } else if ("timestamptz".equals(str) || ("varchar").equals(str) || ("jsonb".equals(str))) {
                            sb.append("'" + rs2.getString(i) + "',");
                        } else {
                            sb.append(rs2.getString(i) + ",");
                        }
                    }
                    //假设抛出异常,用于测试
                    if (rs2.getInt(1) == 500) {
                        throw new Exception();
                    }
                    if(rs2.getInt(1) == 675){
                        throw new Exception();
                    }
                    if(rs2.getInt(1)==7777){
                        throw new Exception();
                    }
                    if(rs2.getInt(1)==8888){
                        throw new Exception();
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    System.out.println(sb);
                    sb.append(");");
                    //写入sql,并换行
                    fileWriter.write(String.valueOf(sb));
                    fileWriter.write("\r\n");
                    pauseId=rs2.getInt(1);
                    sb.replace(0, sb.length(), "");
                    counts--;
                    if(!pause){
                        System.out.println("备份暂停……………………………………………………………………………………………………………………………………………………");
                        break;
                    }
                }
              //出现异常从下一条记录读取,并跳过出错行
            } catch (Exception e) {
                counts--;
                System.out.println("出现了异常……………………………………………………………………………………………………………………………………………………………………");
                try {
                    list.add(rs2.getInt(1));
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            if(!pause){
                System.out.println("数据备份暂停………………………………………………………………………………………………………………………………………………………………………");
                break;
            }
        }
        System.out.println("pauseId: "+pauseId);
        System.out.println("开始删除………………………………………………………………………………………………………………………………………………………………………………:");
        //数据的删除
        StringBuffer sbf = new StringBuffer("delete from " + tableName);
        if (list.size()>0) {
            sbf.append(" where id <= "+pauseId+" and id not in (");
            for (int i = 0; i < list.size(); i++) {
                System.out.println("出错行id为: " + list.get(i));
                sbf.append(list.get(i) + ",");
            }
            sbf.replace(sbf.length() - 1, sbf.length(), ")");
        }

        try {
            if(pauseId>-1) {
                PreparedStatement ps = conn.prepareStatement(String.valueOf(sbf));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            fileWriter.flush();
            fileWriter.close();
            System.out.println("备份完毕……………………………………………………………………………………………………………………");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 数据的导入
     * @param tableName
     */
    public static ImportInformation importSql(String tableName,int pageSize) {
        ImportInformation importInformation=new ImportInformation();
        long startTime=System.currentTimeMillis();
        int count = MetaDataUtil.getColumns(tableName);
        int total=MetaDataUtil.getRows(tableName);
        String sql = MetaDataUtil.getPrefix(tableName, count);
        System.out.println(sql);
        File file = new File("D:/" + tableName + ".sql");

        try {
            Statement statement = conn.createStatement();
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line = null;
            int sum=0;
            while ((line = br.readLine()) != null) {
                System.out.println(sql + " " + line);
                statement.addBatch(sql + " " + line);
                sum++;
                if(sum==pageSize){
                    statement.executeBatch();
                    sum=0;
                    statement=conn.createStatement();
                }
            }
            statement.executeBatch();
            System.out.println("数据导入完成………………………………………………………………………………………………………………………………");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("导入总共花费了: "+(endTime-startTime)/1000+"秒");
        importInformation.setTableName(tableName);
        importInformation.setImportRecords(MetaDataUtil.getRows(tableName)-total);
        importInformation.setTime((float)((endTime-startTime)/1000.0));
        return importInformation;
    }


    //public static void main(String[] args) {
        //测试数据导入
//        String tableName="cdr_ib_detail_1";
//		System.out.println(MetaDataUtil.getColumns(tableName));
//		int count=MetaDataUtil.getColumns(tableName);
//		String sql=MetaDataUtil.getPrefix(tableName, count);
//		int counts=MetaDataUtil.getRows(tableName);
//		MetaDataUtil.exportSql(tableName, count,counts);
        //测试数据导出
        //long startTime = System.currentTimeMillis();
       // MetaDataUtil.importSql("cdr_ib_detail_1");
        //long endTime = System.currentTimeMillis();
    //}
}
