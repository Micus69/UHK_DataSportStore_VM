/*
===========================================================
VIEW: Pohled_AktivniVypujcky
===========================================================

Description:
View showing all currently active rentals
including customers, equipment and rental details.
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
View showing all equipment currently available
for rental including type, price and equipment state.
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
View showing reservation details together with
customer and reserved equipment information.
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