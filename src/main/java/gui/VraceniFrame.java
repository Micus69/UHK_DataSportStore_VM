package gui;

import javax.swing.*;
import java.awt.*;

public class VraceniFrame extends JFrame {

    public VraceniFrame() {
        setTitle("Return Equipment");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField rentalIdField = new JTextField();
        JTextField returnDateField = new JTextField("2024-06-10");

        JButton returnButton = new JButton("Return Equipment");

        returnButton.addActionListener(e -> {

            /*
             * Later this logic will call RentalService
             * and execute the stored procedure VratVypujcku.
             */

            JOptionPane.showMessageDialog(
                    this,
                    "Equipment successfully returned.\nRental ID: "
                            + rentalIdField.getText()
            );
        });

        panel.add(new JLabel("Rental ID:"));
        panel.add(rentalIdField);

        panel.add(new JLabel("Return Date:"));
        panel.add(returnDateField);

        panel.add(new JLabel());
        panel.add(returnButton);

        add(panel);
    }
}