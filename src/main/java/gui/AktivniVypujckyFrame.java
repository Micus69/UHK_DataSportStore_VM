package gui;

import model.AktivniVypujcka;
import repository.AktivniVypujckaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AktivniVypujckyFrame extends JFrame {

    private final AktivniVypujckaRepository repository;
    private DefaultTableModel tableModel;

    public AktivniVypujckyFrame() {

        this.repository = new AktivniVypujckaRepository();

        setTitle("Aktivní výpůjčky");
        setSize(1150, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initLayout();
        loadActiveRentals();
    }

    private void initLayout() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(
                "Přehled aktivních výpůjček",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID výpůjčky",
                        "Zákazník",
                        "Email",
                        "Vybavení",
                        "Inventární číslo",
                        "Typ",
                        "Počet dní",
                        "Cena položky",
                        "Celková cena",
                        "Datum výpůjčky",
                        "Plánované vrácení",
                        "Stav"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshButton = new JButton("Aktualizovat");
        refreshButton.addActionListener(e -> loadActiveRentals());

        JButton closeButton = new JButton("Zavřít");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void loadActiveRentals() {

        tableModel.setRowCount(0);

        List<AktivniVypujcka> rentals = repository.findAllActiveRentals();

        for (AktivniVypujcka rental : rentals) {

            tableModel.addRow(new Object[]{
                    rental.getVypujckaID(),
                    rental.getZakaznikJmeno()
                            + " "
                            + rental.getZakaznikPrijmeni(),
                    rental.getEmail(),
                    rental.getNazevVybaveni(),
                    rental.getInventarniCislo(),
                    rental.getNazevTypu(),
                    rental.getPocetDni(),
                    rental.getCenaPolozky(),
                    rental.getCenaCelkem(),
                    rental.getDatumVypujceni(),
                    rental.getPlanovaneVraceni(),
                    rental.getStavVypujcky()
            });
        }
    }
}