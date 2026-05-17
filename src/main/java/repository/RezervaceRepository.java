/*
 * Repository responsible for reservation creation.
 *
 * Main functionality:
 * - Creates customer record
 * - Creates reservation record
 * - Stores selected equipment into reservation items
 * - Uses transaction to keep reservation data consistent
 */

package repository;

import model.Rezervace;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RezervaceRepository {

    /*
     * Creates a complete reservation workflow.
     *
     * The method inserts customer, reservation and reservation items
     * in one transaction. If any step fails, the whole operation fails.
     */
    public void createReservation(Rezervace rezervace) {

        String insertCustomerSql = """
                INSERT INTO Zakaznik
                (ZamestnanecID, Ulice, CisloPopisne, Mesto, PSC,
                 DatumRegistrace, Email, Jmeno, Prijmeni, Telefon)
                VALUES (?, ?, ?, ?, ?, CURRENT_DATE, ?, ?, ?, ?)
                """;

        String insertReservationSql = """
                INSERT INTO Rezervace
                (ZakaznikID, ZamestnanecID, DatumOd, DatumDo, DatumVytvoreni, StavRezervace)
                VALUES (?, ?, ?, ?, CURRENT_DATE, 'Aktivni')
                """;

        String insertReservationItemSql = """
                INSERT INTO RezervacePolozka
                (RezervaceID, VybaveniID)
                VALUES (?, ?)
                """;

        try (Connection connection = DBConnection.getConnection()) {

            // Transaction ensures that customer, reservation and items are saved together.
            connection.setAutoCommit(false);

            int zakaznikID;
            int rezervaceID;

            /*
             * Inserts customer data and reads generated customer ID.
             */
            try (
                    PreparedStatement customerStatement =
                            connection.prepareStatement(insertCustomerSql, Statement.RETURN_GENERATED_KEYS)
            ) {
                customerStatement.setInt(1, rezervace.getZamestnanecID());
                customerStatement.setString(2, rezervace.getUlice());
                customerStatement.setString(3, rezervace.getCisloPopisne());
                customerStatement.setString(4, rezervace.getMesto());
                customerStatement.setString(5, rezervace.getPsc());
                customerStatement.setString(6, rezervace.getEmail());
                customerStatement.setString(7, rezervace.getJmeno());
                customerStatement.setString(8, rezervace.getPrijmeni());
                customerStatement.setString(9, rezervace.getTelefon());

                customerStatement.executeUpdate();

                try (var generatedKeys = customerStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        zakaznikID = generatedKeys.getInt(1);
                    } else {
                        throw new RuntimeException("Customer ID was not generated.");
                    }
                }
            }

            /*
             * Inserts reservation header and reads generated reservation ID.
             */
            try (
                    PreparedStatement reservationStatement =
                            connection.prepareStatement(insertReservationSql, Statement.RETURN_GENERATED_KEYS)
            ) {
                reservationStatement.setInt(1, zakaznikID);
                reservationStatement.setInt(2, rezervace.getZamestnanecID());
                reservationStatement.setDate(3, Date.valueOf(rezervace.getDatumOd()));
                reservationStatement.setDate(4, Date.valueOf(rezervace.getDatumDo()));

                reservationStatement.executeUpdate();

                try (var generatedKeys = reservationStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rezervaceID = generatedKeys.getInt(1);
                    } else {
                        throw new RuntimeException("Reservation ID was not generated.");
                    }
                }
            }

            /*
             * Inserts all selected equipment items into reservation.
             * Batch insert is used because one reservation can contain multiple items.
             */
            try (
                    PreparedStatement itemStatement =
                            connection.prepareStatement(insertReservationItemSql)
            ) {
                for (Integer vybaveniID : rezervace.getVybaveniIds()) {
                    itemStatement.setInt(1, rezervaceID);
                    itemStatement.setInt(2, vybaveniID);
                    itemStatement.addBatch();
                }

                itemStatement.executeBatch();
            }

            // Saves whole transaction.
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Reservation creation failed.");
        }
    }
}