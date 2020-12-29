//Java Order Info class file

package examples;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import java.lang.*;
import java.sql.*;
import java.util.Date;
import static java.lang.System.*;
import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class OrderInfo {
    private int orderID;
    private int customerID;
    private String customerName = null;
    private Date orderDate = null;
    private String description = null;

    public OrderInfo(){
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    



}