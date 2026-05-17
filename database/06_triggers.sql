
DELIMITER $$

/*
===========================================================
TRIGGER: TRG_RezervacePolozka_AfterInsert
===========================================================

Description:
Automatically changes equipment state
to "Rezervovane" after reservation creation.
===========================================================
*/

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

/*
===========================================================
TRIGGER: TRG_VypujckaPolozka_AfterInsert
===========================================================

Description:
Automatically changes equipment state
to "Zapujcene" after rental creation.
===========================================================
*/

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

/*
===========================================================
TRIGGER: TRG_Vypujcka_AfterUpdate
===========================================================

Description:
Automatically restores equipment state
to "Dostupne" after rental return.
===========================================================
*/

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