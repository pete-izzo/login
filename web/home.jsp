<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>



<html>
    <head>
      <title>Welcome Home</title>
    </head>
  
    <body bgcolor=white>
  
    <h1>Welcome <c:out value="${name}" /></h1>

    <h2>Brought to you by: <c:out value="${DBProductInfo}" /></h2>


    </body>
</html>
  