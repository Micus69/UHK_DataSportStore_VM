package gui;

import model.Vybaveni;
import repository.VybaveniRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class VybaveniManagementFrame extends JFrame {

    private final VybaveniRepository repository;

    private JTable table;
    private DefaultTableModel tableModel;

    public VybaveniManagementFrame() {
        this.repository = new VybaveniRepository();

        setTitle("Správa vybavení");
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadEquipment();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Správa vybavení", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

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
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton addButton = new JButton("Přidat vybavení");
        addButton.addActionListener(e -> addEquipment());

        JButton saveButton = new JButton("Uložit vybrané");
        saveButton.addActionListener(e -> saveSelectedEquipment());

        JButton refreshButton = new JButton("Obnovit");
        refreshButton.addActionListener(e -> loadEquipment());

        JButton closeButton = new JButton("Zavřít");
        closeButton.addActionListener(e -> dispose());

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

    private void loadEquipment() {
        tableModel.setRowCount(0);

        List<Vybaveni> equipmentList = repository.findAll();

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

    private void addEquipment() {

        Vybaveni vybaveni = new Vybaveni(
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

        repository.insert(vybaveni);

        loadEquipment();

        JOptionPane.showMessageDialog(
                this,
                "Vybavení bylo úspěšně přidáno."
        );
    }

    private void saveSelectedEquipment() {

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

        Vybaveni vybaveni = new Vybaveni(
                Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
                Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString()),
                Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString()),
                LocalDate.parse(tableModel.getValueAt(selectedRow, 3).toString()),
                tableModel.getValueAt(selectedRow, 4).toString(),
                tableModel.getValueAt(selectedRow, 5).toString(),
                tableModel.getValueAt(selectedRow, 6).toString(),
                tableModel.getValueAt(selectedRow, 7).toString(),
                tableModel.getValueAt(selectedRow, 8).toString()
        );

        repository.update(vybaveni);

        loadEquipment();

        JOptionPane.showMessageDialog(
                this,
                "Vybavení bylo úspěšně aktualizováno."
        );
    }
}