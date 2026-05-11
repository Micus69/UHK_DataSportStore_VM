/*
===========================================================
FILE: 06_triggers.sql
===========================================================

Description:
This file contains database triggers used for automatic
equipment state management within the sports equipment
rental system.

Purpose:
The triggers automate database operations related to
equipment availability and rental status changes.

Main functionality:
- Automatically updates equipment status after reservation
- Automatically updates equipment status after rental creation
- Automatically restores equipment availability after return
- Ensures database consistency without requiring application logic

Triggers overview:
1. TRG_RezervacePolozka_AfterInsert
   - Triggered after a reservation item is created
   - Changes equipment status to "Reserved"

2. TRG_VypujckaPolozka_AfterInsert
   - Triggered after a rental item is created
   - Changes equipment status to "Rented"

3. TRG_Vypujcka_AfterUpdate
   - Triggered after a rental is updated
   - Detects returned rentals
   - Changes equipment status back to "Available"

Technical details:
- Uses AFTER INSERT and AFTER UPDATE events
- Uses NEW and OLD row references
- Automatically synchronizes equipment availability
- Reduces application-side update logic
- Ensures consistent equipment states

Advantages:
- Automatic data synchronization
- Reduced duplicated logic in application code
- Improved data integrity
- Centralized business rules
- Real-time equipment state updates

Used tables:
- RezervacePolozka
- VypujckaPolozka
- Vypujcka
- Vybaveni
- StavVybaveni

Equipment states used:
- Rezervovane (Reserved)
- Zapujcene (Rented)
- Dostupne (Available)

Designed for:
- MySQL database engine
- Spring Boot integration
- Automatic equipment state management
===========================================================
*/

DELIMITER $$

CREATE TRIGGER TRG_RezervacePolozka_AfterInsert
    AFTER INSERT ON RezervacePolozka
    FOR EACH ROW
BEGIN
    UPDATE Vybaveni
    SET StavVybaveniID = (
        SELECT StavVybaveniID
        FROM StavVybaveni
        WHERE NazevStavu = 'Rezervovane'
        LIMIT 1
    )
    WHERE VybaveniID = NEW.VybaveniID;
END$$

CREATE TRIGGER TRG_VypujckaPolozka_AfterInsert
    AFTER INSERT ON VypujckaPolozka
    FOR EACH ROW
BEGIN
    UPDATE Vybaveni
    SET StavVybaveniID = (
        SELECT StavVybaveniID
        FROM StavVybaveni
        WHERE NazevStavu = 'Zapujcene'
        LIMIT 1
    )
    WHERE VybaveniID = NEW.VybaveniID;
END$$

CREATE TRIGGER TRG_Vypujcka_AfterUpdate
    AFTER UPDATE ON Vypujcka
    FOR EACH ROW
BEGIN
    IF NEW.SkutecneVraceni IS NOT NULL AND OLD.SkutecneVraceni IS NULL THEN
        UPDATE Vybaveni
        SET StavVybaveniID = (
            SELECT StavVybaveniID
            FROM StavVybaveni
            WHERE NazevStavu = 'Dostupne'
            LIMIT 1
        )
        WHERE VybaveniID IN (
            SELECT VybaveniID
            FROM VypujckaPolozka
            WHERE VypujckaID = NEW.VypujckaID
        );
    END IF;
END$$

DELIMITER ;