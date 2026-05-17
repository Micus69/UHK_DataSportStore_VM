/*

Main functionality:
- Inserts employees and application roles
- Inserts equipment types and pricing
- Inserts equipment availability states
- Inserts equipment records
- Inserts customer records
- Inserts reservation records
- Inserts rental records and rental items

Used tables:
- Zamestnanec
- TypVybaveni
- StavVybaveni
- Vybaveni
- Zakaznik
- Rezervace
- RezervacePolozka
- Vypujcka
- VypujckaPolozka
===========================================================
*/

INSERT INTO Zamestnanec
(DatumNastupu, Heslo, Jmeno, Login, Pozice, Prijmeni, Role)
VALUES
    ('2024-01-10', 'admin123', 'Vladimir', 'admin', 'Administrator', 'Mician', 'ADMIN'),
    ('2024-03-01', 'employee123', 'Jan', 'zamestnanec', 'Employee', 'Novak', 'EMPLOYEE');

INSERT INTO TypVybaveni
(CenaZaDen, NazevTypu, Popis)
VALUES
    (350.00, 'Kolo', 'Horske a trekingove kolo'),
    (450.00, 'Lyze', 'Sjezdove lyze'),
    (400.00, 'Snowboard', 'Snowboardove vybaveni'),
    (180.00, 'Brusle', 'Ledni a inline brusle');

INSERT INTO StavVybaveni
(JeDostupneProPujceni, NazevStavu, PopisStavu)
VALUES
    (TRUE, 'Dostupne', 'Vybaveni je dostupne pro vypujceni'),
    (FALSE, 'Zapujcene', 'Vybaveni je aktualne zapujcene'),
    (FALSE, 'Rezervovane', 'Vybaveni je rezervovane zakaznikem'),
    (FALSE, 'Servis', 'Vybaveni je v servisu');

INSERT INTO Vybaveni
(TypVybaveniID, StavVybaveniID, DatumPorizeni, InventarniCislo, Nazev, Poznamky, Velikost, Znacka)
VALUES
    (1, 1, '2023-05-10', 'KOLO-001', 'Horske kolo RockRider', 'Bez poznamek', 'M', 'RockRider'),
    (1, 1, '2023-06-12', 'KOLO-002', 'Trekingove kolo Author', 'Nove plaste', 'L', 'Author'),
    (2, 1, '2022-11-01', 'LYZE-001', 'Sjezdove lyze Atomic', 'Servisovano', '170', 'Atomic'),
    (3, 1, '2022-12-05', 'SNB-001', 'Snowboard Burton', 'Bez poznamek', '155', 'Burton'),
    (4, 1, '2024-01-15', 'BRUSLE-001', 'Inline brusle Tempish', 'Bez poznamek', '42', 'Tempish');

INSERT INTO Zakaznik
(ZamestnanecID, Ulice, CisloPopisne, Mesto, PSC, DatumRegistrace, Email, Jmeno, Prijmeni, Telefon)
VALUES
    (2, 'Hlavni', '100', 'Hradec Kralove', '50003', '2024-04-01', 'petr.svoboda@email.cz', 'Petr', 'Svoboda', '+420777111222'),
    (2, 'Nadrazni', '50', 'Pardubice', '53002', '2024-04-05', 'lucie.novakova@email.cz', 'Lucie', 'Novakova', '+420777333444');

INSERT INTO Rezervace
(ZakaznikID, ZamestnanecID, DatumOd, DatumDo, DatumVytvoreni, StavRezervace)
VALUES
    (1, 2, '2024-06-01', '2024-06-03', '2024-05-20', 'Aktivni');

INSERT INTO RezervacePolozka
(RezervaceID, VybaveniID)
VALUES
    (1, 3),
    (1, 4);

INSERT INTO Vypujcka
(RezervaceID, ZakaznikID, ZamestnanecID, CenaCelkem, DatumVypujceni, PlanovaneVraceni, SkutecneVraceni, StavVypujcky)
VALUES
    (NULL, 2, 2, 1060.00, '2024-05-01', '2024-05-03', NULL, 'Aktivni');

INSERT INTO VypujckaPolozka
(VypujckaID, VybaveniID, CenaZaDen, PocetDni, CenaPolozky)
VALUES
    (1, 1, 350.00, 3, 1050.00);