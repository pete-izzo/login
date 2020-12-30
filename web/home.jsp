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
      <title>Welcome Home</title>
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
  
        <h2>Brought to you by: <c:out value="${DBProductInfo}" /></h2>

        <table>
            <thead>
              <tr>
                <th colspan="4">Order Info</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <th>Order ID</th>
                <th>Customer Name</th>
                <th>Order Date</th>
                <th>Description</th>
              </tr>
                <c:forEach begin="0" var="outer" end="${cooldata.size()-1}" varStatus="loop1">
                  <tr>

                      <th>${cooldata[loop1.index].getOrderID()}</th>
                  
                          <th>${cooldata[loop1.index].getCustomerName()}</th>

                          <th>${cooldata[loop1.index].getOrderDate()}</th>

                          <th>${cooldata[loop1.index].getDescription()}</th>
                  </tr>
                </c:forEach>
                

            </tbody>
        </table>  
      </body>



</html>
  