package repository;

import model.StavVybaveni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StavVybaveniRepository {

    public List<StavVybaveni> findAll() {
        List<StavVybaveni> states = new ArrayList<>();

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
            while (resultSet.next()) {
                StavVybaveni state = new StavVybaveni(
                        resultSet.getInt("StavVybaveniID"),
                        resultSet.getBoolean("JeDostupneProPujceni"),
                        resultSet.getString("NazevStavu"),
                        resultSet.getString("PopisStavu")
                );

                states.add(state);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return states;
    }

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
            statement.setString(1, state.getNazevStavu());
            statement.setBoolean(2, state.isJeDostupneProPujceni());
            statement.setString(3, state.getPopisStavu());
            statement.setInt(4, state.getStavVybaveniID());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}