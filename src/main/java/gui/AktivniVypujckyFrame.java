/*
 * Employee overview window for active rentals.
 * Displays all currently active rentals loaded
 * from database views through repository layer.
 */

package gui;

import model.AktivniVypujcka;
import repository.AktivniVypujckaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AktivniVypujckyFrame extends JFrame {

    // Repository responsible for loading active rentals.
    private final AktivniVypujckaRepository repository;

    // JTable model used for displaying rental data.
    private DefaultTableModel tableModel;

    public AktivniVypujckyFrame() {

        this.repository = new AktivniVypujckaRepository();

        setTitle("Aktivní výpůjčky");
        setSize(1150, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();

        // Loads rental data after frame initialization.
        loadActiveRentals();
    }

    /*
     * Creates graphical layout for active rentals overview.
     */
    private void initLayout() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel titleLabel = new JLabel(
                "Přehled aktivních výpůjček",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Table structure used for rental overview.
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);

        // Improves table readability.
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);

        // Reloads active rentals from database.
        JButton refreshButton = new JButton("Aktualizovat");

        refreshButton.addActionListener(
                e -> loadActiveRentals()
        );

        // Closes current frame.
        JButton closeButton = new JButton("Zavřít");

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    /*
     * Loads active rental data from repository
     * and inserts rows into JTable model.
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
}