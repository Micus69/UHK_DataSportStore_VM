package repository;

import model.ZamestnanecAdmin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZamestnanecRepository {

    public List<ZamestnanecAdmin> findAll() {
        List<ZamestnanecAdmin> employees = new ArrayList<>();

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
            e.printStackTrace();
        }

        return employees;
    }

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
            statement.setDate(1, Date.valueOf(employee.getDatumNastupu()));
            statement.setString(2, employee.getHeslo());
            statement.setString(3, employee.getJmeno());
            statement.setString(4, employee.getLogin());
            statement.setString(5, employee.getPozice());
            statement.setString(6, employee.getPrijmeni());
            statement.setString(7, employee.getRole());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Employee insert failed.");
        }
    }

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
            statement.setDate(1, Date.valueOf(employee.getDatumNastupu()));
            statement.setString(2, employee.getHeslo());
            statement.setString(3, employee.getJmeno());
            statement.setString(4, employee.getLogin());
            statement.setString(5, employee.getPozice());
            statement.setString(6, employee.getPrijmeni());
            statement.setString(7, employee.getRole());
            statement.setInt(8, employee.getZamestnanecID());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Employee update failed.");
        }
    }
}