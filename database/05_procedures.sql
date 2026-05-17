/*
===========================================================
PROCEDURE: VytvorVypujckuZRezervace
===========================================================

Description:
Creates rental from selected reservation,
calculates prices and updates equipment states.
===========================================================
*/

DROP PROCEDURE IF EXISTS VytvorVypujckuZRezervace;
DROP PROCEDURE IF EXISTS VratVypujcku;

DROP TRIGGER IF EXISTS TRG_VypujckaPolozka_AfterInsert;

DELIMITER $$

CREATE PROCEDURE VytvorVypujckuZRezervace(
    IN p_RezervaceID INT,
    IN p_ZamestnanecID INT,
    IN p_DatumVypujceni DATE,
    IN p_PlanovaneVraceni DATE
)
BEGIN
    DECLARE v_ZakaznikID INT;
    DECLARE v_VypujckaID INT;
    DECLARE v_CenaCelkem DECIMAL(10,2);

    START TRANSACTION;

    SELECT ZakaznikID
    INTO v_ZakaznikID
    FROM Rezervace
    WHERE RezervaceID = p_RezervaceID
        FOR UPDATE;

    INSERT INTO Vypujcka
    (
        RezervaceID,
        ZakaznikID,
        ZamestnanecID,
        CenaCelkem,
        DatumVypujceni,
        PlanovaneVraceni,
        SkutecneVraceni,
        StavVypujcky
    )
    VALUES
        (
            p_RezervaceID,
            v_ZakaznikID,
            p_ZamestnanecID,
            0,
            p_DatumVypujceni,
            p_PlanovaneVraceni,
            NULL,
            'Aktivni'
        );

    SET v_VypujckaID = LAST_INSERT_ID();

    INSERT INTO VypujckaPolozka
    (
        VypujckaID,
        VybaveniID,
        CenaZaDen,
        PocetDni,
        CenaPolozky
    )
    SELECT
        v_VypujckaID,
        rp.VybaveniID,
        tv.CenaZaDen,
        PocetDniVypujcky(p_DatumVypujceni, p_PlanovaneVraceni),
        VypocetCenyPolozky(rp.VybaveniID, p_DatumVypujceni, p_PlanovaneVraceni)
    FROM RezervacePolozka rp
             JOIN Vybaveni vyb ON rp.VybaveniID = vyb.VybaveniID
             JOIN TypVybaveni tv ON vyb.TypVybaveniID = tv.TypVybaveniID
    WHERE rp.RezervaceID = p_RezervaceID;

    UPDATE Vybaveni
    SET StavVybaveniID = (
        SELECT StavVybaveniID
        FROM StavVybaveni
        WHERE NazevStavu = 'Zapujcene'
        LIMIT 1
    )
    WHERE VybaveniID IN (
        SELECT VybaveniID
        FROM RezervacePolozka
        WHERE RezervaceID = p_RezervaceID
    );

    SELECT SUM(CenaPolozky)
    INTO v_CenaCelkem
    FROM VypujckaPolozka
    WHERE VypujckaID = v_VypujckaID;

    UPDATE Vypujcka
    SET CenaCelkem = v_CenaCelkem
    WHERE VypujckaID = v_VypujckaID;

    UPDATE Rezervace
    SET StavRezervace = 'PrevedenaNaVypujcku'
    WHERE RezervaceID = p_RezervaceID;

    COMMIT;
END$$

/*
===========================================================
PROCEDURE: VratVypujcku
===========================================================

Description:
Marks rental as returned and completes
the rental process.
===========================================================
*/

CREATE PROCEDURE VratVypujcku(
    IN p_VypujckaID INT,
    IN p_DatumVraceni DATE
)
BEGIN
    START TRANSACTION;

    UPDATE Vypujcka
    SET
        SkutecneVraceni = p_DatumVraceni,
        StavVypujcky = 'Ukoncena'
    WHERE VypujckaID = p_VypujckaID;

    COMMIT;
END$$

DELIMITER ;