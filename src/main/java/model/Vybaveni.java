package model;

import java.time.LocalDate;

public class Vybaveni {

    private int vybaveniID;
    private int typVybaveniID;
    private int stavVybaveniID;
    private LocalDate datumPorizeni;
    private String inventarniCislo;
    private String nazev;
    private String poznamky;
    private String velikost;
    private String znacka;

    public Vybaveni(int vybaveniID, int typVybaveniID, int stavVybaveniID,
                    LocalDate datumPorizeni, String inventarniCislo, String nazev,
                    String poznamky, String velikost, String znacka) {
        this.vybaveniID = vybaveniID;
        this.typVybaveniID = typVybaveniID;
        this.stavVybaveniID = stavVybaveniID;
        this.datumPorizeni = datumPorizeni;
        this.inventarniCislo = inventarniCislo;
        this.nazev = nazev;
        this.poznamky = poznamky;
        this.velikost = velikost;
        this.znacka = znacka;
    }

    public int getVybaveniID() { return vybaveniID; }
    public int getTypVybaveniID() { return typVybaveniID; }
    public int getStavVybaveniID() { return stavVybaveniID; }
    public LocalDate getDatumPorizeni() { return datumPorizeni; }
    public String getInventarniCislo() { return inventarniCislo; }
    public String getNazev() { return nazev; }
    public String getPoznamky() { return poznamky; }
    public String getVelikost() { return velikost; }
    public String getZnacka() { return znacka; }
}