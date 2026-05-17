package gui;

import model.Vybaveni;
import repository.VybaveniRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/*
 * Equipment management window.
 * Allows administrators to manage
 * equipment records stored in the system.
 *
 * Main functionality:
 * - Displays all equipment
 * - Adds new equipment
 * - Updates selected equipment
 * - Reloads equipment data from database
 */

public class VybaveniManagementFrame extends JFrame {

    // Repository responsible for equipment database operations.
    private final VybaveniRepository repository;

    // JTable displaying equipment overview.
    private JTable table;

    // Table model used for equipment data.
    private DefaultTableModel tableModel;

    public VybaveniManagementFrame() {

        this.repository =
                new VybaveniRepository();

        setTitle("Správa vybavení");

        setSize(1100, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();

        // Loads equipment data after frame creation.
        loadEquipment();
    }

    /*
     * Creates graphical layout
     * for equipment management.
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
                        "Správa vybavení",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        /*
         * JTable structure used for displaying
         * equipment records.
         */
        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "ID typu",
                        "ID stavu",
                        "Datum pořízení",
                        "Inventární číslo",
                        "Název",
                        "Poznámky",
                        "Velikost",
                        "Značka"
                },
                0
        );

        table = new JTable(tableModel);

        // Improves table readability.
        table.setRowHeight(24);

        JScrollPane scrollPane =
                new JScrollPane(table);

        /*
         * Creates new equipment record
         * with default values.
         */
        JButton addButton =
                new JButton("Přidat vybavení");

        addButton.addActionListener(
                e -> addEquipment()
        );

        /*
         * Saves selected equipment changes
         * into database.
         */
        JButton saveButton =
                new JButton("Uložit vybrané");

        saveButton.addActionListener(
                e -> saveSelectedEquipment()
        );

        // Reloads equipment data from database.
        JButton refreshButton =
                new JButton("Obnovit");

        refreshButton.addActionListener(
                e -> loadEquipment()
        );

        // Closes current frame.
        JButton closeButton =
                new JButton("Zavřít");

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);

        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    /*
     * Loads equipment records from database
     * and inserts them into JTable.
     */
    private void loadEquipment() {

        // Clears old table content.
        tableModel.setRowCount(0);

        List<Vybaveni> equipmentList =
                repository.findAll();

        // Inserts equipment records into JTable.
        for (Vybaveni equipment : equipmentList) {

            tableModel.addRow(new Object[]{

                    equipment.getVybaveniID(),

                    equipment.getTypVybaveniID(),

                    equipment.getStavVybaveniID(),

                    equipment.getDatumPorizeni(),

                    equipment.getInventarniCislo(),

                    equipment.getNazev(),

                    equipment.getPoznamky(),

                    equipment.getVelikost(),

                    equipment.getZnacka()
            });
        }
    }

    /*
     * Creates new equipment entity
     * and stores it into database.
     */
    private void addEquipment() {

        Vybaveni vybaveni =
                new Vybaveni(
                        0,
                        1,
                        1,
                        LocalDate.now(),
                        "NEW-001",
                        "Nové vybavení",
                        "",
                        "M",
                        "Značka"
                );

        // Inserts new equipment into database.
        repository.insert(vybaveni);

        // Reloads updated equipment overview.
        loadEquipment();

        JOptionPane.showMessageDialog(
                this,
                "Vybavení bylo úspěšně přidáno."
        );
    }

    /*
     * Reads selected JTable row,
     * creates equipment entity
     * and updates database record.
     */
    private void saveSelectedEquipment() {

        // Stops active cell editing before reading values.
        if (table.isEditing()) {

            table.getCellEditor()
                    .stopCellEditing();
        }

        int selectedRow =
                table.getSelectedRow();

        // Prevents saving without row selection.
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nejprve vyberte řádek."
            );

            return;
        }

        /*
         * Creates equipment entity object
         * from selected JTable row.
         */
        Vybaveni vybaveni =
                new Vybaveni(

                        Integer.parseInt(
                                tableModel.getValueAt(
                                        selectedRow,
                                        0
                                ).toString()
                        ),

                        Integer.parseInt(
                                tableModel.getValueAt(
                                        selectedRow,
                                        1
                                ).toString()
                        ),

                        Integer.parseInt(
                                tableModel.getValueAt(
                                        selectedRow,
                                        2
                                ).toString()
                        ),

                        LocalDate.parse(
                                tableModel.getValueAt(
                                        selectedRow,
                                        3
                                ).toString()
                        ),

                        tableModel.getValueAt(
                                selectedRow,
                                4
                        ).toString(),

                        tableModel.getValueAt(
                                selectedRow,
                                5
                        ).toString(),

                        tableModel.getValueAt(
                                selectedRow,
                                6
                        ).toString(),

                        tableModel.getValueAt(
                                selectedRow,
                                7
                        ).toString(),

                        tableModel.getValueAt(
                                selectedRow,
                                8
                        ).toString()
                );

        // Updates equipment record in database.
        repository.update(vybaveni);

        // Reloads updated equipment overview.
        loadEquipment();

        JOptionPane.showMessageDialog(
                this,
                "Vybavení bylo úspěšně aktualizováno."
        );
    }
}