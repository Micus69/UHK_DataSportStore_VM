/*
 * Main application window.
 * Serves as the primary navigation menu
 * for customers, employees and administrators.
 */

package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Systém půjčovny sportovního vybavení");

        setSize(700, 400);

        setLocationRelativeTo(null);

        // Closes entire application after window exit.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initLayout();
    }

    /*
     * Creates main application layout
     * and navigation buttons.
     */
    private void initLayout() {

        JPanel panel = new JPanel(new BorderLayout());

        // Main application title.
        JLabel title = new JLabel(
                "Půjčovna sportovního vybavení",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel buttonPanel =
                new JPanel(new GridLayout(3, 1, 15, 15));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        180,
                        40,
                        180
                )
        );

        // Opens customer equipment overview.
        JButton equipmentButton =
                new JButton("Dostupné vybavení");

        // Opens reservation creation window.
        JButton reservationButton =
                new JButton("Vytvořit rezervaci");

        // Opens employee / administrator login window.
        JButton loginButton =
                new JButton(
                        "Přihlášení zaměstnance / administrátora"
                );

        equipmentButton.addActionListener(
                e -> new DostupneVybaveniFrame()
                        .setVisible(true)
        );

        reservationButton.addActionListener(
                e -> new RezervaceFrame()
                        .setVisible(true)
        );

        loginButton.addActionListener(
                e -> new LoginFrame()
                        .setVisible(true)
        );

        buttonPanel.add(equipmentButton);
        buttonPanel.add(reservationButton);
        buttonPanel.add(loginButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
    }
}