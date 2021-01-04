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

      <!--JQUERY NEEDED TO UPDATE PAGE DYNAMICALLY-->
      <script src="http://code.jquery.com/jquery-latest.min.js"></script>

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

        <form action="HomeServlet" method="POST">

          <input type="hidden" name="selectedValue" value=""/>
          <select name="dropDown">

            <option value="1">All Orders</option>
            <c:forEach items="${masterList}" var="items">
              <option value="${items.customerName}">${items.customerName}</option>
            </c:forEach>
          </select>

          <input type="submit" value="Submit" />

        </form>
        
        

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
                <c:forEach items="${cooldata}" var="item">
                  <tr>

                      <td>${item.orderID}</td>
                  
                      <td>${item.customerName}</td>

                      <td>${item.orderDate}</td>

                      <td>${item.description}</td>
                  </tr>
                </c:forEach>

                <c:forEach items="${custOrders}" var="item">
                  <tr>

                      <td>${item.orderID}</td>
                  
                      <td>${item.customerName}</td>

                      <td>${item.orderDate}</td>

                      <td>${item.description}</td>
                  </tr>
                </c:forEach>
                

            </tbody>
        </table>  
      </body>



</html>
  