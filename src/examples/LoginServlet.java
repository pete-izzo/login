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
 
// Class of LoginServlet/
//@WebServlet("/index.jsp")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        String userText = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();

        
        // gets session and sets and attribute with a key-value pair
        //key is "userText" value is "randText"
        HttpSession session = request.getSession();

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

        session.setAttribute("name", userText);
        session.setAttribute("pw", password);




        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/firstDB");

            con = ds.getConnection();

            stmt = con.createStatement();

            String query = "select * from users where userid = ? and passwd_digest = ?";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, userText);
            preparedStatement.setString(2, password);

            rs = preparedStatement.executeQuery();

            String productInfo = con.getMetaData().getDatabaseProductName();

            session.setAttribute("DBProductInfo", productInfo);
    
            

            if (rs.next()) {
                response.sendRedirect ("home.jsp");

            } else{
                out.println("poop");
            }


        
              

            // String un = result[0];

            // String pw = result[1];

            /**
             * Prepared Satament Example below
             */


            // preparedStatement.executeUpdate();
            // preparedStatement.close();

            // stmt.execute("insert into users (userid, passwd_digest) values ('" + result[0] + "', '" + result[1] + "')");
            
            // rs = stmt.executeQuery("select * from users");
            // st.close();
            // stmt = con.createStatement();
    
            // rs = stmt.executeQuery("SELECT * FROM USERS");


            //try to print DB info


            
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






        //session.setAttribute("testDB", testDBOutput);

    }
}