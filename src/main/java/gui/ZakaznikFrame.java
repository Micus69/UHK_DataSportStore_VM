/*
 * Customer registration window.
 * Allows entering basic customer information
 * required for rental and reservation management.
 *
 * Current functionality:
 * - Displays customer registration form
 * - Collects customer contact information
 * - Simulates registration process
 *
 * Note:
 * This frame currently demonstrates
 * GUI functionality only and does not yet
 * store customer data into database.
 */

package gui;

import javax.swing.*;
import java.awt.*;

public class ZakaznikFrame extends JFrame {

    public ZakaznikFrame() {

        setTitle("Registrace zákazníka");

        setSize(650, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();
    }

    /*
     * Creates graphical layout
     * for customer registration form.
     */
    private void initLayout() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                10,
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
         * Customer personal information fields.
         */
        JTextField firstNameField =
                new JTextField();

        JTextField lastNameField =
                new JTextField();

        /*
         * Customer contact information fields.
         */
        JTextField emailField =
                new JTextField();

        JTextField phoneField =
                new JTextField();

        /*
         * Customer address fields.
         */
        JTextField cityField =
                new JTextField();

        JTextField streetField =
                new JTextField();

        JTextField houseNumberField =
                new JTextField();

        JTextField zipCodeField =
                new JTextField();

        /*
         * Customer registration button.
         */
        JButton registerButton =
                new JButton(
                        "Registrovat zákazníka"
                );

        registerButton.addActionListener(
                e -> {

                    /*
                     * Placeholder registration logic.
                     * Future implementation will save
                     * customer data into database.
                     */
                    JOptionPane.showMessageDialog(
                            this,
                            "Zákazník byl úspěšně zaregistrován."
                    );
                }
        );

        /*
         * Registration form layout structure.
         */
        panel.add(new JLabel("Jméno:"));
        panel.add(firstNameField);

        panel.add(new JLabel("Příjmení:"));
        panel.add(lastNameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Telefon:"));
        panel.add(phoneField);

        panel.add(new JLabel("Město:"));
        panel.add(cityField);

        panel.add(new JLabel("Ulice:"));
        panel.add(streetField);

        panel.add(new JLabel("Číslo popisné:"));
        panel.add(houseNumberField);

        panel.add(new JLabel("PSČ:"));
        panel.add(zipCodeField);

        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);
    }
}