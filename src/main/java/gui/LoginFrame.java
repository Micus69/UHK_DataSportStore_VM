/*
 * Login window used for employee and administrator authentication.
 * The frame verifies login credentials and opens the correct panel
 * based on the user role stored in the database.
 */

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

        // Repository responsible for authentication queries.
        this.authRepository = new AuthRepository();

        setTitle("Přihlášení zaměstnance / administrátora");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    /*
     * Creates login form layout and input components.
     */
    private void initLayout() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        loginField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Přihlásit se");

        // Calls authentication method after button click.
        loginButton.addActionListener(e -> login());

        panel.add(new JLabel("Přihlašovací jméno:"));
        panel.add(loginField);

        panel.add(new JLabel("Heslo:"));
        panel.add(passwordField);

        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
    }

    /*
     * Verifies user credentials against database
     * and opens the correct application panel
     * based on the assigned role.
     */
    private void login() {

        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        // Attempts database authentication.
        Zamestnanec zamestnanec =
                authRepository.login(login, password);

        // Authentication failed.
        if (zamestnanec == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Neplatné přihlašovací jméno nebo heslo.",
                    "Chyba přihlášení",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Opens administrator panel.
        if ("ADMIN".equalsIgnoreCase(zamestnanec.getRole())) {

            new AdminFrame().setVisible(true);

            // Opens employee panel.
        } else if ("EMPLOYEE".equalsIgnoreCase(zamestnanec.getRole())) {

            new ZamestnanecFrame(zamestnanec).setVisible(true);

            // Invalid database role.
        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Neplatné oprávnění uživatele.",
                    "Chyba oprávnění",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Closes login window after successful authentication.
        dispose();
    }
}