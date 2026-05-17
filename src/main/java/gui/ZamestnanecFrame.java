/*
 * Employee main panel.
 * Provides access to employee operations
 * related to reservations, rentals
 * and equipment returns.
 *
 * Accessible only after successful login.
 */

package gui;

import model.Zamestnanec;

import javax.swing.*;
import java.awt.*;

public class ZamestnanecFrame extends JFrame {

    // Currently logged-in employee.
    private final Zamestnanec zamestnanec;

    public ZamestnanecFrame(
            Zamestnanec zamestnanec
    ) {

        this.zamestnanec = zamestnanec;

        setTitle(
                "Panel zaměstnance - "
                        + zamestnanec.getJmeno()
                        + " "
                        + zamestnanec.getPrijmeni()
        );

        setSize(650, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();
    }

    /*
     * Creates graphical layout
     * for employee navigation panel.
     */
    private void initLayout() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                15,
                                15
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        160,
                        40,
                        160
                )
        );

        /*
         * Opens active reservation overview.
         */
        JButton reservationsButton =
                new JButton("Aktivní rezervace");

        /*
         * Opens active rental overview.
         */
        JButton activeRentalsButton =
                new JButton("Aktivní výpůjčky");

        /*
         * Opens equipment return window.
         */
        JButton returnEquipmentButton =
                new JButton("Vrácení vybavení");

        /*
         * Closes employee panel.
         */
        JButton closeButton =
                new JButton("Zavřít");

        reservationsButton.addActionListener(
                e -> new RezervacePrehledFrame(
                        zamestnanec
                ).setVisible(true)
        );

        activeRentalsButton.addActionListener(
                e -> new AktivniVypujckyFrame()
                        .setVisible(true)
        );

        returnEquipmentButton.addActionListener(
                e -> new VraceniFrame()
                        .setVisible(true)
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        panel.add(reservationsButton);

        panel.add(activeRentalsButton);

        panel.add(returnEquipmentButton);

        panel.add(closeButton);

        add(panel);
    }
}