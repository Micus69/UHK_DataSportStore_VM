package repository;

import model.Statistika;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatistikaRepository {

    public Statistika loadStatistics() {
        int activeReservations = count("""
                SELECT COUNT(*)
                FROM Rezervace
                WHERE StavRezervace = 'Aktivni'
                """);

        int activeRentals = count("""
                SELECT COUNT(*)
                FROM Vypujcka
                WHERE StavVypujcky = 'Aktivni'
                """);

        int availableEquipment = count("""
                SELECT COUNT(*)
                FROM Pohled_DostupneVybaveni
                """);

        int rentedEquipment = count("""
                SELECT COUNT(*)
                FROM Vybaveni v
                JOIN StavVybaveni s ON v.StavVybaveniID = s.StavVybaveniID
                WHERE s.NazevStavu = 'Zapujcene'
                """);

        double totalRevenue = sum("""
                SELECT COALESCE(SUM(CenaCelkem), 0)
                FROM Vypujcka
                """);

        return new Statistika(
                activeReservations,
                activeRentals,
                availableEquipment,
                rentedEquipment,
                totalRevenue
        );
    }

    private int count(String sql) {
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private double sum(String sql) {
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}