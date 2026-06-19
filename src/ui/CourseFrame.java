package ui;

import dao.CourseDAO;
import db.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseFrame extends JFrame {

    JTextField txtCourseCode, txtCourseName;
    JButton btnSave, btnDelete;
    JTable table;
    DefaultTableModel model;

    public CourseFrame() {

        setTitle("Ders İşlemleri");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("DERS EKLE / LİSTELE / SİL");
        lblTitle.setBounds(220, 20, 250, 30);

        JLabel lblCode = new JLabel("Ders Kodu:");
        lblCode.setBounds(40, 80, 100, 25);
        txtCourseCode = new JTextField();
        txtCourseCode.setBounds(140, 80, 150, 25);

        JLabel lblName = new JLabel("Ders Adı:");
        lblName.setBounds(40, 130, 100, 25);
        txtCourseName = new JTextField();
        txtCourseName.setBounds(140, 130, 150, 25);

        btnSave = new JButton("Kaydet");
        btnSave.setBounds(100, 190, 130, 35);

        btnDelete = new JButton("Seçili Dersi Sil");
        btnDelete.setBounds(75, 240, 180, 35);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "Ders Kodu", "Ders Adı"
        });

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(320, 80, 280, 260);

        add(lblTitle);
        add(lblCode);
        add(txtCourseCode);
        add(lblName);
        add(txtCourseName);
        add(btnSave);
        add(btnDelete);
        add(scrollPane);

        btnSave.addActionListener(e -> saveCourse());
        btnDelete.addActionListener(e -> deleteCourse());

        loadCourses();
    }

    private void saveCourse() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO courses(course_code, course_name) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, txtCourseCode.getText());
            statement.setString(2, txtCourseName.getText());

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Ders başarıyla kaydedildi!");

            txtCourseCode.setText("");
            txtCourseName.setText("");

            loadCourses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kayıt sırasında hata oluştu!");
            e.printStackTrace();
        }
    }

    private void loadCourses() {
        try {
            model.setRowCount(0);

            CourseDAO courseDAO = new CourseDAO();
            ResultSet resultSet = courseDAO.getAllCourses();

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("course_code"),
                        resultSet.getString("course_name")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCourse() {
        try {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek dersi tablodan seçin!");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Seçili dersi silmek istediğine emin misin?",
                    "Silme Onayı",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            Connection connection = DatabaseConnection.getConnection();

            String sql = "DELETE FROM courses WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Ders başarıyla silindi!");

            loadCourses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Silme sırasında hata oluştu!");
            e.printStackTrace();
        }
    }
}