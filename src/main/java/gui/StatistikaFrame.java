package gui;

import model.Statistika;
import repository.StatistikaRepository;

import javax.swing.*;
import java.awt.*;

public class StatistikaFrame extends JFrame {

    private final StatistikaRepository repository;

    private JLabel activeReservationsValue;
    private JLabel activeRentalsValue;
    private JLabel availableEquipmentValue;
    private JLabel rentedEquipmentValue;
    private JLabel totalRevenueValue;

    public StatistikaFrame() {
        this.repository = new StatistikaRepository();

        setTitle("Statistics Dashboard");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadStatistics();
    }

    private void initLayout() {
        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("Statistics Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel statsPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        statsPanel.setBorder(BorderFactory.createTitledBorder("System Overview"));

        activeReservationsValue = new JLabel();
        activeRentalsValue = new JLabel();
        availableEquipmentValue = new JLabel();
        rentedEquipmentValue = new JLabel();
        totalRevenueValue = new JLabel();

        addStatisticRow(statsPanel, "Active reservations:", activeReservationsValue);
        addStatisticRow(statsPanel, "Active rentals:", activeRentalsValue);
        addStatisticRow(statsPanel, "Available equipment:", availableEquipmentValue);
        addStatisticRow(statsPanel, "Rented equipment:", rentedEquipmentValue);
        addStatisticRow(statsPanel, "Total revenue:", totalRevenueValue);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadStatistics());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        rootPanel.add(titleLabel, BorderLayout.NORTH);
        rootPanel.add(statsPanel, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(rootPanel);
    }

    private void addStatisticRow(JPanel panel, String labelText, JLabel valueLabel) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        valueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        panel.add(label);
        panel.add(valueLabel);
    }

    private void loadStatistics() {
        Statistika statistics = repository.loadStatistics();

        activeReservationsValue.setText(String.valueOf(statistics.getActiveReservations()));
        activeRentalsValue.setText(String.valueOf(statistics.getActiveRentals()));
        availableEquipmentValue.setText(String.valueOf(statistics.getAvailableEquipment()));
        rentedEquipmentValue.setText(String.valueOf(statistics.getRentedEquipment()));
        totalRevenueValue.setText(statistics.getTotalRevenue() + " CZK");
    }
}