package service.impl;

import entity.ExportInformation;
import service.DataService;
import util.MetaDataUtil;
import entity.ImportInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xies
 * @date 2017/11/9
 */
public class DataServiceImpl implements DataService{
    private final static int PAGESIZE=1000000;
    @Override
    public ExportInformation exportData(String tableName, int type) {
        long startTime=System.currentTimeMillis();
        int counts=MetaDataUtil.getRows(tableName);
        List<Integer> list=new ArrayList<>();
        ExportInformation exportInformation=new ExportInformation();
        int pageSize=1000000;
        int pageIndex=0;
        int k=counts/PAGESIZE+1;
        System.out.println("k: "+k);
        for(int i=1;i<k;i++) {
            MetaDataUtil.exportSql(tableName, type, PAGESIZE);
        }
        int h=MetaDataUtil.getRows(tableName)%PAGESIZE;
        list=MetaDataUtil.exportSql(tableName,type,h);
        long endTime = System.currentTimeMillis();
        float time=(float)((endTime-startTime)/1000.0);
        exportInformation.setTableName(tableName);
        exportInformation.setTotalRecords(counts);
        exportInformation.setExportRecords(counts-MetaDataUtil.getRows(tableName));
        exportInformation.setList(list);
        exportInformation.setTime(time);
        return exportInformation;

    }

    @Override
    public ImportInformation importData(String tableName, int type) {
        return MetaDataUtil.importSql(tableName,PAGESIZE);
    }
}
