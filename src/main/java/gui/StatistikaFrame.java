/*
 * System statistics overview window.
 * Displays important business statistics
 * about reservations, rentals,
 * equipment availability and revenue.
 */

package gui;

import model.Statistika;
import repository.StatistikaRepository;

import javax.swing.*;
import java.awt.*;

public class StatistikaFrame extends JFrame {

    // Repository responsible for loading statistics data.
    private final StatistikaRepository repository;

    /*
     * Labels used for displaying
     * calculated system statistics.
     */
    private JLabel activeReservationsValue;
    private JLabel activeRentalsValue;
    private JLabel availableEquipmentValue;
    private JLabel rentedEquipmentValue;
    private JLabel totalRevenueValue;

    public StatistikaFrame() {

        this.repository =
                new StatistikaRepository();

        setTitle("Statistiky systému");

        setSize(650, 420);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();

        // Loads statistics after frame creation.
        loadStatistics();
    }

    /*
     * Creates statistics dashboard layout.
     */
    private void initLayout() {

        JPanel rootPanel =
                new JPanel(new BorderLayout(10, 10));

        rootPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Statistiky systému",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        /*
         * Panel used for displaying
         * system statistics overview.
         */
        JPanel statsPanel =
                new JPanel(new GridLayout(5, 2, 15, 15));

        statsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Přehled systému"
                )
        );

        /*
         * Initializes statistic value labels.
         */
        activeReservationsValue = new JLabel();

        activeRentalsValue = new JLabel();

        availableEquipmentValue = new JLabel();

        rentedEquipmentValue = new JLabel();

        totalRevenueValue = new JLabel();

        /*
         * Creates statistic rows.
         */
        addStatisticRow(
                statsPanel,
                "Aktivní rezervace:",
                activeReservationsValue
        );

        addStatisticRow(
                statsPanel,
                "Aktivní výpůjčky:",
                activeRentalsValue
        );

        addStatisticRow(
                statsPanel,
                "Dostupné vybavení:",
                availableEquipmentValue
        );

        addStatisticRow(
                statsPanel,
                "Zapůjčené vybavení:",
                rentedEquipmentValue
        );

        addStatisticRow(
                statsPanel,
                "Celkový obrat:",
                totalRevenueValue
        );

        // Reloads statistics from database.
        JButton refreshButton =
                new JButton("Obnovit");

        refreshButton.addActionListener(
                e -> loadStatistics()
        );

        // Closes statistics window.
        JButton closeButton =
                new JButton("Zavřít");

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        rootPanel.add(titleLabel, BorderLayout.NORTH);

        rootPanel.add(statsPanel, BorderLayout.CENTER);

        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(rootPanel);
    }

    /*
     * Creates one statistic row
     * consisting of label and value.
     */
    private void addStatisticRow(
            JPanel panel,
            String labelText,
            JLabel valueLabel
    ) {

        JLabel label = new JLabel(labelText);

        label.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        valueLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        panel.add(label);

        panel.add(valueLabel);
    }

    /*
     * Loads statistics from repository
     * and updates displayed values.
     */
    private void loadStatistics() {

        Statistika statistics =
                repository.loadStatistics();

        activeReservationsValue.setText(
                String.valueOf(
                        statistics.getActiveReservations()
                )
        );

        activeRentalsValue.setText(
                String.valueOf(
                        statistics.getActiveRentals()
                )
        );

        availableEquipmentValue.setText(
                String.valueOf(
                        statistics.getAvailableEquipment()
                )
        );

        rentedEquipmentValue.setText(
                String.valueOf(
                        statistics.getRentedEquipment()
                )
        );

        totalRevenueValue.setText(
                statistics.getTotalRevenue() + " Kč"
        );
    }
}