package repository;

import model.AktivniRezervace;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VypujckaRepository {

    public List<AktivniRezervace> findActiveReservations() {
        List<AktivniRezervace> reservations = new ArrayList<>();

        String sql = """
                SELECT r.RezervaceID,
                       CONCAT(z.Jmeno, ' ', z.Prijmeni) AS Zakaznik,
                       r.DatumOd,
                       r.DatumDo,
                       r.StavRezervace
                FROM Rezervace r
                         JOIN Zakaznik z ON r.ZakaznikID = z.ZakaznikID
                WHERE r.StavRezervace = 'Aktivni'
                ORDER BY r.DatumOd
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                reservations.add(new AktivniRezervace(
                        resultSet.getInt("RezervaceID"),
                        resultSet.getString("Zakaznik"),
                        resultSet.getDate("DatumOd").toString(),
                        resultSet.getDate("DatumDo").toString(),
                        resultSet.getString("StavRezervace")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public void createRentalFromReservation(int rezervaceID,
                                            int zamestnanecID,
                                            LocalDate datumVypujceni,
                                            LocalDate planovaneVraceni) {

        String sql = "{CALL VytvorVypujckuZRezervace(?, ?, ?, ?)}";

        try (
                Connection connection = DBConnection.getConnection();
                CallableStatement statement = connection.prepareCall(sql)
        ) {
            statement.setInt(1, rezervaceID);
            statement.setInt(2, zamestnanecID);
            statement.setDate(3, Date.valueOf(datumVypujceni));
            statement.setDate(4, Date.valueOf(planovaneVraceni));

            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Rental creation from reservation failed.");
        }
    }
}