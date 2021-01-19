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
import java.util.ArrayList;
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

        String newDate = request.getParameter("editOrderDate");
        String newOrderDate = request.getParameter("newOrderDate");


        String editOrderIDString = (String)session.getAttribute("editOrderIDString");


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

        try {

            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

            con = ds.getConnection();

            stmt = con.createStatement();

            /////////////////////////////////
            // START ORDER EDIT CODE
            /////////////////////////////////


            if (newOrderDate != null){
                Date editOrderDate = java.sql.Date.valueOf(newOrderDate);
                String editDescription = request.getParameter("editOrderDescription");
                int customerOrderID = (int)session.getAttribute("editOrderID");



                String updateOrder = "UPDATE orders" +
                " SET order_date = ?" +
                ", order_desc = ?" +
                " WHERE order_id = ?";

                PreparedStatement editOrder = con.prepareStatement(updateOrder);
                editOrder.setDate(1, editOrderDate);
                editOrder.setString(2, editDescription);
                editOrder.setInt(3, customerOrderID);

                editOrder.executeUpdate();
                editOrder.close();

                stmt = con.createStatement();
                stmt.execute(updateOrder);

        
            }

            /////////////////////////////////////
            // ADD NEW ORDER
            /////////////////////////////////////
            if(customer != null) {
                int custID = Integer.parseInt(customer);
                String orderDate = request.getParameter("order_date");
                Date date = java.sql.Date.valueOf(orderDate);
                String description = request.getParameter("orderDescription");
        
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
            }

    

    
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

            /**
             * Reset 'editOrderIDString' to null so you can add new 
             * orders after editing one
             */
            editOrderIDString = null;
            session.setAttribute("editOrderIDString", editOrderIDString);
    
        //send to HomeServlet so whole db will be
        //queried and results will show on screen
        response.sendRedirect ("HomeServlet");


    }

    /**
     * //////////////////////////
     * Get method for Order Edits
     * //////////////////////////
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);


        ///////////STUFF BELOW HERE IS OLD IDK IF USABLE \\\\\\\\\\\\\\\\\\
        String editOrderIDString = request.getParameter("editOrderID");
        // only save edit variables if edit button pressed
        if(editOrderIDString != null) {
            session.setAttribute("editOrderIDString", editOrderIDString);

            int editOrderID = Integer.parseInt(editOrderIDString);
            String newDate = request.getParameter("editOrderDate");
            String custName = request.getParameter("editCustomerName");
            String editDescription = request.getParameter("editOrderDescription");
                
            
            session.setAttribute("custName", custName);
            session.setAttribute("editOrderID", editOrderID);
            session.setAttribute("newDate", newDate);
            session.setAttribute("editDescription", editDescription);
    
        };
        /////////////////// END UNUSABLE CODE \\\\\\\\\\\\\\\\\\\\\

        //Edit Link ID
        String orderIDString = request.getParameter("orderID");
        int orderIDInt = Integer.parseInt(orderIDString);
        String del = request.getParameter("delOrderID");


        session.setAttribute("orderID", orderIDString);
        session.setAttribute("del", del);


        // delOrderID not needed, just a repeat of orderID
        // instead set to a string of true to go between delete and not
        //Delete Link ID
        // String delIDString = request.getParameter("delOrderID");
        // if(delIDString != null){
        //     int delIDInt = Integer.parseInt(delIDString);
        // };
        // String delOrderString = request.getParameter("delOrderID");
        // session.setAttribute("delOrderID", delOrderString);

        //Gets newOrders ArrayList from session to iterate over to get the specific order
        //for whichever edit is clicked
        ArrayList<OrderInfo> newOrders = (ArrayList)session.getAttribute("cooldata");

        //Grabs only the "orders" obj inside "newOrders" that has a matching id as the
        //one of the clicked edit button
        for(OrderInfo orders : newOrders){

            if(orders.getOrderID() == orderIDInt) {
                System.out.println("newOrders contains: " + orders.getOrderID() + 
                                    " " + orders.getCustomerName() +
                                    " " + orders.getOrderDate() +
                                    " " + orders.getDescription());

                    /*
                    * \\\\\\\\\\\\\\\\\\\
                    *   save this order to session so you can mess with it
                    *   or at least save its values to session to display them
                    *   in the event that more attributes are saved than displayed on the
                    *   home screen
                    * \\\\\\\\\\\\\\\\\\\\
                    */

                    session.setAttribute("orders", orders);

            }


        };


        response.sendRedirect ("OrderEdit.jsp");
    }
}