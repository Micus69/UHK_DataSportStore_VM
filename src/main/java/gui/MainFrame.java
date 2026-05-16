package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Systém půjčovny sportovního vybavení");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Půjčovna sportovního vybavení", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));

        JButton equipmentButton = new JButton("Dostupné vybavení");
        JButton reservationButton = new JButton("Vytvořit rezervaci");
        JButton loginButton = new JButton("Přihlášení zaměstnance / administrátora");

        equipmentButton.addActionListener(e ->
                new DostupneVybaveniFrame().setVisible(true)
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