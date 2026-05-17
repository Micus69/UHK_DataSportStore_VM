/*
 * Repository responsible for employee management.
 *
 * Main functionality:
 * - Loads employee records
 * - Creates new employees
 * - Updates existing employees
 * - Transfers employee data between database and GUI
 */

package repository;

import model.ZamestnanecAdmin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZamestnanecRepository {

    /*
     * Loads all employees from database.
     */
    public List<ZamestnanecAdmin> findAll() {

        // Collection storing employee records.
        List<ZamestnanecAdmin> employees =
                new ArrayList<>();

        String sql = """
                SELECT ZamestnanecID, DatumNastupu, Heslo, Jmeno,
                       Login, Pozice, Prijmeni, Role
                FROM Zamestnanec
                ORDER BY ZamestnanecID
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            // Converts database rows into model objects.
            while (resultSet.next()) {

                employees.add(new ZamestnanecAdmin(
                        resultSet.getInt("ZamestnanecID"),
                        resultSet.getDate("DatumNastupu").toLocalDate(),
                        resultSet.getString("Heslo"),
                        resultSet.getString("Jmeno"),
                        resultSet.getString("Login"),
                        resultSet.getString("Pozice"),
                        resultSet.getString("Prijmeni"),
                        resultSet.getString("Role")
                ));
            }

        } catch (Exception e) {

            // Prints database loading error.
            e.printStackTrace();
        }

        return employees;
    }

    /*
     * Inserts new employee into database.
     */
    public void insert(ZamestnanecAdmin employee) {

        String sql = """
                INSERT INTO Zamestnanec
                (DatumNastupu, Heslo, Jmeno, Login, Pozice, Prijmeni, Role)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            // Maps employee object values into SQL query.
            statement.setDate(1, Date.valueOf(employee.getDatumNastupu()));
            statement.setString(2, employee.getHeslo());
            statement.setString(3, employee.getJmeno());
            statement.setString(4, employee.getLogin());
            statement.setString(5, employee.getPozice());
            statement.setString(6, employee.getPrijmeni());
            statement.setString(7, employee.getRole());

            // Executes INSERT query.
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Employee insert failed."
            );
        }
    }

    /*
     * Updates existing employee record.
     */
    public void update(ZamestnanecAdmin employee) {

        String sql = """
                UPDATE Zamestnanec
                SET DatumNastupu = ?,
                    Heslo = ?,
                    Jmeno = ?,
                    Login = ?,
                    Pozice = ?,
                    Prijmeni = ?,
                    Role = ?
                WHERE ZamestnanecID = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            // Maps updated employee values into SQL query.
            statement.setDate(1, Date.valueOf(employee.getDatumNastupu()));
            statement.setString(2, employee.getHeslo());
            statement.setString(3, employee.getJmeno());
            statement.setString(4, employee.getLogin());
            statement.setString(5, employee.getPozice());
            statement.setString(6, employee.getPrijmeni());
            statement.setString(7, employee.getRole());
            statement.setInt(8, employee.getZamestnanecID());

            // Executes UPDATE query.
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Employee update failed."
            );
        }
    }
}