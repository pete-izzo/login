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

        System.out.println("first");


       
        
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

        ArrayList<OrderInfo> newOrders = new ArrayList<OrderInfo>();

        //Get data from derby
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

            con = ds.getConnection();

            stmt = con.createStatement();

            /**
             * query the cust and order tables
             * not sure if necessary to order
             * since it's ordered at the bottom already
             */
            System.out.println("Before query"); 

            /**
             * The Query
             * connects both tables at cust_id so customers names 
             * aren't duplicated for each order
             */
            String sql = "SELECT o.*, c.cust_name" +
                         " FROM orders o, customers c" +
                         " WHERE o.cust_id = c.cust_id";

            System.out.println("After query"); 
            
            rs = stmt.executeQuery(sql);

            
            while(rs.next()) {

                /**Creates new Order object
                 * sets all the necessary data from the query and
                 * adds each object to the "newOrders" array list
                 */
                OrderInfo orders = new OrderInfo();

                orders.setOrderID(rs.getInt("order_id"));

                orders.setCustomerName(rs.getString("cust_name"));
                
                orders.setOrderDate(rs.getDate("order_date"));

                orders.setDescription(rs.getString("order_desc"));              

                newOrders.add(orders);
            }

            //Print DB info to page to make sure it's connected

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

        /**
         * Sorts all order objects by date ascending
         */
        Collections.sort(newOrders, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        });

        System.out.println("after sort");

        session.setAttribute("cooldata", newOrders);

        response.sendRedirect ("home.jsp");

    }

    /**
     * Post for form submission
     * to edit query?
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            HttpSession session = request.getSession(false);

            String myUserName = (String)session.getAttribute("name");

            String isLoggedIn = (String)session.getAttribute("logged");


            /**
             * db query needs to be updated with 
             * new query and then saved to session so
             * home.jsp can post it
             */
            session.setAttribute("newDBQuery", newDBQuery)
    
    }
}