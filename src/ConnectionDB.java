import java.sql.*;

public class ConnectionDB {

    public static final String URL = "jdbc:mysql://149.4.211.180:3306/";
    public static final String DBName = "hara6098";
    public static final String USER = "hara6098";
    public static final String PASS = "14026098";

    public static Connection getConnection()
    {
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL+DBName,USER,PASS);

        } catch (SQLException ex) {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } catch (Exception e) {

            System.out.println(e);

        }

        return conn;
    }

    public static boolean isValidUser (String userName, String passWord) {

        int userID = Integer.parseInt(userName);
        String returnedUserID = "";
        String returnedPass = "";

        Statement stmt = null;
        String query = String.format("SELECT a.UserID, p.Password FROM admin a INNER JOIN password p ON a.UserID = p.UserId WHERE a.UserID = %s", userID);

        try {

            Connection conn = ConnectionDB.getConnection();

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                returnedUserID = rs.getString("UserID");
                returnedPass = rs.getString("Password");

            }

            conn.close();
            if (returnedUserID.equals(userName) && returnedPass.equals(passWord)) return true;

        } catch (SQLException e ) {

            System.out.println("Invalid");

        }
        return false;
    }

}