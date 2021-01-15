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
        HttpSession session = request.getSession(false);

        String customer = request.getParameter("customerChoice");
        session.setAttribute("customerChoice", customer);

        System.out.println("customerChoice: "+ customer);

        String editOrderIDString = request.getParameter("editOrderID");
        session.setAttribute("editOrderIDString", editOrderIDString);

        System.out.println("EditOderIDString: " + editOrderIDString);

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
        String addOrder = null;


        /////////////////////////////////
        // START ORDER EDIT CODE
        /////////////////////////////////
        if (editOrderIDString != null){
            int editOrderID = Integer.parseInt(editOrderIDString);
            String newDate = request.getParameter("editOrderDate");
            Date editDate = java.sql.Date.valueOf(newDate);
            String custName = request.getParameter("editCustomerName");
            String editDescription = request.getParameter("editOrderDescription");

            //
            session.setAttribute("custName", custName);
            session.setAttribute("editOrderID", editOrderID);
            session.setAttribute("newDate", newDate);
            session.setAttribute("editDescription", editDescription);

            // String updateOrder = "UPDATE orders" +
            // " SET order_date = ?" +
            // ", order_desc = ?" +
            // " WHERE order_id = ?";

            // PreparedStatement editOrder = con.prepareStatement(updateOrder);
            // editOrder.setDate(1, newOrderDate);
            // editOrder.setString(2, newDescription);
            // editOrder.setInt(3, orderIDToEdit);

            // editOrder.executeUpdate();
            // editOrder.close();

            // editOrderStatement = con.createStatement();
            // editOrderStatement.execute(updateOrder);


            System.out.println(editOrderID);
            System.out.println(editDate);
            System.out.println(editDescription);
    
        } else {
            System.out.println(editOrderIDString);
        }

        /////////////////////////////////////
        // ADD NEW ORDER
        /////////////////////////////////////
        if(customer != null) {
            int custID = Integer.parseInt(customer);
            String orderDate = request.getParameter("order_date");
            Date date = java.sql.Date.valueOf(orderDate);
            String description = request.getParameter("orderDescription");
    

            try {
                System.out.println("start try-catch");
    
                ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");
    
                con = ds.getConnection();
    
                stmt = con.createStatement();
                // Add new order to DB
                // regular insert stmt
                //works but not secure
    
                // addOrder =  "INSERT INTO orders " +
                //             "VALUES " +
                //             " (DEFAULT, " + custID + ", CURRENT_DATE, '" + description + "')";
                // System.out.println(addOrder);
    
                // stmt.execute(addOrder);
    
                /**
                 *  
                 * //////////////////
                 * PREPARED STATEMENT
                 * to insert new orders into orders
                 * table
                 * //////////////////
                 * 
                 */
                addOrder =  "INSERT INTO orders (cust_id, order_date, order_desc) VALUES (?, ?, ?)";
                PreparedStatement insertOrder = con.prepareStatement(addOrder);
    
                insertOrder.setInt(1, custID);
                insertOrder.setDate(2, date);
                insertOrder.setString(3, description);
    
                insertOrder.executeUpdate();
                insertOrder.close();             
    
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
    

        }


        response.sendRedirect ("OrderEdit.jsp");


    }

    /**
     * //////////////////////////
     * Get method for Order Edits
     * //////////////////////////
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String editOrderIDString = request.getParameter("editOrderID");
        session.setAttribute("editOrderIDString", editOrderIDString);
        System.out.println("EditOderIDString: " + editOrderIDString);

        response.sendRedirect ("OrderEdit.jsp");
    }
}