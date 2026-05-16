package gui;

import model.DostupneVybaveni;
import repository.DostupneVybaveniRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DostupneVybaveniFrame extends JFrame {

    private final DostupneVybaveniRepository repository;
    private DefaultTableModel tableModel;

    public DostupneVybaveniFrame() {
        this.repository = new DostupneVybaveniRepository();

        setTitle("Dostupné vybavení");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadDataFromDatabase();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Přehled dostupného vybavení", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

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
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Zákaznický pohled je pouze pro čtení.
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = new JButton("Aktualizovat");
        refreshButton.addActionListener(e -> loadDataFromDatabase());

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

    private void loadDataFromDatabase() {
        tableModel.setRowCount(0);

        List<DostupneVybaveni> equipmentList = repository.findAllAvailable();

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