package dao;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendanceDAO {

    public ResultSet getAttendanceReport() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = """
                    SELECT 
                        a.id,
                        s.student_no,
                        s.name,
                        s.surname,
                        c.course_code,
                        c.course_name,
                        a.attendance_date,
                        a.status
                    FROM attendance a
                    INNER JOIN students s ON a.student_id = s.id
                    INNER JOIN courses c ON a.course_id = c.id
                    ORDER BY a.attendance_date DESC
                    """;

            PreparedStatement statement = connection.prepareStatement(sql);

            return statement.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}