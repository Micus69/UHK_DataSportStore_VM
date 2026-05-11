/*
===========================================================
VIEW: Pohled_AktivniVypujcky
===========================================================

Description:
This view provides an overview of all currently active rentals
that have not yet been returned.

Purpose:
The view combines information from rentals, customers,
employees, equipment, and equipment types into a single
readable dataset.

Main functionality:
- Displays active rental records
- Shows customer information
- Shows rented equipment details
- Displays rental pricing information
- Displays employee responsible for the rental
- Filters only rentals where the equipment has not yet been returned

Used tables:
- Vypujcka
- VypujckaPolozka
- Vybaveni
- TypVybaveni
- Zakaznik
- Zamestnanec
===========================================================
*/

CREATE VIEW Pohled_AktivniVypujcky AS
SELECT
    v.VypujckaID,
    z.Jmeno AS ZakaznikJmeno,
    z.Prijmeni AS ZakaznikPrijmeni,
    z.Email,
    vyb.Nazev AS NazevVybaveni,
    vyb.InventarniCislo,
    tv.NazevTypu,
    vp.CenaZaDen,
    vp.PocetDni,
    vp.CenaPolozky,
    v.CenaCelkem,
    v.DatumVypujceni,
    v.PlanovaneVraceni,
    v.StavVypujcky,
    zam.Jmeno AS ZamestnanecJmeno,
    zam.Prijmeni AS ZamestnanecPrijmeni
FROM Vypujcka v
         JOIN VypujckaPolozka vp ON v.VypujckaID = vp.VypujckaID
         JOIN Vybaveni vyb ON vp.VybaveniID = vyb.VybaveniID
         JOIN TypVybaveni tv ON vyb.TypVybaveniID = tv.TypVybaveniID
         JOIN Zakaznik z ON v.ZakaznikID = z.ZakaznikID
         JOIN Zamestnanec zam ON v.ZamestnanecID = zam.ZamestnanecID
WHERE v.SkutecneVraceni IS NULL;


/*
===========================================================
VIEW: Pohled_DostupneVybaveni
===========================================================

Description:
This view displays all equipment currently available for rental.

Purpose:
The view simplifies access to available equipment and combines
equipment information, equipment type, pricing, and equipment status.

Main functionality:
- Displays only available equipment
- Shows equipment type and pricing
- Displays inventory information
- Used for employee and customer equipment overview

Used tables:
- Vybaveni
- TypVybaveni
- StavVybaveni
===========================================================
*/

CREATE VIEW Pohled_DostupneVybaveni AS
SELECT
    vyb.VybaveniID,
    vyb.Nazev,
    vyb.InventarniCislo,
    vyb.Znacka,
    vyb.Velikost,
    tv.NazevTypu,
    tv.CenaZaDen,
    sv.NazevStavu
FROM Vybaveni vyb
         JOIN TypVybaveni tv ON vyb.TypVybaveniID = tv.TypVybaveniID
         JOIN StavVybaveni sv ON vyb.StavVybaveniID = sv.StavVybaveniID
WHERE sv.JeDostupneProPujceni = TRUE;


/*
===========================================================
VIEW: Pohled_RezervaceDetail
===========================================================

Description:
This view provides detailed information about reservations.

Purpose:
The view combines reservation records with customers
and reserved equipment into a single overview.

Main functionality:
- Displays reservation details
- Shows customer information
- Displays reserved equipment
- Displays reservation dates and status

Used tables:
- Rezervace
- RezervacePolozka
- Vybaveni
- Zakaznik
===========================================================
*/

CREATE VIEW Pohled_RezervaceDetail AS
SELECT
    r.RezervaceID,
    z.Jmeno AS ZakaznikJmeno,
    z.Prijmeni AS ZakaznikPrijmeni,
    vyb.Nazev AS NazevVybaveni,
    vyb.InventarniCislo,
    r.DatumOd,
    r.DatumDo,
    r.StavRezervace
FROM Rezervace r
         JOIN RezervacePolozka rp ON r.RezervaceID = rp.RezervaceID
         JOIN Vybaveni vyb ON rp.VybaveniID = vyb.VybaveniID
         JOIN Zakaznik z ON r.ZakaznikID = z.ZakaznikID;