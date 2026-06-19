package ui;

import dao.AttendanceDAO;
import db.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AttendanceFrame extends JFrame {

    JComboBox<String> cmbStudents, cmbCourses, cmbStatus;
    JTextField txtDate;
    JButton btnSave;
    JTable table;
    DefaultTableModel model;

    public AttendanceFrame() {

        setTitle("Yoklama İşlemleri");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("YOKLAMA AL / LİSTELE");
        lblTitle.setBounds(360, 20, 250, 30);

        JLabel lblStudent = new JLabel("Öğrenci:");
        lblStudent.setBounds(40, 80, 100, 25);
        cmbStudents = new JComboBox<>();
        cmbStudents.setBounds(140, 80, 200, 25);

        JLabel lblCourse = new JLabel("Ders:");
        lblCourse.setBounds(40, 120, 100, 25);
        cmbCourses = new JComboBox<>();
        cmbCourses.setBounds(140, 120, 200, 25);

        JLabel lblDate = new JLabel("Tarih:");
        lblDate.setBounds(40, 160, 100, 25);
        txtDate = new JTextField("2026-06-19");
        txtDate.setBounds(140, 160, 200, 25);

        JLabel lblStatus = new JLabel("Durum:");
        lblStatus.setBounds(40, 200, 100, 25);
        cmbStatus = new JComboBox<>(new String[]{"Geldi", "Gelmedi"});
        cmbStatus.setBounds(140, 200, 200, 25);

        btnSave = new JButton("Yoklamayı Kaydet");
        btnSave.setBounds(120, 250, 180, 35);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "No", "Ad", "Soyad", "Ders Kodu", "Ders Adı", "Tarih", "Durum"
        });

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(370, 80, 480, 360);

        add(lblTitle);
        add(lblStudent);
        add(cmbStudents);
        add(lblCourse);
        add(cmbCourses);
        add(lblDate);
        add(txtDate);
        add(lblStatus);
        add(cmbStatus);
        add(btnSave);
        add(scrollPane);

        loadStudents();
        loadCourses();
        loadAttendance();

        btnSave.addActionListener(e -> saveAttendance());
    }

    private void loadStudents() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT id, student_no, name, surname FROM students";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            cmbStudents.removeAllItems();

            while (resultSet.next()) {
                String item = resultSet.getInt("id") + " - "
                        + resultSet.getString("student_no") + " - "
                        + resultSet.getString("name") + " "
                        + resultSet.getString("surname");

                cmbStudents.addItem(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCourses() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT id, course_code, course_name FROM courses";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            cmbCourses.removeAllItems();

            while (resultSet.next()) {
                String item = resultSet.getInt("id") + " - "
                        + resultSet.getString("course_code") + " - "
                        + resultSet.getString("course_name");

                cmbCourses.addItem(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAttendance() {
        try {
            if (cmbStudents.getSelectedItem() == null || cmbCourses.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Önce öğrenci ve ders eklemelisin!");
                return;
            }

            String studentText = cmbStudents.getSelectedItem().toString();
            String courseText = cmbCourses.getSelectedItem().toString();

            int studentId = Integer.parseInt(studentText.split(" - ")[0]);
            int courseId = Integer.parseInt(courseText.split(" - ")[0]);

            Connection connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO attendance(student_id, course_id, attendance_date, status) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.setString(3, txtDate.getText());
            statement.setString(4, cmbStatus.getSelectedItem().toString());

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Yoklama başarıyla kaydedildi!");

            loadAttendance();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Yoklama kaydedilirken hata oluştu! Tarihi YYYY-MM-DD şeklinde yaz.");
            e.printStackTrace();
        }
    }

    private void loadAttendance() {
        try {
            model.setRowCount(0);

            AttendanceDAO attendanceDAO = new AttendanceDAO();
            ResultSet resultSet = attendanceDAO.getAttendanceReport();

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("student_no"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("course_code"),
                        resultSet.getString("course_name"),
                        resultSet.getDate("attendance_date"),
                        resultSet.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}