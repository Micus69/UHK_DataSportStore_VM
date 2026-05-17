/*
 * Equipment return management window.
 * Displays all active rentals
 * and allows employees to return
 * rented equipment back into the system.
 */

package gui;

import model.AktivniVypujcka;
import repository.AktivniVypujckaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class VraceniFrame extends JFrame {

    // Repository responsible for rental return operations.
    private final AktivniVypujckaRepository repository;

    // JTable displaying active rental records.
    private JTable table;

    // Table model for rental overview data.
    private DefaultTableModel tableModel;

    public VraceniFrame() {

        this.repository =
                new AktivniVypujckaRepository();

        setTitle("Vrácení vybavení");

        setSize(1150, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();

        // Loads active rentals after frame creation.
        loadActiveRentals();
    }

    /*
     * Creates graphical layout
     * for active rental overview.
     */
    private void initLayout() {

        JPanel panel =
                new JPanel(new BorderLayout(10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Vrácení vybavení",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        /*
         * JTable structure used for displaying
         * active rental records.
         */
        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID výpůjčky",
                        "Zákazník",
                        "Email",
                        "Vybavení",
                        "Inventární číslo",
                        "Typ",
                        "Počet dní",
                        "Cena položky",
                        "Celková cena",
                        "Datum výpůjčky",
                        "Plánované vrácení",
                        "Stav"
                },
                0
        ) {

            // Table is read-only.
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        table = new JTable(tableModel);

        // Improves table readability.
        table.setRowHeight(24);

        JScrollPane scrollPane =
                new JScrollPane(table);

        /*
         * Returns selected rental
         * and updates equipment state.
         */
        JButton returnButton =
                new JButton(
                        "Vrátit vybranou výpůjčku"
                );

        returnButton.addActionListener(
                e -> returnSelectedRental()
        );

        // Reloads rental data from database.
        JButton refreshButton =
                new JButton("Obnovit");

        refreshButton.addActionListener(
                e -> loadActiveRentals()
        );

        // Closes current frame.
        JButton closeButton =
                new JButton("Zavřít");

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(returnButton);

        buttonPanel.add(refreshButton);

        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);

        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    /*
     * Loads active rentals from repository
     * and inserts them into JTable.
     */
    private void loadActiveRentals() {

        // Clears old table content.
        tableModel.setRowCount(0);

        List<AktivniVypujcka> rentals =
                repository.findAllActiveRentals();

        // Inserts rental records into JTable.
        for (AktivniVypujcka rental : rentals) {

            tableModel.addRow(new Object[]{

                    rental.getVypujckaID(),

                    rental.getZakaznikJmeno()
                            + " "
                            + rental.getZakaznikPrijmeni(),

                    rental.getEmail(),

                    rental.getNazevVybaveni(),

                    rental.getInventarniCislo(),

                    rental.getNazevTypu(),

                    rental.getPocetDni(),

                    rental.getCenaPolozky(),

                    rental.getCenaCelkem(),

                    rental.getDatumVypujceni(),

                    rental.getPlanovaneVraceni(),

                    rental.getStavVypujcky()
            });
        }
    }

    /*
     * Returns selected rental
     * and updates rental state in database.
     */
    private void returnSelectedRental() {

        int selectedRow =
                table.getSelectedRow();

        // Prevents return operation without selection.
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nejprve vyberte výpůjčku."
            );

            return;
        }

        /*
         * Reads selected rental ID.
         */
        int vypujckaID = Integer.parseInt(
                tableModel.getValueAt(
                        selectedRow,
                        0
                ).toString()
        );

        /*
         * Displays confirmation dialog
         * before rental return.
         */
        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Opravdu chcete vrátit výpůjčku ID "
                                + vypujckaID
                                + "?",
                        "Potvrzení vrácení",
                        JOptionPane.YES_NO_OPTION
                );

        // Cancels operation if user declines.
        if (result != JOptionPane.YES_OPTION) {

            return;
        }

        /*
         * Updates rental return date
         * and changes equipment state.
         */
        repository.returnRental(
                vypujckaID,
                LocalDate.now()
        );

        JOptionPane.showMessageDialog(
                this,
                "Výpůjčka byla úspěšně vrácena."
        );

        // Reloads updated rental overview.
        loadActiveRentals();
    }
}