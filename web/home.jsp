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

                        <!--EDIT ITEMS-->
                        <td>
                          <a href="<c:url value='OrderServlet'>
                                <c:param name="orderID" value="${item.orderID}"/>
                                </c:url>">Edit
                          </a>
                        </td>

                        <!--DELETE ITEMS-->
                        <td>
                          <a href="<c:url value='OrderServlet'>
                          <c:param name="orderID" value="${item.orderID}"/>
                          <c:param name="delOrderID" value="1"/>

                          </c:url>">Delete
                          </a>
                        </td>

                      
                  </tr>
                </c:forEach>                

            </tbody>
        </table>  

        
        <br>
        <br>          
        <a href="OrderServlet"><button>Submit New Order</button></a>
        <br>
  
      </body>



</html>
  