/*
 * Repository responsible for equipment management.
 *
 * Main functionality:
 * - Loads equipment records
 * - Creates new equipment
 * - Updates existing equipment
 * - Transfers equipment data between database and GUI
 */

package repository;

import model.Vybaveni;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VybaveniRepository {

    /*
     * Loads all equipment records from database.
     */
    public List<Vybaveni> findAll() {

        // Collection storing equipment records.
        List<Vybaveni> equipmentList =
                new ArrayList<>();

        String sql = """
                SELECT VybaveniID, TypVybaveniID, StavVybaveniID, DatumPorizeni,
                       InventarniCislo, Nazev, Poznamky, Velikost, Znacka
                FROM Vybaveni
                ORDER BY VybaveniID
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            // Converts database rows into model objects.
            while (resultSet.next()) {

                equipmentList.add(new Vybaveni(
                        resultSet.getInt("VybaveniID"),
                        resultSet.getInt("TypVybaveniID"),
                        resultSet.getInt("StavVybaveniID"),
                        resultSet.getDate("DatumPorizeni").toLocalDate(),
                        resultSet.getString("InventarniCislo"),
                        resultSet.getString("Nazev"),
                        resultSet.getString("Poznamky"),
                        resultSet.getString("Velikost"),
                        resultSet.getString("Znacka")
                ));
            }

        } catch (Exception e) {

            // Prints database loading error.
            e.printStackTrace();
        }

        return equipmentList;
    }

    /*
     * Inserts new equipment record into database.
     */
    public void insert(Vybaveni vybaveni) {

        String sql = """
                INSERT INTO Vybaveni
                (TypVybaveniID, StavVybaveniID, DatumPorizeni, InventarniCislo,
                 Nazev, Poznamky, Velikost, Znacka)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            // Maps equipment object values into SQL query.
            statement.setInt(1, vybaveni.getTypVybaveniID());
            statement.setInt(2, vybaveni.getStavVybaveniID());
            statement.setDate(3, Date.valueOf(vybaveni.getDatumPorizeni()));
            statement.setString(4, vybaveni.getInventarniCislo());
            statement.setString(5, vybaveni.getNazev());
            statement.setString(6, vybaveni.getPoznamky());
            statement.setString(7, vybaveni.getVelikost());
            statement.setString(8, vybaveni.getZnacka());

            // Executes INSERT query.
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Equipment insert failed."
            );
        }
    }

    /*
     * Updates existing equipment record.
     */
    public void update(Vybaveni vybaveni) {

        String sql = """
                UPDATE Vybaveni
                SET TypVybaveniID = ?,
                    StavVybaveniID = ?,
                    DatumPorizeni = ?,
                    InventarniCislo = ?,
                    Nazev = ?,
                    Poznamky = ?,
                    Velikost = ?,
                    Znacka = ?
                WHERE VybaveniID = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            // Maps updated equipment values into SQL query.
            statement.setInt(1, vybaveni.getTypVybaveniID());
            statement.setInt(2, vybaveni.getStavVybaveniID());
            statement.setDate(3, Date.valueOf(vybaveni.getDatumPorizeni()));
            statement.setString(4, vybaveni.getInventarniCislo());
            statement.setString(5, vybaveni.getNazev());
            statement.setString(6, vybaveni.getPoznamky());
            statement.setString(7, vybaveni.getVelikost());
            statement.setString(8, vybaveni.getZnacka());
            statement.setInt(9, vybaveni.getVybaveniID());

            // Executes UPDATE query.
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Equipment update failed."
            );
        }
    }
}