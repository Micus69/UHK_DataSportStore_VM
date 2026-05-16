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

        JButton manageEquipmentButton = new JButton("Správa vybavení");
        JButton manageEmployeesButton = new JButton("Správa zaměstnanců");
        JButton manageCodeListsButton = new JButton("Správa čísel");
        JButton statisticsButton = new JButton("Statistiky obchodu");
        JButton closeButton = new JButton("Zavřít");

        manageEquipmentButton.addActionListener(e ->
                new VybaveniManagementFrame().setVisible(true)
        );

        manageEmployeesButton.addActionListener(e ->
                new ZamestnanecManagementFrame().setVisible(true)
        );

        manageCodeListsButton.addActionListener(e ->
                new StavVybaveniFrame().setVisible(true)
        );

        statisticsButton.addActionListener(e ->
                new StatistikaFrame().setVisible(true)
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