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

        // Dropdown choice
        String choice =  request.getParameter("dropDown");

        /**
         * New Query
         * queries and displays info based on dropdown selection
         */

        String newQuery = null;

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
        Statement statement = null;
        ResultSet rs = null;
        ResultSet resultset = null;
        String sql;
        String customerQuery;

        //list of customers for dropdown
        ArrayList<CustomerInfo> customerList = new ArrayList<CustomerInfo>();
        //order info
        ArrayList<OrderInfo> newOrders = new ArrayList<OrderInfo>();

        //Get data from derby
        try {

            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

            con = ds.getConnection();

            stmt = con.createStatement();
            statement = con.createStatement();
            newOrders.clear();

            customerQuery = "SELECT * FROM customers";

            resultset = statement.executeQuery(customerQuery);

            while(resultset.next()) {

                /**
                 * create customer list for drop down
                 */

                CustomerInfo customers = new CustomerInfo();

                customers.setCustomerName(resultset.getString("cust_name"));
                customers.setCustomerID(resultset.getInt("cust_id"));

                customerList.add(customers);

            }
            /**
             * Need to use ".equals()" here because
             * simply using "==" means the value is in the same
             * address in memory. Which it isn't.
             */
            if (choice == null || choice.equals("all")) {

                /**
                 * The Query
                 * connects both tables at cust_id so customers names 
                 * aren't duplicated for each order
                 */

                sql = "SELECT o.*, c.cust_name" +
                " FROM orders o, customers c" +
                " WHERE o.cust_id = c.cust_id";

                rs = stmt.executeQuery(sql);

            } else {

                sql = "SELECT o.*, c.cust_name" +
                " FROM orders o, customers c" +
                " WHERE o.cust_id = c.cust_id" +
                " AND c.cust_id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, choice);
                rs = preparedStatement.executeQuery();

            }
            
            while(rs.next()) {
    
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
                resultset.close();
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
         * Sorts all order array lists by date ascending
         */
        Collections.sort(newOrders, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        });

        session.setAttribute("cooldata", newOrders);
        session.setAttribute("customerList", customerList);

        response.sendRedirect ("home.jsp");
    }

    /**
     * //////////////////////////
     * Post method for dropdown selection
     * //////////////////////////
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request,response);

        //refreshes current page instead of sending elsewhere
        response.setHeader("Refresh", "0; URL=/login/home.jsp"); 
    }
}