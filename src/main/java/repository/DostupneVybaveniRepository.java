package repository;

import model.DostupneVybaveni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DostupneVybaveniRepository {

    public List<DostupneVybaveni> findAllAvailable() {
        List<DostupneVybaveni> equipmentList = new ArrayList<>();

        String sql = """
                SELECT VybaveniID,
                       Nazev,
                       InventarniCislo,
                       Znacka,
                       Velikost,
                       NazevTypu,
                       CenaZaDen,
                       NazevStavu
                FROM Pohled_DostupneVybaveni
                ORDER BY NazevTypu, Nazev
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                DostupneVybaveni equipment = new DostupneVybaveni(
                        resultSet.getInt("VybaveniID"),
                        resultSet.getString("Nazev"),
                        resultSet.getString("InventarniCislo"),
                        resultSet.getString("Znacka"),
                        resultSet.getString("Velikost"),
                        resultSet.getString("NazevTypu"),
                        resultSet.getDouble("CenaZaDen"),
                        resultSet.getString("NazevStavu")
                );

                equipmentList.add(equipment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return equipmentList;
    }
}