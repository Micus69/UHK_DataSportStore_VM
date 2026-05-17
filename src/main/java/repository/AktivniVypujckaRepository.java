/*
 * Repository responsible for active rental operations.
 *
 * Main functionality:
 * - Loads active rentals from database view
 * - Calls stored procedure for rental return
 * - Transfers rental data between database and GUI
 */

package repository;

import model.AktivniVypujcka;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AktivniVypujckaRepository {

    /*
     * Loads all active rentals from database view.
     * View Pohled_AktivniVypujcky already joins all required tables.
     */
    public List<AktivniVypujcka> findAllActiveRentals() {
        List<AktivniVypujcka> rentals = new ArrayList<>();

        String sql = """
                SELECT VypujckaID,
                       ZakaznikJmeno,
                       ZakaznikPrijmeni,
                       Email,
                       NazevVybaveni,
                       InventarniCislo,
                       NazevTypu,
                       CenaZaDen,
                       PocetDni,
                       CenaPolozky,
                       CenaCelkem,
                       DatumVypujceni,
                       PlanovaneVraceni,
                       StavVypujcky
                FROM Pohled_AktivniVypujcky
                ORDER BY VypujckaID DESC
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                rentals.add(new AktivniVypujcka(
                        resultSet.getInt("VypujckaID"),
                        resultSet.getString("ZakaznikJmeno"),
                        resultSet.getString("ZakaznikPrijmeni"),
                        resultSet.getString("Email"),
                        resultSet.getString("NazevVybaveni"),
                        resultSet.getString("InventarniCislo"),
                        resultSet.getString("NazevTypu"),
                        resultSet.getDouble("CenaZaDen"),
                        resultSet.getInt("PocetDni"),
                        resultSet.getDouble("CenaPolozky"),
                        resultSet.getDouble("CenaCelkem"),
                        resultSet.getDate("DatumVypujceni").toString(),
                        resultSet.getDate("PlanovaneVraceni").toString(),
                        resultSet.getString("StavVypujcky")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rentals;
    }

    /*
     * Returns selected rental using stored procedure VratVypujcku.
     * The procedure updates rental return date and rental status.
     */
    public void returnRental(int vypujckaID, LocalDate returnDate) {
        String sql = "{CALL VratVypujcku(?, ?)}";

        try (
                Connection connection = DBConnection.getConnection();
                CallableStatement statement = connection.prepareCall(sql)
        ) {
            statement.setInt(1, vypujckaID);
            statement.setDate(2, Date.valueOf(returnDate));
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Returning rental failed.");
        }
    }
}