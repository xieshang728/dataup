package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import entity.ImportInformation;
import service.impl.DataServiceImpl;
import util.MetaDataUtil;
/**
 * Created by Administrator on 2017/11/8.
 * @author xies
 */
public class ImportServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableName="cdr_ib_detail";
        ImportInformation importInformation= new  DataServiceImpl().importData(tableName,1);
        request.setAttribute("importInformation",importInformation);
        request.getRequestDispatcher("/importInformation.jsp").forward(request,response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
