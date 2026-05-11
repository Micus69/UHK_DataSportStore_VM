package gui;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    public AdminFrame() {
        setTitle("Administrator Panel");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 160, 40, 160));

        JButton manageEquipmentButton = new JButton("Manage Equipment");
        JButton manageEmployeesButton = new JButton("Manage Employees");
        JButton manageCodeListsButton = new JButton("Manage Code Lists");
        JButton statisticsButton = new JButton("Statistics Dashboard");
        JButton closeButton = new JButton("Close");

        manageEquipmentButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Equipment management will be implemented.")
        );

        manageEmployeesButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Employee management will be implemented.")
        );

        manageCodeListsButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Code list management will be implemented.")
        );

        statisticsButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Statistics dashboard will be implemented.")
        );

        closeButton.addActionListener(e -> dispose());

        panel.add(manageEquipmentButton);
        panel.add(manageEmployeesButton);
        panel.add(manageCodeListsButton);
        panel.add(statisticsButton);
        panel.add(closeButton);

        add(panel);
    }
}