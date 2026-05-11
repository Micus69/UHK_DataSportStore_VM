/*
===========================================================
FILE: 04_functions.sql
===========================================================

Description:
This file contains custom MySQL database functions used
for rental calculations within the sports equipment rental system.

Purpose:
The functions provide reusable business logic directly
inside the database layer.

Main functionality:
- Calculates the number of rental days
- Calculates the total price for rented equipment
- Supports reusable pricing logic
- Simplifies calculations for procedures and views
- Ensures consistent pricing calculations across the application

Functions overview:
1. PocetDniVypujcky
   - Calculates the number of rental days
   - Ensures the minimum rental duration is one day

2. VypocetCenyPolozky
   - Calculates the total rental price for a single equipment item
   - Uses equipment daily price and rental duration
   - Reuses the PocetDniVypujcky function

Technical details:
- Functions are deterministic
- Functions return calculated values
- Functions can be reused in:
    - procedures
    - views
    - triggers
    - application queries

Advantages:
- Centralized calculation logic
- Better maintainability
- Reduced duplicated logic in application code
- Improved database consistency

Designed for:
- MySQL database engine
- Spring Boot integration
- Repository and procedure usage
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