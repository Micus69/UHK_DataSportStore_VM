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

    private final Zamestnanec zamestnanec;
    private final VypujckaRepository repository;

    private JTable table;
    private DefaultTableModel tableModel;

    public RezervacePrehledFrame(Zamestnanec zamestnanec) {
        this.zamestnanec = zamestnanec;
        this.repository = new VypujckaRepository();

        setTitle("Aktivní rezervace");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadReservations();
    }

    private void initLayout() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Aktivní rezervace", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

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
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton createRentalButton = new JButton("Vytvořit výpůjčku z rezervace");
        createRentalButton.addActionListener(e -> createRentalFromSelectedReservation());

        JButton refreshButton = new JButton("Obnovit");
        refreshButton.addActionListener(e -> loadReservations());

        JButton closeButton = new JButton("Zavřít");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createRentalButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void loadReservations() {
        tableModel.setRowCount(0);

        List<AktivniRezervace> reservations = repository.findActiveReservations();

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

    private void createRentalFromSelectedReservation() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nejprve vyberte rezervaci."
            );
            return;
        }

        int rezervaceID = Integer.parseInt(
                tableModel.getValueAt(selectedRow, 0).toString()
        );

        LocalDate today = LocalDate.now();
        LocalDate plannedReturn = today.plusDays(3);

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

        loadReservations();
    }
}