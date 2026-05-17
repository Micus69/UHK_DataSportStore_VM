/*
 * Equipment state management window.
 * Allows administrators to manage
 * equipment availability states
 * stored inside the database.
 */

package gui;

import model.StavVybaveni;
import repository.StavVybaveniRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StavVybaveniFrame extends JFrame {

    // Repository responsible for equipment state operations.
    private final StavVybaveniRepository repository;

    // JTable model used for displaying equipment states.
    private DefaultTableModel tableModel;

    // Table component displaying state records.
    private JTable table;

    public StavVybaveniFrame() {

        this.repository =
                new StavVybaveniRepository();

        setTitle("Stavy vybavení");

        setSize(700, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        initLayout();

        // Loads equipment states after frame creation.
        loadDataFromDatabase();
    }

    /*
     * Creates graphical layout
     * for equipment state management.
     */
    private void initLayout() {

        JPanel panel =
                new JPanel(new BorderLayout());

        JLabel titleLabel =
                new JLabel(
                        "Přehled stavů vybavení",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        /*
         * JTable structure used for displaying
         * equipment state records.
         */
        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Název stavu",
                        "Dostupné",
                        "Popis"
                },
                0
        );

        table = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        // Saves edited equipment state into database.
        JButton saveButton =
                new JButton("Uložit");

        saveButton.addActionListener(
                e -> pushDataToDatabase()
        );

        // Closes current window.
        JButton closeButton =
                new JButton("Zavřít");

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);

        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    /*
     * Loads equipment state data
     * from database into JTable.
     */
    private void loadDataFromDatabase() {

        // Clears old table content.
        tableModel.setRowCount(0);

        List<StavVybaveni> states =
                repository.findAll();

        // Inserts state records into JTable.
        for (StavVybaveni state : states) {

            tableModel.addRow(new Object[]{

                    state.getStavVybaveniID(),

                    state.getNazevStavu(),

                    state.isJeDostupneProPujceni(),

                    state.getPopisStavu()
            });
        }
    }

    /*
     * Reads selected row values
     * and updates equipment state in database.
     */
    private void pushDataToDatabase() {

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
         * Creates equipment state entity
         * from selected JTable row.
         */
        StavVybaveni state =
                new StavVybaveni();

        state.setStavVybaveniID(
                Integer.parseInt(
                        tableModel.getValueAt(
                                selectedRow,
                                0
                        ).toString()
                )
        );

        state.setNazevStavu(
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString()
        );

        state.setJeDostupneProPujceni(
                Boolean.parseBoolean(
                        tableModel.getValueAt(
                                selectedRow,
                                2
                        ).toString()
                )
        );

        state.setPopisStavu(
                tableModel.getValueAt(
                        selectedRow,
                        3
                ).toString()
        );

        // Updates database record.
        repository.update(state);

        JOptionPane.showMessageDialog(
                this,
                "Data byla úspěšně aktualizována."
        );

        // Reloads updated data from database.
        loadDataFromDatabase();
    }
}