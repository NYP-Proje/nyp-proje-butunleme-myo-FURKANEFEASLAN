package dao;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    public ResultSet getAllStudents() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM students";

            PreparedStatement statement = connection.prepareStatement(sql);

            return statement.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}