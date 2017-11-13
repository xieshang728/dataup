package entity;

/**
 * @author xies
 * @date 2017/11/9
 */
public class ImportInformation {
    private String tableName;
    private float time;
    private int importRecords;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getImportRecords() {
        return importRecords;
    }

    public void setImportRecords(int importRecords) {
        this.importRecords = importRecords;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
