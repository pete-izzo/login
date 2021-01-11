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

        

        response.sendRedirect ("./home.jsp");


    }
}