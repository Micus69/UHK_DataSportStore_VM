/*
 * Repository responsible for user authentication.
 *
 * Main functionality:
 * - Verifies employee login credentials
 * - Loads employee role information
 * - Returns authenticated employee object
 */

package repository;

import model.Zamestnanec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthRepository {

    /*
     * Verifies login credentials against database.
     *
     * Returns:
     * - Zamestnanec object if authentication succeeds
     * - null if login fails
     */
    public Zamestnanec login(String login, String password) {

        // SQL query used for employee authentication.
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

            // Sets login credentials into SQL query.
            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                /*
                 * If matching employee exists,
                 * create authenticated employee object.
                 */
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

            // Prints database authentication error.
            e.printStackTrace();
        }

        // Authentication failed.
        return null;
    }
}