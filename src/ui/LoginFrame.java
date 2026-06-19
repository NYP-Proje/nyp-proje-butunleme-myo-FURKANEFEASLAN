package ui;

import dao.UserDAO;

import javax.swing.*;

public class LoginFrame extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin;

    public LoginFrame() {

        setTitle("Devam Takip Sistemi");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        JLabel lblTitle = new JLabel("DEVAM TAKİP SİSTEMİ");
        lblTitle.setBounds(120, 20, 200, 30);

        JLabel lblUser = new JLabel("Kullanıcı Adı:");
        lblUser.setBounds(50, 80, 100, 25);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 80, 150, 25);

        JLabel lblPass = new JLabel("Şifre:");
        lblPass.setBounds(50, 130, 100, 25);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 130, 150, 25);

        btnLogin = new JButton("Giriş Yap");
        btnLogin.setBounds(130, 190, 120, 35);

        add(lblTitle);
        add(lblUser);
        add(txtUsername);
        add(lblPass);
        add(txtPassword);
        add(btnLogin);

        btnLogin.addActionListener(e -> {

            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            UserDAO userDAO = new UserDAO();

            if (userDAO.login(username, password)) {
                JOptionPane.showMessageDialog(this, "Giriş başarılı!");
                DashboardFrame dashboardFrame = new DashboardFrame();
                dashboardFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Kullanıcı adı veya şifre hatalı!");
            }
        });
    }
}