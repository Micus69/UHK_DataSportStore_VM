package gui;

import javax.swing.*;
import java.awt.*;

public class ZakaznikFrame extends JFrame {

    public ZakaznikFrame() {

        setTitle("Customer Registration");
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

        JButton registerButton = new JButton("Register Customer");

        registerButton.addActionListener(e -> {

            /*
             * Later this logic will call ZakaznikService
             * and save customer information into MySQL.
             */

            JOptionPane.showMessageDialog(
                    this,
                    "Customer successfully registered."
            );
        });

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("City:"));
        panel.add(cityField);

        panel.add(new JLabel("Street:"));
        panel.add(streetField);

        panel.add(new JLabel("House Number:"));
        panel.add(houseNumberField);

        panel.add(new JLabel("ZIP Code:"));
        panel.add(zipCodeField);

        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);
    }
}