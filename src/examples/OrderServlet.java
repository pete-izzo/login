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
        int custID = Integer.parseInt(customer);
        String orderDate = request.getParameter("order_date");
        Date date = java.sql.Date.valueOf(orderDate);
        String description = request.getParameter("orderDescription");

        System.out.println(customer);
        System.out.println(orderDate);
        System.out.println(description);
        System.out.println(date);




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
        String addOrder = null;


        try {
            System.out.println("start try-catch");

            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

            con = ds.getConnection();

            stmt = con.createStatement();
            // Add new order to DB
            System.out.println("default");

            // addOrder =  "INSERT INTO orders " +
            //             "VALUES " +
            //             " (DEFAULT, " + custID + ", CURRENT_DATE, '" + description + "')";
            // System.out.println(addOrder);

            // stmt.execute(addOrder);

            /**
             *  
             * //////////////////
             * PREPARED STATEMENT
             * //////////////////
             * 
             */
            addOrder =  "INSERT INTO orders (cust_id, order_date, order_desc) VALUES (?, ?, ?)";
            PreparedStatement insertOrder = con.prepareStatement(addOrder);
            System.out.println("prepared stmt created");

            insertOrder.setInt(1, custID);
            System.out.println("cust ID set");

            //This when formatted into a prepared stmt isnt working
            insertOrder.setDate(2, date);
            System.out.println("order date set");

            insertOrder.setString(3, description);
            System.out.println("description set");

            insertOrder.executeUpdate();
            insertOrder.close();

            System.out.println("closed");
             

            // END OF ADDING NEW ORDER

        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
                ctx.close();

            }catch (SQLException error) {
                error.printStackTrace();
            }catch (NamingException error) {
                error.printStackTrace();
            }
        }


        response.sendRedirect ("HomeServlet");


    }
}