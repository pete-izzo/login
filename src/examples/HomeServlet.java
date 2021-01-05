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
        System.out.println("got choice");

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

        //Master list created so you can always query every customer later
        ArrayList<OrderInfo> masterList = new ArrayList<OrderInfo>();

        ArrayList<OrderInfo> newOrders = new ArrayList<OrderInfo>();

        ArrayList<OrderInfo> custOrders = new ArrayList<OrderInfo>();



        //Get data from derby
        try {

            System.out.println("initial db connect");

            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

            con = ds.getConnection();

            stmt = con.createStatement();
            statement = con.createStatement();
            /**
             * The Query
             * connects both tables at cust_id so customers names 
             * aren't duplicated for each order
             */


            String sql = "SELECT o.*, c.cust_name" +
                         " FROM orders o, customers c" +
                         " WHERE o.cust_id = c.cust_id";
            
            rs = stmt.executeQuery(sql);

            /**
            * New Query
            * queries and displays info based on dropdown selection
            */

            /**
             * Need to use ".equals()" here because
             * simply using "==" means the value is in the same
             * address in memory. Which it isn't.
             */
            System.out.println("before if/else");
            System.out.println(choice);

            if(choice != null) {
                System.out.println(masterList);


                if (choice.equals("1")){
                    System.out.println("inside if");
    
                    newQuery = "SELECT o.*, c.cust_name" +
                    " FROM orders o, customers c" +
                    " WHERE o.cust_id = c.cust_id";
        
    
                } else{
                    newQuery = "SELECT o.*, c.cust_name" +
                    " FROM orders o, customers c" +
                    " WHERE o.cust_id = c.cust_id" +
                    " AND c.cust_name = '" + choice + "'";
        
    
                }
                resultset = statement.executeQuery(newQuery);

                while(resultset.next()) {
    
                    OrderInfo orders = new OrderInfo();
    
                    orders.setOrderID(resultset.getInt("order_id"));
    
                    orders.setCustomerName(resultset.getString("cust_name"));
                    
                    orders.setOrderDate(resultset.getDate("order_date"));
    
                    orders.setDescription(resultset.getString("order_desc"));              
    
                    custOrders.add(orders);
                }
    
    
            }
            System.out.println(masterList);

           

            
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
                masterList.add(orders);
            }

            //Print DB info to page to make sure it's connected

            String productInfo = con.getMetaData().getDatabaseProductName();

            session.setAttribute("DBProductInfo", productInfo);

            System.out.println("end db");

        } catch (NamingException ex) {

            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
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
        System.out.println("begin sort");

        Collections.sort(newOrders, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        });

        Collections.sort(masterList, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        });

        Collections.sort(custOrders, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                return o1.getOrderDate().compareTo(o2.getOrderDate());
            }
        });


        System.out.println("after sort");

        session.setAttribute("cooldata", newOrders);
        session.setAttribute("masterList", masterList);

        //list with new query objects
        session.setAttribute("custOrders", custOrders);



        response.sendRedirect ("home.jsp");

    }

    /**
     * //////////////////////////
     * Post method for dropdown selection
     * //////////////////////////
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        doGet(request, response);
        System.out.println("doGet call");

        // HttpSession session = request.getSession();

        // String myUserName = (String)session.getAttribute("name");

        // String isLoggedIn = (String)session.getAttribute("logged");

        // //Should be  grabbed from session just so we can clear it 
        // //to make room for the other queries
        // ArrayList<OrderInfo> newOrders = (ArrayList)session.getAttribute("cooldata");


        // /**
        //  * /////////////
        //  * DB INFO
        //  * /////////////
        //  */

        // String dbURL ="java:comp/env/jdbc/NewDBTest";

        // String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        // Context context = null;
        // Connection conn = null;
        // Statement statement = null;
        // ResultSet resultset = null;

        /**
         * clear "new orders" and "custOrders" array's so the tables on the
         * page only display what is queried
         */

        // //Get data from derby
        // try {
        //     context = new InitialContext();
        //     DataSource dsource = (DataSource) context.lookup("java:comp/env/jdbc/firstDB");

        //     conn = dsource.getConnection();

        //     statement = conn.createStatement();


        //     /**
        //      * Need to use ".equals()" here because
        //      * simply using "==" means the value is in the same
        //      * address in memory. Which it isn't.
        //      */
        //     if (choice.equals("1")){
        //         newQuery = "SELECT o.*, c.cust_name" +
        //         " FROM orders o, customers c" +
        //         " WHERE o.cust_id = c.cust_id";

        //     } else{
        //         newQuery = "SELECT o.*, c.cust_name" +
        //         " FROM orders o, customers c" +
        //         " WHERE o.cust_id = c.cust_id" +
        //         " AND c.cust_name = '" + choice + "'";

        //     }

        //     resultset = statement.executeQuery(newQuery);

            
        //     while(resultset.next()) {

        //         OrderInfo orders = new OrderInfo();

        //         orders.setOrderID(resultset.getInt("order_id"));

        //         orders.setCustomerName(resultset.getString("cust_name"));
                
        //         orders.setOrderDate(resultset.getDate("order_date"));

        //         orders.setDescription(resultset.getString("order_desc"));              

        //         custOrders.add(orders);
        //     }
            
        // } catch (NamingException ex) {

        //     ex.printStackTrace();
        // } catch (SQLException ex) {
        //     ex.printStackTrace();
        // } finally {
        //     try {
        //         resultset.close();
        //         statement.close();
        //         conn.close();
        //         context.close();


    
        //     }catch (SQLException error) {
        //         error.printStackTrace();
        //     }catch (NamingException error) {
        //         error.printStackTrace();
        //     }
        // }

            

        //refreshes current page instead of sending elsewhere
        response.setHeader("Refresh", "0; URL=/login/home.jsp");

    
    }
}