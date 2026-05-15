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

        setTitle("Available Equipment");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadDataFromDatabase();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Available Equipment Overview", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Name",
                        "Inventory No.",
                        "Brand",
                        "Size",
                        "Type",
                        "Price / Day",
                        "Status"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Customer view is read-only.
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadDataFromDatabase());

        JButton closeButton = new JButton("Back");
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