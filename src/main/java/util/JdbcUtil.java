package util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
/**
 * @author xies
 * @date 2017/11/16
 */

public class JdbcUtil {
	private static DataSource ds=null;
	private static Properties properties=new Properties();
	
	static{
		try {
			//FileInputStream is=new FileInputStream("db.properties");
			properties.setProperty("driverClassName","org.postgresql.Driver");
			properties.setProperty("url","jdbc:postgresql://localhost:5432/db");
			properties.setProperty("username","postgres");
			properties.setProperty("password","123456");
			ds= BasicDataSourceFactory.createDataSource(properties);

		} catch (Exception e) {
			e.printStackTrace();
		}
		}

	/**
	 *
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}

	/**
	 *
	 * @param conn
	 */
	public static void closeConnection(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void startTransaction(Connection conn){
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void commitTransaction(Connection conn){
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollBack(Connection conn){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//测试
//	public static void main(String[] args){
//		try {
//			Connection conn= JdbcUtil.getConnection();
//
//			if(conn!=null){
//				System.out.println("连接成功");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
}
