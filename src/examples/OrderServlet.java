// Java program to verify a users login credentials

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
 
// Class of OrderServlet/
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PrintWriter out = response.getWriter();

        
        String customer = request.getParameter("customerChoice");
        String orderDate = request.getParameter("order_date");
        String description = request.getParameter("orderDescription");

        System.out.println(customer);
        System.out.println(orderDate);
        System.out.println(description);



        /**
         * /////////////
         * DB INFO
         * /////////////
         */

         /*
        String dbURL ="java:comp/env/jdbc/NewDBTest";

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        Context ctx = null;
        Connection con = null;
        Statement stmt = null;
        Statement addOrderStatement = null;
        ResultSet rs = null;
        ResultSet resultset = null;
        ResultSet addOrderResult = null;
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


            // Add new order to DB
            try {


                addOrder =  "INSERT INTO orders (cust_id, order_date, order_desc)" +
                            " VALUES(LAST_INSERT_ID(), CURRENT_DATE, ?)";
                PreparedStatement insertOrder = con.prepareStatement(addOrder);
                insertOrder.setString(1, insertOrderDescription);


                insertCustomer.executeUpdate();
                insertCustomer.close();
                insertOrder.executeUpdate();
                insertOrder.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            // END OF ADDING NEW ORDER

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
        
        */


        response.sendRedirect ("./home.jsp");


    }
}