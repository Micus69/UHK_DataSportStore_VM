package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sports Equipment Rental System");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Sports Equipment Rental", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));

        JButton equipmentButton = new JButton("Available Equipment");
        JButton reservationButton = new JButton("Create Reservation");
        JButton loginButton = new JButton("Employee / Admin Login");

        equipmentButton.addActionListener(e ->
                new StavVybaveniFrame().setVisible(true)
        );

        reservationButton.addActionListener(e ->
                new RezervaceFrame().setVisible(true)
        );

        loginButton.addActionListener(e ->
                new LoginFrame().setVisible(true)
        );

        buttonPanel.add(equipmentButton);
        buttonPanel.add(reservationButton);
        buttonPanel.add(loginButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
    }
}