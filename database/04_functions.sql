/*
===========================================================
FUNCTION: PocetDniVypujcky
===========================================================

Description:
Returns the total number of rental days
between two selected dates.
===========================================================
*/

DELIMITER $$

CREATE FUNCTION PocetDniVypujcky(
    p_DatumOd DATE,
    p_DatumDo DATE
)
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE v_PocetDni INT;

    SET v_PocetDni = DATEDIFF(p_DatumDo, p_DatumOd) + 1;

    IF v_PocetDni < 1 THEN
        SET v_PocetDni = 1;
    END IF;

    RETURN v_PocetDni;
END$$

/*
===========================================================
FUNCTION: VypocetCenyPolozky
===========================================================

Description:
Calculates total rental price for selected
equipment based on rental duration.
===========================================================
*/

CREATE FUNCTION VypocetCenyPolozky(
    p_VybaveniID INT,
    p_DatumOd DATE,
    p_DatumDo DATE
)
    RETURNS DECIMAL(10,2)
    DETERMINISTIC
BEGIN
    DECLARE v_CenaZaDen DECIMAL(10,2);
    DECLARE v_PocetDni INT;

    SELECT tv.CenaZaDen
    INTO v_CenaZaDen
    FROM Vybaveni vyb
             JOIN TypVybaveni tv ON vyb.TypVybaveniID = tv.TypVybaveniID
    WHERE vyb.VybaveniID = p_VybaveniID;

    SET v_PocetDni = PocetDniVypujcky(p_DatumOd, p_DatumDo);

    RETURN v_CenaZaDen * v_PocetDni;
END$$

DELIMITER ;