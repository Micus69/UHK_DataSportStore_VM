/*
 * Employee reservation overview window.
 * Displays active customer reservations
 * and allows employees to create rentals
 * directly from selected reservations.
 */

package gui;

import model.AktivniRezervace;
import model.Zamestnanec;
import repository.VypujckaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RezervacePrehledFrame extends JFrame {

    // Currently logged-in employee.
    private final Zamestnanec zamestnanec;

    // Repository responsible for rental operations.
    private final VypujckaRepository repository;

    // JTable used for reservation overview.
    private JTable table;

    // Table model for reservation data.
    private DefaultTableModel tableModel;

    public RezervacePrehledFrame(
            Zamestnanec zamestnanec
    ) {

        this.zamestnanec = zamestnanec;

        this.repository =
                new VypujckaRepository();

        setTitle("Aktivní rezervace");

        setSize(850, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();

        // Loads reservation data after frame creation.
        loadReservations();
    }

    /*
     * Creates graphical layout for reservation overview.
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
                        "Aktivní rezervace",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        /*
         * JTable structure used for displaying
         * active reservation records.
         */
        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID rezervace",
                        "Zákazník",
                        "Datum od",
                        "Datum do",
                        "Stav rezervace"
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
         * Creates rental from selected reservation.
         */
        JButton createRentalButton =
                new JButton(
                        "Vytvořit výpůjčku z rezervace"
                );

        createRentalButton.addActionListener(
                e -> createRentalFromSelectedReservation()
        );

        // Reloads reservation data from database.
        JButton refreshButton =
                new JButton("Obnovit");

        refreshButton.addActionListener(
                e -> loadReservations()
        );

        // Closes current frame.
        JButton closeButton =
                new JButton("Zavřít");

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(createRentalButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    /*
     * Loads active reservations from repository
     * and inserts records into JTable.
     */
    private void loadReservations() {

        // Clears old table content.
        tableModel.setRowCount(0);

        List<AktivniRezervace> reservations =
                repository.findActiveReservations();

        // Inserts reservation rows into JTable.
        for (AktivniRezervace reservation : reservations) {

            tableModel.addRow(new Object[]{

                    reservation.getRezervaceID(),

                    reservation.getZakaznik(),

                    reservation.getDatumOd(),

                    reservation.getDatumDo(),

                    reservation.getStavRezervace()
            });
        }
    }

    /*
     * Creates rental from selected reservation
     * using stored procedure logic inside repository.
     */
    private void createRentalFromSelectedReservation() {

        int selectedRow = table.getSelectedRow();

        // Prevents rental creation without selection.
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nejprve vyberte rezervaci."
            );

            return;
        }

        /*
         * Reads selected reservation ID.
         */
        int rezervaceID = Integer.parseInt(
                tableModel.getValueAt(
                        selectedRow,
                        0
                ).toString()
        );

        /*
         * Automatically generates rental dates.
         */
        LocalDate today = LocalDate.now();

        LocalDate plannedReturn =
                today.plusDays(3);

        /*
         * Creates rental record in database.
         */
        repository.createRentalFromReservation(
                rezervaceID,
                zamestnanec.getZamestnanecID(),
                today,
                plannedReturn
        );

        JOptionPane.showMessageDialog(
                this,
                "Výpůjčka byla úspěšně vytvořena z rezervace."
        );

        // Reloads reservation overview.
        loadReservations();
    }
}