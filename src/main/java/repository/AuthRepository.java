package repository;

import model.Zamestnanec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthRepository {

    public Zamestnanec login(String login, String password) {
        String sql = """
                SELECT ZamestnanecID, Login, Role, Jmeno, Prijmeni
                FROM Zamestnanec
                WHERE Login = ?
                  AND Heslo = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Zamestnanec(
                            resultSet.getInt("ZamestnanecID"),
                            resultSet.getString("Login"),
                            resultSet.getString("Role"),
                            resultSet.getString("Jmeno"),
                            resultSet.getString("Prijmeni")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}