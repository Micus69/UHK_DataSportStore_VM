package repository;

import model.Vybaveni;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VybaveniRepository {

    public List<Vybaveni> findAll() {
        List<Vybaveni> equipmentList = new ArrayList<>();

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
            e.printStackTrace();
        }

        return equipmentList;
    }

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
            statement.setInt(1, vybaveni.getTypVybaveniID());
            statement.setInt(2, vybaveni.getStavVybaveniID());
            statement.setDate(3, Date.valueOf(vybaveni.getDatumPorizeni()));
            statement.setString(4, vybaveni.getInventarniCislo());
            statement.setString(5, vybaveni.getNazev());
            statement.setString(6, vybaveni.getPoznamky());
            statement.setString(7, vybaveni.getVelikost());
            statement.setString(8, vybaveni.getZnacka());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Equipment insert failed.");
        }
    }

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
            statement.setInt(1, vybaveni.getTypVybaveniID());
            statement.setInt(2, vybaveni.getStavVybaveniID());
            statement.setDate(3, Date.valueOf(vybaveni.getDatumPorizeni()));
            statement.setString(4, vybaveni.getInventarniCislo());
            statement.setString(5, vybaveni.getNazev());
            statement.setString(6, vybaveni.getPoznamky());
            statement.setString(7, vybaveni.getVelikost());
            statement.setString(8, vybaveni.getZnacka());
            statement.setInt(9, vybaveni.getVybaveniID());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Equipment update failed.");
        }
    }
}