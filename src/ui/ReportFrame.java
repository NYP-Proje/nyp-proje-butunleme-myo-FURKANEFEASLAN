package ui;

import dao.ReportDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class ReportFrame extends JFrame {

    JTable table;
    DefaultTableModel model;
    JLabel lblInfo;

    public ReportFrame() {

        setTitle("Devamsızlık Raporu");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("DEVAMSIZLIK RAPORU");
        lblTitle.setBounds(290, 20, 200, 30);

        lblInfo = new JLabel("5 ve üzeri devamsızlığı olan öğrenciler riskli kabul edilir.");
        lblInfo.setBounds(190, 55, 400, 25);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Öğrenci No", "Ad", "Soyad", "Bölüm", "Devamsızlık Sayısı", "Durum"
        });

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 100, 660, 260);

        add(lblTitle);
        add(lblInfo);
        add(scrollPane);

        loadReport();
    }

    private void loadReport() {
        try {
            model.setRowCount(0);

            ReportDAO reportDAO = new ReportDAO();
            ResultSet resultSet = reportDAO.getAbsenceReport();

            while (resultSet.next()) {

                int absenceCount = resultSet.getInt("absence_count");

                String status;

                if (absenceCount >= 5) {
                    status = "Riskli";
                } else {
                    status = "Normal";
                }

                model.addRow(new Object[]{
                        resultSet.getString("student_no"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("department"),
                        absenceCount,
                        status
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}