package gui;

import model.DostupneVybaveni;
import model.Rezervace;
import repository.DostupneVybaveniRepository;
import repository.RezervaceRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RezervaceFrame extends JFrame {

    private final RezervaceRepository rezervaceRepository;
    private final DostupneVybaveniRepository vybaveniRepository;

    private JTextField jmenoField;
    private JTextField prijmeniField;
    private JTextField emailField;
    private JTextField telefonField;
    private JTextField uliceField;
    private JTextField cisloPopisneField;
    private JTextField mestoField;
    private JTextField pscField;

    private JComboBox<Integer> dayFromBox;
    private JComboBox<Integer> monthFromBox;
    private JComboBox<Integer> yearFromBox;

    private JComboBox<Integer> dayToBox;
    private JComboBox<Integer> monthToBox;
    private JComboBox<Integer> yearToBox;

    private JTable equipmentTable;
    private DefaultTableModel tableModel;

    public RezervaceFrame() {
        this.rezervaceRepository = new RezervaceRepository();
        this.vybaveniRepository = new DostupneVybaveniRepository();

        setTitle("Vytvoření rezervace");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadAvailableEquipment();
    }

    private void initLayout() {
        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Vytvoření zákaznické rezervace", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel customerPanel = createCustomerPanel();

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(customerPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(
                new Object[]{
                        "Vybrat",
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
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        equipmentTable = new JTable(tableModel);
        equipmentTable.setRowHeight(24);

        equipmentTable.getColumnModel().getColumn(0).setPreferredWidth(55);
        equipmentTable.getColumnModel().getColumn(1).setPreferredWidth(40);
        equipmentTable.getColumnModel().getColumn(2).setPreferredWidth(180);
        equipmentTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        equipmentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        equipmentTable.getColumnModel().getColumn(5).setPreferredWidth(70);
        equipmentTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        equipmentTable.getColumnModel().getColumn(7).setPreferredWidth(90);
        equipmentTable.getColumnModel().getColumn(8).setPreferredWidth(90);

        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Dostupné vybavení"));

        JButton createButton = new JButton("Vytvořit rezervaci");
        createButton.addActionListener(e -> createReservation());

        JButton closeButton = new JButton("Zpět");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.add(createButton);
        buttonPanel.add(closeButton);

        rootPanel.add(topPanel, BorderLayout.NORTH);
        rootPanel.add(scrollPane, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(rootPanel);
    }

    private JPanel createCustomerPanel() {
        JPanel customerPanel = new JPanel(new GridBagLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Informace o zákazníkovi"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        jmenoField = new JTextField(18);
        prijmeniField = new JTextField(18);
        emailField = new JTextField(18);
        telefonField = new JTextField(18);
        uliceField = new JTextField(18);
        cisloPopisneField = new JTextField(18);
        mestoField = new JTextField(18);
        pscField = new JTextField(18);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        dayFromBox = createDayBox();
        monthFromBox = createMonthBox();
        yearFromBox = createYearBox();

        dayToBox = createDayBox();
        monthToBox = createMonthBox();
        yearToBox = createYearBox();

        dayFromBox.setSelectedItem(today.getDayOfMonth());
        monthFromBox.setSelectedItem(today.getMonthValue());
        yearFromBox.setSelectedItem(today.getYear());

        dayToBox.setSelectedItem(tomorrow.getDayOfMonth());
        monthToBox.setSelectedItem(tomorrow.getMonthValue());
        yearToBox.setSelectedItem(tomorrow.getYear());

        JPanel dateFromPanel = createDatePanel(dayFromBox, monthFromBox, yearFromBox);
        JPanel dateToPanel = createDatePanel(dayToBox, monthToBox, yearToBox);

        addFormRow(customerPanel, gbc, 0,
                "Jméno:", jmenoField,
                "Příjmení:", prijmeniField);

        addFormRow(customerPanel, gbc, 1,
                "Email:", emailField,
                "Telefon:", telefonField);

        addFormRow(customerPanel, gbc, 2,
                "Ulice:", uliceField,
                "Číslo popisné:", cisloPopisneField);

        addFormRow(customerPanel, gbc, 3,
                "Město:", mestoField,
                "PSČ:", pscField);

        addFormRowPanel(customerPanel, gbc, 4,
                "Datum od:", dateFromPanel,
                "Datum do:", dateToPanel);

        return customerPanel;
    }

    private JPanel createDatePanel(JComboBox<Integer> dayBox,
                                   JComboBox<Integer> monthBox,
                                   JComboBox<Integer> yearBox) {

        JPanel panel = new JPanel(new GridLayout(1, 3, 5, 0));
        panel.add(dayBox);
        panel.add(monthBox);
        panel.add(yearBox);

        return panel;
    }

    private JComboBox<Integer> createDayBox() {
        JComboBox<Integer> box = new JComboBox<>();

        for (int i = 1; i <= 31; i++) {
            box.addItem(i);
        }

        return box;
    }

    private JComboBox<Integer> createMonthBox() {
        JComboBox<Integer> box = new JComboBox<>();

        for (int i = 1; i <= 12; i++) {
            box.addItem(i);
        }

        return box;
    }

    private JComboBox<Integer> createYearBox() {
        JComboBox<Integer> box = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();

        for (int i = currentYear; i <= currentYear + 5; i++) {
            box.addItem(i);
        }

        return box;
    }

    private LocalDate getDateFromBoxes(JComboBox<Integer> dayBox,
                                       JComboBox<Integer> monthBox,
                                       JComboBox<Integer> yearBox) {

        int day = (Integer) dayBox.getSelectedItem();
        int month = (Integer) monthBox.getSelectedItem();
        int year = (Integer) yearBox.getSelectedItem();

        return LocalDate.of(year, month, day);
    }

    private void addFormRow(JPanel panel,
                            GridBagConstraints gbc,
                            int row,
                            String label1,
                            JTextField field1,
                            String label2,
                            JTextField field2) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(new JLabel(label1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field1, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(new JLabel(label2), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        panel.add(field2, gbc);
    }

    private void addFormRowPanel(JPanel panel,
                                 GridBagConstraints gbc,
                                 int row,
                                 String label1,
                                 JPanel panel1,
                                 String label2,
                                 JPanel panel2) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(new JLabel(label1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(panel1, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(new JLabel(label2), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        panel.add(panel2, gbc);
    }

    private void loadAvailableEquipment() {
        tableModel.setRowCount(0);

        List<DostupneVybaveni> equipmentList = vybaveniRepository.findAllAvailable();

        for (DostupneVybaveni equipment : equipmentList) {
            tableModel.addRow(new Object[]{
                    false,
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

    private void createReservation() {
        try {

            if (equipmentTable.isEditing()) {
                equipmentTable.getCellEditor().stopCellEditing();
            }

            LocalDate datumOd = getDateFromBoxes(dayFromBox, monthFromBox, yearFromBox);
            LocalDate datumDo = getDateFromBoxes(dayToBox, monthToBox, yearToBox);

            if (datumOd.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this,
                        "Datum od nemůže být v minulosti.");
                return;
            }

            if (datumDo.isBefore(datumOd)) {
                JOptionPane.showMessageDialog(this,
                        "Datum do nemůže být před datem od.");
                return;
            }

            List<Integer> selectedEquipmentIds = new ArrayList<>();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Boolean selected = (Boolean) tableModel.getValueAt(i, 0);

                if (Boolean.TRUE.equals(selected)) {
                    int vybaveniID = Integer.parseInt(
                            tableModel.getValueAt(i, 1).toString()
                    );

                    selectedEquipmentIds.add(vybaveniID);
                }
            }

            if (selectedEquipmentIds.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vyberte alespoň jedno vybavení.");
                return;
            }

            Rezervace rezervace = new Rezervace(
                    jmenoField.getText(),
                    prijmeniField.getText(),
                    emailField.getText(),
                    telefonField.getText(),
                    uliceField.getText(),
                    cisloPopisneField.getText(),
                    mestoField.getText(),
                    pscField.getText(),
                    2,
                    datumOd,
                    datumDo,
                    selectedEquipmentIds
            );

            rezervaceRepository.createReservation(rezervace);

            JOptionPane.showMessageDialog(this,
                    "Rezervace byla úspěšně vytvořena.");

            loadAvailableEquipment();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vytvoření rezervace selhalo. Zkontrolujte zadané údaje.",
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}