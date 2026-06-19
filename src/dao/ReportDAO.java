package dao;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {

    public ResultSet getAbsenceReport() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = """
                    SELECT 
                        s.student_no,
                        s.name,
                        s.surname,
                        s.department,
                        COUNT(a.id) AS absence_count
                    FROM students s
                    LEFT JOIN attendance a 
                        ON s.id = a.student_id 
                        AND a.status = 'Gelmedi'
                    GROUP BY s.id, s.student_no, s.name, s.surname, s.department
                    ORDER BY absence_count DESC
                    """;

            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}