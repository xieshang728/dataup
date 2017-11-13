package service;

import entity.ExportInformation;
import entity.ImportInformation;

/**
 *@author xies
 *@date 2017/11/9
 */
public interface DataService{
        /**
         * 导入数据
         * @param tableName
         * @param type
         * @return ImportInformation
         */
        public ImportInformation importData(String tableName, int type);

        /**
         * 导出数据
         * @param tableName
         * @param type
         * @return ExportInformation
         */
        public ExportInformation exportData(String tableName, int type);
}
