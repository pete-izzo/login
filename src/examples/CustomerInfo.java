//Java Customer Info class file

package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.sql.*;
import java.util.Date;
import static java.lang.System.*;
import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class CustomerInfo{
    private int customerID;
    private String customerName = null;

    public CustomerInfo(){
    }
    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}