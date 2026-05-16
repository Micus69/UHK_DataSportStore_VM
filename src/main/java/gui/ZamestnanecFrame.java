package gui;

import model.Zamestnanec;

import javax.swing.*;
import java.awt.*;

public class ZamestnanecFrame extends JFrame {

    private final Zamestnanec zamestnanec;

    public ZamestnanecFrame(Zamestnanec zamestnanec) {
        this.zamestnanec = zamestnanec;

        setTitle("Employee Panel - " + zamestnanec.getJmeno() + " " + zamestnanec.getPrijmeni());
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 160, 40, 160));

        JButton reservationsButton = new JButton("Active Reservations");
        JButton activeRentalsButton = new JButton("Active Rentals");
        JButton returnEquipmentButton = new JButton("Return Equipment");
        JButton closeButton = new JButton("Close");

        reservationsButton.addActionListener(e ->
                new RezervacePrehledFrame(zamestnanec).setVisible(true)
        );

        activeRentalsButton.addActionListener(e ->
                new AktivniVypujckyFrame().setVisible(true)
        );

        returnEquipmentButton.addActionListener(e ->
                new VraceniFrame().setVisible(true)
        );

        closeButton.addActionListener(e -> dispose());

        panel.add(reservationsButton);
        panel.add(activeRentalsButton);
        panel.add(returnEquipmentButton);
        panel.add(closeButton);

        add(panel);
    }
}