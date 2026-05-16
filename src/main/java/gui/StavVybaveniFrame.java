package gui;

import model.StavVybaveni;
import repository.StavVybaveniRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StavVybaveniFrame extends JFrame {

    private final StavVybaveniRepository repository;
    private DefaultTableModel tableModel;
    private JTable table;

    public StavVybaveniFrame() {
        this.repository = new StavVybaveniRepository();

        setTitle("Stavy vybavení");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadDataFromDatabase();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Přehled stavů vybavení", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

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

        JScrollPane scrollPane = new JScrollPane(table);

        JButton saveButton = new JButton("Uložit");
        saveButton.addActionListener(e -> pushDataToDatabase());

        JButton closeButton = new JButton("Zavřít");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void loadDataFromDatabase() {
        tableModel.setRowCount(0);

        List<StavVybaveni> states = repository.findAll();

        for (StavVybaveni state : states) {
            tableModel.addRow(new Object[]{
                    state.getStavVybaveniID(),
                    state.getNazevStavu(),
                    state.isJeDostupneProPujceni(),
                    state.getPopisStavu()
            });
        }
    }

    private void pushDataToDatabase() {

        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nejprve vyberte řádek."
            );
            return;
        }

        StavVybaveni state = new StavVybaveni();

        state.setStavVybaveniID(
                Integer.parseInt(
                        tableModel.getValueAt(selectedRow, 0).toString()
                )
        );

        state.setNazevStavu(
                tableModel.getValueAt(selectedRow, 1).toString()
        );

        state.setJeDostupneProPujceni(
                Boolean.parseBoolean(
                        tableModel.getValueAt(selectedRow, 2).toString()
                )
        );

        state.setPopisStavu(
                tableModel.getValueAt(selectedRow, 3).toString()
        );

        repository.update(state);

        JOptionPane.showMessageDialog(
                this,
                "Data byla úspěšně aktualizována."
        );

        loadDataFromDatabase();
    }
}