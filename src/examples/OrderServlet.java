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

        System.out.println("-------------START POST------------------");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        String customer = request.getParameter("customerChoice");
        session.setAttribute("customerChoice", customer);

        String newDate = request.getParameter("editOrderDate");
        String newOrderDate = request.getParameter("newOrderDate");


        String orderIDString = (String)session.getAttribute("orderID");
        System.out.println("orderIDString: " + orderIDString);
        String del = request.getParameter("delete");
        System.out.println("del:: " + del);





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
                int orderIDInt = Integer.parseInt(orderIDString);




                String updateOrder = "UPDATE orders" +
                " SET order_date = ?" +
                ", order_desc = ?" +
                " WHERE order_id = ?";

                PreparedStatement editOrder = con.prepareStatement(updateOrder);
                editOrder.setDate(1, editOrderDate);
                editOrder.setString(2, editDescription);
                editOrder.setInt(3, orderIDInt);

                editOrder.executeUpdate();
                editOrder.close();

                stmt = con.createStatement();
                stmt.execute(updateOrder);

        
            }

            /////////////////////////////////////
            // ADD NEW ORDER
            /////////////////////////////////////
            if(customer != null) {
                System.out.println("--------------Inside New Order Function----------------");

                int custID = Integer.parseInt(customer);
                System.out.println("custID: " + custID);

                String orderDate = request.getParameter("order_date");
                Date date = java.sql.Date.valueOf(orderDate);
                System.out.println("Date: " + date);

                String description = request.getParameter("orderDescription");
                System.out.println("Description: " + description);

        
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
                    System.out.println("insertOrder: " + insertOrder);

        
                    insertOrder.setInt(1, custID);
                    insertOrder.setDate(2, date);
                    insertOrder.setString(3, description);
        
                    insertOrder.executeUpdate();
                    insertOrder.close();             
        
                    System.out.println("------------Prepared Statement Complete-------------");

                    // END OF ADDING NEW ORDER
            }


            /**
             * /////////////////
             * DELETE ORDER
             * \\\\\\\\\\\\\\\\\
             */
            System.out.println("------------Before Delete Function-------------");

            if(del != null) {
                int orderIDInt = Integer.parseInt(orderIDString);

                String deleteOrder = "DELETE FROM orders" +
                                     " WHERE order_id = ?";
                PreparedStatement delOrder = con.prepareStatement(deleteOrder);

                delOrder.setInt(1, orderIDInt);
                delOrder.executeUpdate();
                delOrder.close();
            };

            //////// END DELETE ORDER \\\\\\\\\

            System.out.println("------------End Delete-------------");

    

    
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
            System.out.println("------------End all SQL code-------------");


            /**
             * Reset 'editOrderIDString' to null so you can add new 
             * orders after editing one
             */
            System.out.println("Before redirect orderIDString: " + orderIDString);

            orderIDString = null;
            session.setAttribute("orderIDString", orderIDString);
            System.out.println("After reset orderIDString: " + orderIDString);

    
        System.out.println("------------Redirect to HomeServlet-------------");
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

        //Edit Link ID
        String orderIDString = request.getParameter("orderID");
        if(orderIDString != null) {

            int orderIDInt = Integer.parseInt(orderIDString);

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

        }
        String del = request.getParameter("delOrderID");


        session.setAttribute("orderID", orderIDString);
        session.setAttribute("del", del);

        response.sendRedirect ("OrderEdit.jsp");
    }
}