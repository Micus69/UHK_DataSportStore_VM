package gui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class RezervaceFrame extends JFrame {

    public RezervaceFrame() {
        setTitle("Create Reservation");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        JTextField equipmentIdField = new JTextField();
        JTextField dateFromField = new JTextField("2024-06-01");
        JTextField dateToField = new JTextField("2024-06-03");

        JButton createButton = new JButton("Create Reservation");

        createButton.addActionListener(e -> {
            /*
             * This is currently only GUI logic.
             * Later this button will call ReservationService,
             * which will save the reservation into the database.
             */
            String message =
                    "Reservation request created:\n\n" +
                            "Customer: " + firstNameField.getText() + " " + lastNameField.getText() + "\n" +
                            "Email: " + emailField.getText() + "\n" +
                            "Phone: " + phoneField.getText() + "\n" +
                            "Equipment ID: " + equipmentIdField.getText() + "\n" +
                            "From: " + dateFromField.getText() + "\n" +
                            "To: " + dateToField.getText();

            JOptionPane.showMessageDialog(this, message);
        });

        panel.add(new JLabel("First name:"));
        panel.add(firstNameField);

        panel.add(new JLabel("Last name:"));
        panel.add(lastNameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Equipment ID:"));
        panel.add(equipmentIdField);

        panel.add(new JLabel("Date from:"));
        panel.add(dateFromField);

        panel.add(new JLabel("Date to:"));
        panel.add(dateToField);

        panel.add(new JLabel());
        panel.add(createButton);

        add(panel);
    }
}