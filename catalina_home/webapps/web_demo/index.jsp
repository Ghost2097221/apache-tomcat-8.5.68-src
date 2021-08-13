<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>测试页面</title>
</head>
<body>
<%
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateStr = dateFormat.format(new Date());
%>
This is index.jsp

<br/>
<%= dateStr %>
</body>
</html>