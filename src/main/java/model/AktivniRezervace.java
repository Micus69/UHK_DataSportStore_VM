package model;

public class AktivniRezervace {

    private int rezervaceID;
    private String zakaznik;
    private String datumOd;
    private String datumDo;
    private String stavRezervace;

    public AktivniRezervace(int rezervaceID, String zakaznik, String datumOd, String datumDo, String stavRezervace) {
        this.rezervaceID = rezervaceID;
        this.zakaznik = zakaznik;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.stavRezervace = stavRezervace;
    }

    public int getRezervaceID() {
        return rezervaceID;
    }

    public String getZakaznik() {
        return zakaznik;
    }

    public String getDatumOd() {
        return datumOd;
    }

    public String getDatumDo() {
        return datumDo;
    }

    public String getStavRezervace() {
        return stavRezervace;
    }
}