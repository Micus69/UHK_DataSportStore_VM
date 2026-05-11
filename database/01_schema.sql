/*

Description:
This file contains the physical database schema definition
for the sports equipment rental system.

Purpose:
The schema defines all database tables, relationships,
constraints, and integrity rules required for the application.

Main functionality:
- Defines primary keys and foreign keys
- Defines entity relationships
- Enforces data integrity using constraints
- Prevents invalid or inconsistent data
- Supports reservations and rentals of multiple equipment items
- Supports customer and employee management

Database design:
The schema is normalized and designed using relational
database principles.

Main entities:
- Employees
- Customers
- Equipment
- Equipment types
- Equipment states
- Reservations
- Rentals
- Reservation items
- Rental items

Main features:
- One reservation can contain multiple equipment items
- One rental can contain multiple equipment items
- Reservations can be converted into rentals
- Equipment availability is controlled by equipment states
- Data validation is enforced using CHECK constraints
- Relational integrity is enforced using FOREIGN KEY constraints

Integrity constraints:
- PRIMARY KEY
- FOREIGN KEY
- UNIQUE
- NOT NULL
- CHECK constraints

The schema is designed for:
- Spring Boot integration
- MySQL database engine
- Docker container deployment
- Repository and ORM mapping using JPA/Hibernate
===========================================================
*/

CREATE TABLE Zamestnanec (
                             ZamestnanecID INT AUTO_INCREMENT PRIMARY KEY,
                             DatumNastupu DATE NOT NULL,
                             Heslo VARCHAR(255) NOT NULL,
                             Jmeno VARCHAR(100) NOT NULL,
                             Login VARCHAR(100) NOT NULL UNIQUE,
                             Pozice VARCHAR(100) NOT NULL,
                             Prijmeni VARCHAR(100) NOT NULL,
                             Role VARCHAR(30) NOT NULL
);

CREATE TABLE Zakaznik (
                          ZakaznikID INT AUTO_INCREMENT PRIMARY KEY,
                          ZamestnanecID INT NOT NULL,
                          Ulice VARCHAR(100) NOT NULL,
                          CisloPopisne VARCHAR(20) NOT NULL,
                          Mesto VARCHAR(100) NOT NULL,
                          PSC VARCHAR(20) NOT NULL,
                          DatumRegistrace DATE NOT NULL,
                          Email VARCHAR(120) NOT NULL UNIQUE,
                          Jmeno VARCHAR(100) NOT NULL,
                          Prijmeni VARCHAR(100) NOT NULL,
                          Telefon VARCHAR(30) NOT NULL,

                          CONSTRAINT FK_Zakaznik_Zamestnanec
                              FOREIGN KEY (ZamestnanecID)
                                  REFERENCES Zamestnanec(ZamestnanecID)
);

CREATE TABLE TypVybaveni (
                             TypVybaveniID INT AUTO_INCREMENT PRIMARY KEY,
                             CenaZaDen DECIMAL(10,2) NOT NULL,
                             NazevTypu VARCHAR(100) NOT NULL UNIQUE,
                             Popis VARCHAR(255),

                             CONSTRAINT CHK_TypVybaveni_Cena
                                 CHECK (CenaZaDen >= 0)
);

CREATE TABLE StavVybaveni (
                              StavVybaveniID INT AUTO_INCREMENT PRIMARY KEY,
                              JeDostupneProPujceni BOOLEAN NOT NULL,
                              NazevStavu VARCHAR(100) NOT NULL UNIQUE,
                              PopisStavu VARCHAR(255)
);

CREATE TABLE Vybaveni (
                          VybaveniID INT AUTO_INCREMENT PRIMARY KEY,
                          TypVybaveniID INT NOT NULL,
                          StavVybaveniID INT NOT NULL,
                          DatumPorizeni DATE NOT NULL,
                          InventarniCislo VARCHAR(100) NOT NULL UNIQUE,
                          Nazev VARCHAR(120) NOT NULL,
                          Poznamky VARCHAR(255),
                          Velikost VARCHAR(50),
                          Znacka VARCHAR(100) NOT NULL,

                          CONSTRAINT FK_Vybaveni_TypVybaveni
                              FOREIGN KEY (TypVybaveniID)
                                  REFERENCES TypVybaveni(TypVybaveniID),

                          CONSTRAINT FK_Vybaveni_StavVybaveni
                              FOREIGN KEY (StavVybaveniID)
                                  REFERENCES StavVybaveni(StavVybaveniID)
);

