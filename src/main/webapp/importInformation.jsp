<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/9
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    表名为:${requestScope.importInformation.tableName}<br>
    成功导入记录:${requestScope.importInformation.importRecords}条<br>
    导入花费时间为:${requestScope.importInformation.time}<br>
</body>
</html>
