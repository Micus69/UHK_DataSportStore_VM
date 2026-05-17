/*
 * Rental creation window.
 * Allows employees to manually create
 * equipment rental records.
 *
 * Current functionality:
 * - Displays rental input form
 * - Allows entering rental information
 * - Simulates rental creation process
 *
 * Note:
 * This frame currently demonstrates
 * GUI functionality only and does not yet
 * store rental data into database.
 */

package gui;

import javax.swing.*;
import java.awt.*;

public class VypujckaFrame extends JFrame {

    public VypujckaFrame() {

        setTitle("Vytvoření výpůjčky");

        setSize(600, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();
    }

    /*
     * Creates graphical layout
     * for rental creation form.
     */
    private void initLayout() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                7,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        /*
         * Input fields for rental information.
         */
        JTextField customerIdField =
                new JTextField();

        JTextField equipmentIdField =
                new JTextField();

        JTextField employeeIdField =
                new JTextField();

        /*
         * Rental date fields.
         */
        JTextField dateFromField =
                new JTextField("2024-06-01");

        JTextField dateToField =
                new JTextField("2024-06-03");

        /*
         * Creates rental action button.
         */
        JButton createRentalButton =
                new JButton("Vytvořit výpůjčku");

        createRentalButton.addActionListener(
                e -> {

                    /*
                     * Placeholder rental logic.
                     * Future implementation will save
                     * rental data into database.
                     */
                    JOptionPane.showMessageDialog(
                            this,
                            "Výpůjčka byla úspěšně vytvořena."
                    );
                }
        );

        /*
         * Form layout structure.
         */
        panel.add(new JLabel("ID zákazníka:"));
        panel.add(customerIdField);

        panel.add(new JLabel("ID vybavení:"));
        panel.add(equipmentIdField);

        panel.add(new JLabel("ID zaměstnance:"));
        panel.add(employeeIdField);

        panel.add(new JLabel("Datum od:"));
        panel.add(dateFromField);

        panel.add(new JLabel("Datum do:"));
        panel.add(dateToField);

        panel.add(new JLabel());
        panel.add(createRentalButton);

        add(panel);
    }
}