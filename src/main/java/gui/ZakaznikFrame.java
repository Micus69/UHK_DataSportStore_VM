package gui;

import javax.swing.*;
import java.awt.*;

public class ZakaznikFrame extends JFrame {

    public ZakaznikFrame() {

        setTitle("Registrace zákazníka");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();

        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        JTextField cityField = new JTextField();
        JTextField streetField = new JTextField();

        JTextField houseNumberField = new JTextField();
        JTextField zipCodeField = new JTextField();

        JButton registerButton = new JButton("Registrovat zákazníka");

        registerButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Zákazník byl úspěšně zaregistrován."
            );
        });

        panel.add(new JLabel("Jméno:"));
        panel.add(firstNameField);

        panel.add(new JLabel("Příjmení:"));
        panel.add(lastNameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Telefon:"));
        panel.add(phoneField);

        panel.add(new JLabel("Město:"));
        panel.add(cityField);

        panel.add(new JLabel("Ulice:"));
        panel.add(streetField);

        panel.add(new JLabel("Číslo popisné:"));
        panel.add(houseNumberField);

        panel.add(new JLabel("PSČ:"));
        panel.add(zipCodeField);

        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);
    }
}