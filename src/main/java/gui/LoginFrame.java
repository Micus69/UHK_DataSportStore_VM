package gui;

import model.Zamestnanec;
import repository.AuthRepository;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthRepository authRepository;

    private JTextField loginField;
    private JPasswordField passwordField;

    public LoginFrame() {
        this.authRepository = new AuthRepository();

        setTitle("Employee / Admin Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        loginField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());

        panel.add(new JLabel("Login:"));
        panel.add(loginField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
    }

    private void login() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        Zamestnanec zamestnanec = authRepository.login(login, password);

        if (zamestnanec == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid login or password.",
                    "Login failed",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if ("ADMIN".equalsIgnoreCase(zamestnanec.getRole())) {
            new AdminFrame().setVisible(true);
        } else if ("EMPLOYEE".equalsIgnoreCase(zamestnanec.getRole())) {
            new ZamestnanecFrame(zamestnanec).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Unknown user role.");
            return;
        }

        dispose();
    }
}