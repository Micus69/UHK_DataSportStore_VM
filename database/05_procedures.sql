/*
===========================================================
FILE: 05_procedures.sql
===========================================================

Description:
This file contains stored procedures used for managing
rentals and reservation processing within the sports
equipment rental system.

Purpose:
The procedures encapsulate business logic directly
inside the database layer and ensure transactional
consistency during critical operations.

Main functionality:
- Converts reservations into active rentals
- Automatically creates rental items from reservation items
- Calculates total rental prices
- Updates reservation states
- Handles equipment return operations
- Ensures transactional safety using COMMIT operations

Procedures overview:
1. VytvorVypujckuZRezervace
   - Converts an existing reservation into a rental
   - Creates rental records and rental items
   - Calculates total rental cost
   - Updates reservation status
   - Uses database transactions for consistency

2. VratVypujcku
   - Marks rental as returned
   - Updates return date
   - Updates rental status

Technical details:
- Uses START TRANSACTION and COMMIT
- Prevents inconsistent data during rental creation
- Reuses database functions for pricing calculations
- Uses SELECT INTO for internal variable handling
- Uses LAST_INSERT_ID for newly created rental records

Advantages:
- Centralized business logic
- Reduced application-side complexity
- Improved database consistency
- Better transactional safety
- Reusable database operations

Used tables:
- Rezervace
- RezervacePolozka
- Vypujcka
- VypujckaPolozka
- Vybaveni
- TypVybaveni

Used functions:
- PocetDniVypujcky
- VypocetCenyPolozky

Designed for:
- MySQL database engine
- Spring Boot integration
- Transaction-safe rental processing
===========================================================
*/

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