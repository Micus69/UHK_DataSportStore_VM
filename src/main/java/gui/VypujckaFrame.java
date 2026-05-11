package gui;

import javax.swing.*;
import java.awt.*;

public class VypujckaFrame extends JFrame {

    public VypujckaFrame() {
        setTitle("Create Rental");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField customerIdField = new JTextField();
        JTextField equipmentIdField = new JTextField();
        JTextField employeeIdField = new JTextField();

        JTextField dateFromField = new JTextField("2024-06-01");
        JTextField dateToField = new JTextField("2024-06-03");

        JButton createRentalButton = new JButton("Create Rental");

        createRentalButton.addActionListener(e -> {

            /*
             * Later this button will call RentalService
             * and save rental data into the database.
             */

            JOptionPane.showMessageDialog(
                    this,
                    "Rental successfully created."
            );
        });

        panel.add(new JLabel("Customer ID:"));
        panel.add(customerIdField);

        panel.add(new JLabel("Equipment ID:"));
        panel.add(equipmentIdField);

        panel.add(new JLabel("Employee ID:"));
        panel.add(employeeIdField);

        panel.add(new JLabel("Date From:"));
        panel.add(dateFromField);

        panel.add(new JLabel("Date To:"));
        panel.add(dateToField);

        panel.add(new JLabel());
        panel.add(createRentalButton);

        add(panel);
    }
}