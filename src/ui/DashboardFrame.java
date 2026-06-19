package ui;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    JButton btnStudents;
    JButton btnCourses;
    JButton btnAttendance;
    JButton btnReport;
    JButton btnExit;

    public DashboardFrame() {

        setTitle("Devam Takip Sistemi - Ana Menü");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("ANA MENÜ");
        lblTitle.setBounds(180, 30, 150, 30);

        btnStudents = new JButton("Öğrenci İşlemleri");
        btnStudents.setBounds(120, 80, 200, 35);

        btnCourses = new JButton("Ders İşlemleri");
        btnCourses.setBounds(120, 130, 200, 35);

        btnAttendance = new JButton("Yoklama İşlemleri");
        btnAttendance.setBounds(120, 180, 200, 35);

        btnReport = new JButton("Devamsızlık Raporu");
        btnReport.setBounds(120, 230, 200, 35);

        btnExit = new JButton("Çıkış");
        btnExit.setBounds(120, 280, 200, 35);

        add(lblTitle);
        add(btnStudents);
        add(btnCourses);
        add(btnAttendance);
        add(btnReport);
        add(btnExit);

        btnStudents.addActionListener(e -> {
            StudentFrame studentFrame = new StudentFrame();
            studentFrame.setVisible(true);
        });

        btnCourses.addActionListener(e -> {
            CourseFrame courseFrame = new CourseFrame();
            courseFrame.setVisible(true);
        });

        btnAttendance.addActionListener(e -> {
            AttendanceFrame attendanceFrame = new AttendanceFrame();
            attendanceFrame.setVisible(true);
        });

        btnReport.addActionListener(e -> {
            ReportFrame reportFrame = new ReportFrame();
            reportFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            System.exit(0);
        });
    }
}