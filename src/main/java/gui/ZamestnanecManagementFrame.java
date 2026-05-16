package gui;

import model.ZamestnanecAdmin;
import repository.ZamestnanecRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ZamestnanecManagementFrame extends JFrame {

    private final ZamestnanecRepository repository;

    private JTable table;
    private DefaultTableModel tableModel;

    public ZamestnanecManagementFrame() {
        this.repository = new ZamestnanecRepository();

        setTitle("Správa zaměstnanců");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadEmployees();
    }

    private void initLayout() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Správa zaměstnanců", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Datum nástupu",
                        "Heslo",
                        "Jméno",
                        "Login",
                        "Pozice",
                        "Příjmení",
                        "Role"
                },
                0
        );

        table = new JTable(tableModel);
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton addButton = new JButton("Přidat zaměstnance");
        addButton.addActionListener(e -> addEmployee());

        JButton saveButton = new JButton("Uložit vybraného");
        saveButton.addActionListener(e -> saveSelectedEmployee());

        JButton refreshButton = new JButton("Obnovit");
        refreshButton.addActionListener(e -> loadEmployees());

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

    private void loadEmployees() {

        tableModel.setRowCount(0);

        List<ZamestnanecAdmin> employees = repository.findAll();

        for (ZamestnanecAdmin employee : employees) {
            tableModel.addRow(new Object[]{
                    employee.getZamestnanecID(),
                    employee.getDatumNastupu(),
                    employee.getHeslo(),
                    employee.getJmeno(),
                    employee.getLogin(),
                    employee.getPozice(),
                    employee.getPrijmeni(),
                    employee.getRole()
            });
        }
    }

    private void addEmployee() {

        ZamestnanecAdmin employee = new ZamestnanecAdmin(
                0,
                LocalDate.now(),
                "password123",
                "Nový",
                "novy_login",
                "Zaměstnanec",
                "Zaměstnanec",
                "EMPLOYEE"
        );

        repository.insert(employee);

        loadEmployees();

        JOptionPane.showMessageDialog(
                this,
                "Zaměstnanec byl úspěšně přidán."
        );
    }

    private void saveSelectedEmployee() {

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

        ZamestnanecAdmin employee = new ZamestnanecAdmin(
                Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
                LocalDate.parse(tableModel.getValueAt(selectedRow, 1).toString()),
                tableModel.getValueAt(selectedRow, 2).toString(),
                tableModel.getValueAt(selectedRow, 3).toString(),
                tableModel.getValueAt(selectedRow, 4).toString(),
                tableModel.getValueAt(selectedRow, 5).toString(),
                tableModel.getValueAt(selectedRow, 6).toString(),
                tableModel.getValueAt(selectedRow, 7).toString()
        );

        repository.update(employee);

        loadEmployees();

        JOptionPane.showMessageDialog(
                this,
                "Zaměstnanec byl úspěšně aktualizován."
        );
    }
}