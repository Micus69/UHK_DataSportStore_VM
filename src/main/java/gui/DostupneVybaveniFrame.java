/*
 * Customer equipment overview window.
 * Displays all currently available equipment
 * loaded from database views through repository layer.
 */

package gui;

import model.DostupneVybaveni;
import repository.DostupneVybaveniRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DostupneVybaveniFrame extends JFrame {

    // Repository responsible for loading available equipment.
    private final DostupneVybaveniRepository repository;

    // JTable model used for displaying equipment data.
    private DefaultTableModel tableModel;

    public DostupneVybaveniFrame() {

        this.repository = new DostupneVybaveniRepository();

        setTitle("Dostupné vybavení");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();

        // Loads equipment data immediately after frame creation.
        loadDataFromDatabase();
    }

    /*
     * Creates graphical layout for equipment overview.
     */
    private void initLayout() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel =
                new JLabel(
                        "Přehled dostupného vybavení",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Table structure used for customer equipment overview.
        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Název",
                        "Inventární číslo",
                        "Značka",
                        "Velikost",
                        "Typ",
                        "Cena / den",
                        "Stav"
                },
                0
        ) {

            // Customer table is read-only.
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        // Reloads equipment data from database.
        JButton refreshButton = new JButton("Aktualizovat");
        refreshButton.addActionListener(e -> loadDataFromDatabase());

        // Closes current frame.
        JButton closeButton = new JButton("Zpět");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    /*
     * Loads available equipment from repository
     * and displays data inside JTable.
     */
    private void loadDataFromDatabase() {

        // Clears old table content.
        tableModel.setRowCount(0);

        List<DostupneVybaveni> equipmentList =
                repository.findAllAvailable();

        // Inserts equipment rows into JTable model.
        for (DostupneVybaveni equipment : equipmentList) {

            tableModel.addRow(new Object[]{
                    equipment.getVybaveniID(),
                    equipment.getNazev(),
                    equipment.getInventarniCislo(),
                    equipment.getZnacka(),
                    equipment.getVelikost(),
                    equipment.getNazevTypu(),
                    equipment.getCenaZaDen(),
                    equipment.getNazevStavu()
            });
        }
    }
}