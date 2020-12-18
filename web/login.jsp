<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>



<html>
    <head>
      <title>Login</title>
    </head>
  
    <body bgcolor=white>
  
    <form action="LoginServlet" method="POST">

        <h3>Please enter your credentials then click login:</h3>
        <input type="text" required placeholder="Your Username" name="username" id="username">
        <br> 
        <input type="password" required placeholder="Your Password" name="password" id="password">
        
        <input type="submit" value="Submit"> 

    <!--PLACE MESSAGE HERE IF LOGIN INFO IS INCORRECT-->
    </form>
        <h3 style="color:red"><em><c:out value="${invalid}"/></em></h3>

    </body>
</html>
  