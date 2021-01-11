<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>


<html>
  <style type="text/css">
    table, th, td {
      border: 1px solid black;
    }
  </style>
    <head>
      <title>Add an Order</title>  

    </head>

    <%
    String uname = (String) session.getAttribute("name");
    String isLogged = (String) session.getAttribute("logged");

    if (null == uname || isLogged == "false") {
    session.setAttribute("errorMessage", "Login Failed ");
    response.sendRedirect("login.jsp");
    }
    %>
      
      <body bgcolor=white>
    
        <h1>Welcome <c:out value="${name}" /></h1>
  
        <h2>Add an order here!</h2>

        <form action="OrderServlet" method="post">
            <select name="customerChoice">
                <c:forEach items="${customerList}" var="items">
                    <option value="${items.customerID}">${items.customerName} ${items.customerID}</option>
                </c:forEach>
            </select>

            <label for="date">Order Date: </label><br>
            <input type="date" required placeholder="2019-12-25" name="order_date" id="order_date">
            <br>
            <label for="description">Order Description:</label><br>
            <input type="text" required placeholder="What was ordered?" name="orderDescription" id="orderDescription">
            <br> 
       
      

            <input type="submit" value="Submit" />


        </form>


  
      </body>



</html>
  