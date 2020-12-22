import java.sql.*;

public class CreateDB {
    //DB details
    static final String driver = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:\\Users\\pizzo\\projects\\Login\\src\\sql";

    //DB credentials
    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        System.out.print("idk");
        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            System.out.print("cool");
            try {
                try{
                    stmt.execute("CREATE DATABASE tables");
                    stmt.execute("CREATE TABLE users(userid varchar(128) primary key, passwd_digest varchar(128))");
                    stmt.execute("CREATE TABLE customers(cust_id int not null auto_increment primary key, cust_name varchar(128))");
                    stmt.execute("CREATE TABLE orders(order_id int auto_increment primary key, cust_id int foreign key references customers(cust_id), order_date date, order_desc varchar(128))");
                    conn.commit();

                } catch (SQLException ex) {
                    stmt.execute("open database tables");
                }

                stmt.executeUpdate("insert into users(userid, passwd_digest) values ('Testboy1', 'pass')");
                stmt.executeUpdate("insert into customers(cust_name) values ( 'Johnny')");
                stmt.executeUpdate("insert into orders(order_date, order_desc) values (curdate(), 'A pool noodle')");
                conn.commit();
                ResultSet rs = stmt.executeQuery("select * from tables");
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                try {
                    while(rs.next()) {
                        String row = "";
                        for (int i = 1; i <= columnsNumber; i++)
                            System.out.print(rs.getString(i) + " ");
                        System.out.println();
                    } 
                } finally {
                    rs.close();
                }
            } finally {
                stmt.close();
            }

        }catch (SQLException exception) {
            System.err.println ("SQLException : " + exception.toString ());
        } finally{
            conn.close();
        }

    } 
}