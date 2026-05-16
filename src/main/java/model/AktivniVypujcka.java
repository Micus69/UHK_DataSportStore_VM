package model;

public class AktivniVypujcka {

    private int vypujckaID;
    private String zakaznikJmeno;
    private String zakaznikPrijmeni;
    private String email;
    private String nazevVybaveni;
    private String inventarniCislo;
    private String nazevTypu;
    private double cenaZaDen;
    private int pocetDni;
    private double cenaPolozky;
    private double cenaCelkem;
    private String datumVypujceni;
    private String planovaneVraceni;
    private String stavVypujcky;

    public AktivniVypujcka(int vypujckaID, String zakaznikJmeno, String zakaznikPrijmeni,
                           String email, String nazevVybaveni, String inventarniCislo,
                           String nazevTypu, double cenaZaDen, int pocetDni,
                           double cenaPolozky, double cenaCelkem, String datumVypujceni,
                           String planovaneVraceni, String stavVypujcky) {
        this.vypujckaID = vypujckaID;
        this.zakaznikJmeno = zakaznikJmeno;
        this.zakaznikPrijmeni = zakaznikPrijmeni;
        this.email = email;
        this.nazevVybaveni = nazevVybaveni;
        this.inventarniCislo = inventarniCislo;
        this.nazevTypu = nazevTypu;
        this.cenaZaDen = cenaZaDen;
        this.pocetDni = pocetDni;
        this.cenaPolozky = cenaPolozky;
        this.cenaCelkem = cenaCelkem;
        this.datumVypujceni = datumVypujceni;
        this.planovaneVraceni = planovaneVraceni;
        this.stavVypujcky = stavVypujcky;
    }

    public int getVypujckaID() { return vypujckaID; }
    public String getZakaznikJmeno() { return zakaznikJmeno; }
    public String getZakaznikPrijmeni() { return zakaznikPrijmeni; }
    public String getEmail() { return email; }
    public String getNazevVybaveni() { return nazevVybaveni; }
    public String getInventarniCislo() { return inventarniCislo; }
    public String getNazevTypu() { return nazevTypu; }
    public double getCenaZaDen() { return cenaZaDen; }
    public int getPocetDni() { return pocetDni; }
    public double getCenaPolozky() { return cenaPolozky; }
    public double getCenaCelkem() { return cenaCelkem; }
    public String getDatumVypujceni() { return datumVypujceni; }
    public String getPlanovaneVraceni() { return planovaneVraceni; }
    public String getStavVypujcky() { return stavVypujcky; }
}