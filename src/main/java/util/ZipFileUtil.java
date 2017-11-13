package util;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/11/13.
 * @author xies
 * @date 2017/11/13
 */
public class ZipFileUtil {
    public static void main(String[] args){
        //ZipFileUtil.zipIn("cdr_ib_detail_3");
        System.out.println("112");
        ZipFileUtil.zipOut("cdr_ib_detail");
    }
    public static void zipIn(String tableName){
        try {
            File file=new File("D:/"+tableName+".sql");
            FileInputStream fis = new FileInputStream("D:/"+tableName+".sql");
            BufferedInputStream bis=new BufferedInputStream(fis);
            ZipOutputStream zos=new ZipOutputStream(new FileOutputStream("D:/"+tableName+".zip"));
            BufferedOutputStream bos=new BufferedOutputStream(zos);
            zos.putNextEntry(new ZipEntry(tableName+".sql"));
            byte[] b=new byte[1024];
            while(true){
                int len=bis.read(b);
                if(len==-1){
                    break;
                }
                bos.write(b);
            }
            if(file.exists()){
                file.delete();
            }
            fis.close();
            bos.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void zipOut(String tableName){
        try {
            File zipFile=new File("D:"+File.separator+tableName+".zip");
            File pathFile=new File("D:"+File.separator+tableName);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile);
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                String outPath = ("D:"+File.separator+tableName+File.separator +zipEntryName).replaceAll("\\*", "/");
                File file = new File(tableName);
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                System.out.println(outPath);

                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
