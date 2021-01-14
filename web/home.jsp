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

        <form action="HomeServlet" method="POST">

          <select name="dropDown">

            <option value="all">All Orders</option>
            <c:forEach items="${customerList}" var="items">
              <option value="${items.customerID}">${items.customerName}</option>
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

            </tbody>
        </table>  


        <br>
        <br>          
        <a href="./OrderEdit.jsp"><button>Submit New Order</button></a>
        <br>


        <h2>To edit a previous order please select the corresponding Order ID from the dropdown</h2>
        <hr>
        <!--Order edits-->
        <form action="HomeServlet" method="POST">

          <label for="editDropDown">Order ID</label>
          <select name="editDropDown">
            <c:forEach items="${cooldata}" var="item">
              <option value="${item.orderID}">${item.orderID}</option>
            </c:forEach>
          </select>

          <br>
          <label for="newOrderDate">Order Date</label>
          <input type="date" name="newOrderDate" id="orderDate" required>
          <br>
          <label for="newDescription">Order Description</label>
          <input type="text" name="newDescription" id="description" required>
          <input type="submit" value="Edit a previous order">


        </form>
  
      </body>



</html>
  