CREATE TABLE Rezervace (
                           RezervaceID INT AUTO_INCREMENT PRIMARY KEY,
                           ZakaznikID INT NOT NULL,
                           ZamestnanecID INT NOT NULL,
                           DatumOd DATE NOT NULL,
                           DatumDo DATE NOT NULL,
                           DatumVytvoreni DATE NOT NULL,
                           StavRezervace VARCHAR(50) NOT NULL,

                           CONSTRAINT FK_Rezervace_Zakaznik
                               FOREIGN KEY (ZakaznikID)
                                   REFERENCES Zakaznik(ZakaznikID),

                           CONSTRAINT FK_Rezervace_Zamestnanec
                               FOREIGN KEY (ZamestnanecID)
                                   REFERENCES Zamestnanec(ZamestnanecID),

                           CONSTRAINT CHK_Rezervace_Datum
                               CHECK (DatumDo >= DatumOd)
);

CREATE TABLE RezervacePolozka (
                                  RezervacePolozkaID INT AUTO_INCREMENT PRIMARY KEY,
                                  RezervaceID INT NOT NULL,
                                  VybaveniID INT NOT NULL,

                                  CONSTRAINT FK_RezervacePolozka_Rezervace
                                      FOREIGN KEY (RezervaceID)
                                          REFERENCES Rezervace(RezervaceID),

                                  CONSTRAINT FK_RezervacePolozka_Vybaveni
                                      FOREIGN KEY (VybaveniID)
                                          REFERENCES Vybaveni(VybaveniID),

                                  CONSTRAINT UQ_RezervacePolozka
                                      UNIQUE (RezervaceID, VybaveniID)
);

CREATE TABLE Vypujcka (
                          VypujckaID INT AUTO_INCREMENT PRIMARY KEY,
                          RezervaceID INT NULL,
                          ZakaznikID INT NOT NULL,
                          ZamestnanecID INT NOT NULL,
                          CenaCelkem DECIMAL(10,2) NOT NULL DEFAULT 0,
                          DatumVypujceni DATE NOT NULL,
                          PlanovaneVraceni DATE NOT NULL,
                          SkutecneVraceni DATE NULL,
                          StavVypujcky VARCHAR(50) NOT NULL,

                          CONSTRAINT FK_Vypujcka_Rezervace
                              FOREIGN KEY (RezervaceID)
                                  REFERENCES Rezervace(RezervaceID),

                          CONSTRAINT FK_Vypujcka_Zakaznik
                              FOREIGN KEY (ZakaznikID)
                                  REFERENCES Zakaznik(ZakaznikID),

                          CONSTRAINT FK_Vypujcka_Zamestnanec
                              FOREIGN KEY (ZamestnanecID)
                                  REFERENCES Zamestnanec(ZamestnanecID),

                          CONSTRAINT CHK_Vypujcka_Datum
                              CHECK (PlanovaneVraceni >= DatumVypujceni),

                          CONSTRAINT CHK_Vypujcka_Cena
                              CHECK (CenaCelkem >= 0)
);

CREATE TABLE VypujckaPolozka (
                                 VypujckaPolozkaID INT AUTO_INCREMENT PRIMARY KEY,
                                 VypujckaID INT NOT NULL,
                                 VybaveniID INT NOT NULL,
                                 CenaZaDen DECIMAL(10,2) NOT NULL,
                                 PocetDni INT NOT NULL,
                                 CenaPolozky DECIMAL(10,2) NOT NULL,

                                 CONSTRAINT FK_VypujckaPolozka_Vypujcka
                                     FOREIGN KEY (VypujckaID)
                                         REFERENCES Vypujcka(VypujckaID),

                                 CONSTRAINT FK_VypujckaPolozka_Vybaveni
                                     FOREIGN KEY (VybaveniID)
                                         REFERENCES Vybaveni(VybaveniID),

                                 CONSTRAINT UQ_VypujckaPolozka
                                     UNIQUE (VypujckaID, VybaveniID),

                                 CONSTRAINT CHK_VypujckaPolozka_Cena
                                     CHECK (CenaZaDen >= 0 AND PocetDni > 0 AND CenaPolozky >= 0)
);