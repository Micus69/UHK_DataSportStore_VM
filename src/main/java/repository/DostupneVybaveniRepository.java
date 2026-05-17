/*
 * Repository responsible for available equipment operations.
 *
 * Main functionality:
 * - Loads available equipment from database view
 * - Transfers equipment data into GUI layer
 * - Provides customer equipment overview
 */

package repository;

import model.DostupneVybaveni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DostupneVybaveniRepository {

    /*
     * Loads all currently available equipment.
     *
     * Uses:
     * Pohled_DostupneVybaveni
     */

    public List<DostupneVybaveni> findAllAvailable() {

        // Collection storing available equipment.
        List<DostupneVybaveni> equipmentList = new ArrayList<>();


        /*
         * SQL query loading available equipment overview.
         * Database view already contains joined equipment data.
         */
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

            // Converts database rows into model objects.
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

            // Prints database loading error.
            e.printStackTrace();
        }

        return equipmentList;
    }
}