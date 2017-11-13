package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import entity.ExportInformation;
import service.impl.DataServiceImpl;
/**
 * Created by Administrator on 2017/11/8.
 * @author xies
 */
public class ExportServlet extends javax.servlet.http.HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableName="cdr_ib_detail";
        int type=Integer.valueOf(request.getParameter("type"));
        ExportInformation exportInformation=new DataServiceImpl().exportData(tableName,type);
        request.setAttribute("exportInformation",exportInformation);
        request.getRequestDispatcher("/exportInformation.jsp").forward(request,response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
