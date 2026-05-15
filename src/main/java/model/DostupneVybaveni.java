package model;

public class DostupneVybaveni {

    private int vybaveniID;
    private String nazev;
    private String inventarniCislo;
    private String znacka;
    private String velikost;
    private String nazevTypu;
    private double cenaZaDen;
    private String nazevStavu;

    public DostupneVybaveni(int vybaveniID, String nazev, String inventarniCislo,
                            String znacka, String velikost, String nazevTypu,
                            double cenaZaDen, String nazevStavu) {
        this.vybaveniID = vybaveniID;
        this.nazev = nazev;
        this.inventarniCislo = inventarniCislo;
        this.znacka = znacka;
        this.velikost = velikost;
        this.nazevTypu = nazevTypu;
        this.cenaZaDen = cenaZaDen;
        this.nazevStavu = nazevStavu;
    }

    public int getVybaveniID() {
        return vybaveniID;
    }

    public String getNazev() {
        return nazev;
    }

    public String getInventarniCislo() {
        return inventarniCislo;
    }

    public String getZnacka() {
        return znacka;
    }

    public String getVelikost() {
        return velikost;
    }

    public String getNazevTypu() {
        return nazevTypu;
    }

    public double getCenaZaDen() {
        return cenaZaDen;
    }

    public String getNazevStavu() {
        return nazevStavu;
    }
}