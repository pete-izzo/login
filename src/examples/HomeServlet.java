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

        /**
         * ///////////////////////////
         *  TODO: DISPLAY DB ITEMS
         *  FOR LOGGED IN USER
         * ///////////////////////////
         */
        String userText = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();

        
        // gets session and sets and attribute with a key-value pair
        //key is "userText" value is "randText"
        HttpSession session = request.getSession(false);

        String myUserName = (String)session.getAttribute("name");

        String isLoggedIn = (String)session.getAttribute("logged");


        /**
         *  //////////////////////////////////////
         *  Check UN & PW
         *  //////////////////////////////////////
         */
        
        

        String dbURL ="java:comp/env/jdbc/NewDBTest";

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        Context ctx = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        // ArrayList<Object> testDBOutput = new ArrayList<Object>();


        response.sendRedirect ("home.jsp");






        // try {
        //     ctx = new InitialContext();
        //     DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

        //     con = ds.getConnection();

        //     stmt = con.createStatement();

        //     String query = "select * from users where userid = ? and passwd_digest = ?";

        //     PreparedStatement preparedStatement = con.prepareStatement(query);
        //     preparedStatement.setString(1, userText);
        //     preparedStatement.setString(2, password);

        //     rs = preparedStatement.executeQuery();

        //     String productInfo = con.getMetaData().getDatabaseProductName();

        //     session.setAttribute("DBProductInfo", productInfo);
    
            

        //     if (rs.next()) {
        //         response.sendRedirect ("home.jsp");
        //         isLoggedIn = "true";

        //     } else{
        //         response.sendRedirect ("login.jsp");
        //         String invalid = "Please enter a valid username or password";
        //         session.setAttribute("invalid", invalid);

        //     }

            
        // } catch (NamingException ex) {
    
        //     ex.printStackTrace();
        // } catch (SQLException ex) {
        //     ex.printStackTrace();
        // } finally {
        //     try {
        //         rs.close();
        //         stmt.close();
        //         con.close();
        //         ctx.close();



    
        //     }catch (SQLException error) {
        //         error.printStackTrace();
        //     }catch (NamingException error) {
        //         error.printStackTrace();
        //     }
        // }

        // session.setAttribute("logged", isLoggedIn);


    }
}