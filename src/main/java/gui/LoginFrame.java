package gui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Employee / Admin Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            /*
             * This is a temporary login check.
             * Later this logic should be moved into AuthService
             * and verified against the Zamestnanec database table.
             */
            if (login.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Logged in as ADMIN");
                new AdminFrame().setVisible(true);
                dispose();
            } else if (login.equals("employee") && password.equals("employee123")) {
                JOptionPane.showMessageDialog(this, "Logged in as EMPLOYEE");
                new ZamestnanecFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid login or password.",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        panel.add(new JLabel("Login:"));
        panel.add(loginField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
    }
}