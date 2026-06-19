package ui;

import dao.StudentDAO;
import db.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentFrame extends JFrame {

    JTextField txtNo, txtName, txtSurname, txtDepartment;
    JButton btnSave, btnDelete;
    JTable table;
    DefaultTableModel model;

    public StudentFrame() {

        setTitle("Öğrenci İşlemleri");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("ÖĞRENCİ EKLE / LİSTELE / SİL");
        lblTitle.setBounds(240, 20, 250, 30);

        JLabel lblNo = new JLabel("Öğrenci No:");
        lblNo.setBounds(50, 70, 100, 25);
        txtNo = new JTextField();
        txtNo.setBounds(160, 70, 150, 25);

        JLabel lblName = new JLabel("Ad:");
        lblName.setBounds(50, 110, 100, 25);
        txtName = new JTextField();
        txtName.setBounds(160, 110, 150, 25);

        JLabel lblSurname = new JLabel("Soyad:");
        lblSurname.setBounds(50, 150, 100, 25);
        txtSurname = new JTextField();
        txtSurname.setBounds(160, 150, 150, 25);

        JLabel lblDepartment = new JLabel("Bölüm:");
        lblDepartment.setBounds(50, 190, 100, 25);
        txtDepartment = new JTextField();
        txtDepartment.setBounds(160, 190, 150, 25);

        btnSave = new JButton("Kaydet");
        btnSave.setBounds(130, 240, 120, 35);

        btnDelete = new JButton("Seçili Öğrenciyi Sil");
        btnDelete.setBounds(100, 290, 180, 35);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "Öğrenci No", "Ad", "Soyad", "Bölüm"
        });

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(330, 70, 330, 360);

        add(lblTitle);
        add(lblNo);
        add(txtNo);
        add(lblName);
        add(txtName);
        add(lblSurname);
        add(txtSurname);
        add(lblDepartment);
        add(txtDepartment);
        add(btnSave);
        add(btnDelete);
        add(scrollPane);

        btnSave.addActionListener(e -> saveStudent());
        btnDelete.addActionListener(e -> deleteStudent());

        loadStudents();
    }

    private void saveStudent() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO students(student_no, name, surname, department) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, txtNo.getText());
            statement.setString(2, txtName.getText());
            statement.setString(3, txtSurname.getText());
            statement.setString(4, txtDepartment.getText());

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Öğrenci başarıyla kaydedildi!");

            txtNo.setText("");
            txtName.setText("");
            txtSurname.setText("");
            txtDepartment.setText("");

            loadStudents();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kayıt sırasında hata oluştu!");
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try {
            model.setRowCount(0);

            StudentDAO studentDAO = new StudentDAO();
            ResultSet resultSet = studentDAO.getAllStudents();

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("student_no"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("department")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent() {
        try {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek öğrenciyi tablodan seçin!");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Seçili öğrenciyi silmek istediğine emin misin?",
                    "Silme Onayı",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            Connection connection = DatabaseConnection.getConnection();

            String sql = "DELETE FROM students WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Öğrenci başarıyla silindi!");

            loadStudents();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Silme sırasında hata oluştu!");
            e.printStackTrace();
        }
    }
}