package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/devam_takip";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "15ea1978";

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            System.out.println("Veritabanı bağlantı hatası!");
            e.printStackTrace();

            return null;
        }
    }
}