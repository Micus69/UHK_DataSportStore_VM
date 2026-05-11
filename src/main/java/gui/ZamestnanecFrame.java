package gui;

import javax.swing.*;
import java.awt.*;

public class ZamestnanecFrame extends JFrame {

    public ZamestnanecFrame() {
        setTitle("Employee Panel");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 160, 40, 160));

        JButton registerCustomerButton = new JButton("Register Customer");
        JButton availableEquipmentButton = new JButton("Available Equipment");
        JButton createRentalButton = new JButton("Create Rental");
        JButton returnEquipmentButton = new JButton("Return Equipment");
        JButton closeButton = new JButton("Close");

        registerCustomerButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Customer registration will be implemented.")
        );

        availableEquipmentButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Available equipment overview will be implemented.")
        );

        createRentalButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Rental creation will be implemented.")
        );

        returnEquipmentButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Equipment return will be implemented.")
        );

        closeButton.addActionListener(e -> dispose());

        panel.add(registerCustomerButton);
        panel.add(availableEquipmentButton);
        panel.add(createRentalButton);
        panel.add(returnEquipmentButton);
        panel.add(closeButton);

        add(panel);
    }
}