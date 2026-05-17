/*
 * Repository responsible for equipment state management.
 *
 * Main functionality:
 * - Loads all equipment states
 * - Updates equipment state records
 * - Transfers equipment state data between database and GUI
 */

package repository;

import model.StavVybaveni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StavVybaveniRepository {

    /*
     * Loads all equipment states from database.
     */
    public List<StavVybaveni> findAll() {

        // Collection storing equipment states.
        List<StavVybaveni> states =
                new ArrayList<>();

        String sql = """
                SELECT StavVybaveniID, JeDostupneProPujceni, NazevStavu, PopisStavu
                FROM StavVybaveni
                ORDER BY StavVybaveniID
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            // Converts database rows into model objects.
            while (resultSet.next()) {

                StavVybaveni state =
                        new StavVybaveni(
                                resultSet.getInt("StavVybaveniID"),
                                resultSet.getBoolean("JeDostupneProPujceni"),
                                resultSet.getString("NazevStavu"),
                                resultSet.getString("PopisStavu")
                        );

                states.add(state);
            }

        } catch (Exception e) {

            // Prints database loading error.
            e.printStackTrace();
        }

        return states;
    }

    /*
     * Updates selected equipment state record.
     */
    public void update(StavVybaveni state) {

        String sql = """
                UPDATE StavVybaveni
                SET NazevStavu = ?,
                    JeDostupneProPujceni = ?,
                    PopisStavu = ?
                WHERE StavVybaveniID = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            // Maps model values into SQL query.
            statement.setString(1, state.getNazevStavu());
            statement.setBoolean(2, state.isJeDostupneProPujceni());
            statement.setString(3, state.getPopisStavu());
            statement.setInt(4, state.getStavVybaveniID());

            // Executes database update.
            statement.executeUpdate();

        } catch (Exception e) {

            // Prints database update error.
            e.printStackTrace();
        }
    }
}