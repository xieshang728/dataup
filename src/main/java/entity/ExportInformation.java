package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xies
 * @date 2017/11/9
 */
public class ExportInformation {
    private float time;
    private int totalRecords;
    private int exportRecords;
    private String tableName;
    private List<Integer> list=new ArrayList<Integer>();

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getExportRecords() {
        return exportRecords;
    }

    public void setExportRecords(int exportRecords) {
        this.exportRecords = exportRecords;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
