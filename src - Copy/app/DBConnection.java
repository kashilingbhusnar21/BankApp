package app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String url =
            "jdbc:mysql://localhost:3306/bankapp";
    private static final String username = "root";
    private static final String password = "Sushant";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}