// Java program to display logged in user's db details

package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import java.lang.*;
import static java.lang.System.*;
import java.lang.Object;
import java.util.Collections;
import static java.util.Comparator.comparing;
import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
// Class of Servlet/
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       
        String userText = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        //Retrieve session variables
        String myUserName = (String)session.getAttribute("name");

        String isLoggedIn = (String)session.getAttribute("logged");


       
        
        /**
         * /////////////
         * DB INFO
         * /////////////
         */

        String dbURL ="java:comp/env/jdbc/NewDBTest";

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        Context ctx = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet newrs = null;

        

        ArrayList<Object> orderID = new ArrayList<Object>();
        ArrayList<Object> custName = new ArrayList<Object>();
        ArrayList<Object> orderDate = new ArrayList<Object>();
        ArrayList<Object> description = new ArrayList<Object>();

        ArrayList<OrderInfo> newOrders = new ArrayList<OrderInfo>();





        //Get data from derby
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

            con = ds.getConnection();

            stmt = con.createStatement();

            String sql = "SELECT orders.*, customers.*" +
                         " FROM orders, customers" +
                         " INNER JOIN orders ON customers.cust_id = orders.cust_id";
            
            rs = stmt.executeQuery(sql);
            // st.close();
            // stmt = con.createStatement();
    
            // rs = stmt.executeQuery("SELECT * FROM USERS");

            while(rs.next()) {
                OrderInfo orders = new OrderInfo();

                // if(!newOrders.contains(rs.getInt("order_id"))) {
                //     orders.setOrderID(rs.getInt("order_id"));
                // } 
                // if(!newOrders.contains(rs.getString("cust_name"))) {
                //     orders.setCustomerName(rs.getString("cust_name"));   
                // } 
                // if(!newOrders.contains(rs.getDate("order_date"))) {
                //     orders.setOrderDate(rs.getDate("order_date"));   
                // } 
                // if(!newOrders.contains(rs.getString("order_desc"))) {
                //     orders.setDescription(rs.getString("order_desc"));   
                // } 

                orders.setOrderID(rs.getInt("order_id"));

                if (rs.getString("cust_name") == null) {
                    orders.setCustomerName(" ");
                    return;
                } else {
                    orders.setCustomerName(rs.getString("cust_name"));
                }
                
                orders.setOrderDate(rs.getDate("order_date"));
                orders.setDescription(rs.getString("order_desc"));   
   

                
                

                newOrders.add(orders);


                // orderID.add(rs.getInt("order_id"));
                // orderDate.add(rs.getDate("order_date"));
                // description.add(rs.getString("order_desc"));
                // custName.add(rs.getString("cust_name"));
            }

            // newrs = stmt.executeQuery("select cust_name from customers");


            // while(newrs.next()) {
            //     custName.add(newrs.getString("cust_name"));
            // }

            //try to print DB info

            String productInfo = con.getMetaData().getDatabaseProductName();

            session.setAttribute("DBProductInfo", productInfo);

            
        } catch (NamingException ex) {

            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                // newrs.close();
                stmt.close();
                con.close();
                ctx.close();


    
            }catch (SQLException error) {
                error.printStackTrace();
            }catch (NamingException error) {
                error.printStackTrace();
            }
        }

        Collections.sort(newOrders, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        });

        session.setAttribute("cooldata", newOrders);


        session.setAttribute("orderID", orderID);
        session.setAttribute("custName", custName);
        session.setAttribute("orderDate", orderDate);
        session.setAttribute("description", description);

        response.sendRedirect ("home.jsp");



        // Collections.sort(orderInfo, new Comparator<OrderInfo>() {
        //     @Override
        //     public int compare(Person p1, Person p2){
        //         return p1.getIndex().compareTo(p2.getIndex());
        //     }
        // });

    }
}