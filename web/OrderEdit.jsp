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

        <!--orderID set in home.jsp saved in servlet from doGet and passed to this page-->
        <c:if test="${sessionScope.orderID != null && sessionScope.del != '1'}">
          <h1>Choose a new date and type a new description to edit the order below</h1>

          <table>
            <thead>
              <tr>
                <th colspan="4">Edit Order Info</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <th><c:out value="${orders.getOrderID()}"/></th>
                <th><c:out value="${orders.getCustomerName()}"/></th>
                <th><c:out value="${orders.getOrderDate()}"/></th>
                <th><c:out value="${orders.getDescription()}"/></th>
              </tr>
                  <tr>
                      <form action="OrderServlet" method="POST">

                        <td></td>
                        <td></td>
                        <td><input type="date" name="newOrderDate" id="orderDate" required></td>
                        <td><input type="text" name="editOrderDescription" value="${item.description}"></td>
                        <td><input type="submit" value="Save"/></td>

                      </form>
                      
                  </tr>

            </tbody>
          </table>  



        </c:if>

        <!--END EDIT ORDER SECTION-->

        <!--DELETE SECTION-->
        <c:if test="${sessionScope.del == '1'}">

          <h1>Are you sure you would like to delete:</h1>
          <table>
            <thead>
              <tr>
                <th colspan="4">Order Info</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <th><c:out value="${orders.getOrderID()}"/></th>
                <th><c:out value="${orders.getCustomerName()}"/></th>
                <th><c:out value="${orders.getOrderDate()}"/></th>
                <th><c:out value="${orders.getDescription()}"/></th>
              </tr>
            </tbody>
          </table>  

          <br>
          <form action="OrderServlet" method="POST">
            <td><input type="hidden" name="delete" value="delete"></td>

            <input type="submit" value="Yes"/>

          </form>
          <a href="HomeServlet">Back Home</a>



        </c:if>

        <!--END DELETE SECTION-->



        <!--ADD ORDER SECTION-->
        <c:if test="${sessionScope.del == null && sessionScope.orderID == null}">
          <h2>Add an order for...</h2>

          <form action="OrderServlet" method="post">
              <select name="customerChoice">
                  <c:forEach items="${customerList}" var="items">
                      <option value="${items.customerID}">${items.customerName} ${items.customerID}</option>
                  </c:forEach>
              </select>
  
              <br>
              <br>
              <label for="date">Order Date: </label><br>
              <input type="date" required name="order_date" id="order_date">
              <br>
              <label for="description">Order Description:</label><br>
              <input type="text" required placeholder="What was ordered?" name="orderDescription" id="orderDescription">
              <br> 
  
              <input type="submit" value="Submit" />
              <a href="HomeServlet">Back Home</a>

          </form>
  
        </c:if>
        <!--END ADD ORDER-->

  
      </body>



</html>
  