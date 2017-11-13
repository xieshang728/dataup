<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/9
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    表名为:${requestScope.exportInformation.tableName}<br>
    表总记录数为:${requestScope.exportInformation.totalRecords}条<br>
    成功导出记录:${requestScope.exportInformation.exportRecords}条<br>
    未成功导出id有:${requestScope.exportInformation.list}<br>
    导出花费时间:${requestScope.exportInformation.time}<br>


</body>
</html>
