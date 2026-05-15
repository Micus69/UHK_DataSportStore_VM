package model;

import java.time.LocalDate;
import java.util.List;

public class Rezervace {

    private String jmeno;
    private String prijmeni;
    private String email;
    private String telefon;

    private String ulice;
    private String cisloPopisne;
    private String mesto;
    private String psc;

    private int zamestnanecID;
    private LocalDate datumOd;
    private LocalDate datumDo;

    private List<Integer> vybaveniIds;

    public Rezervace(String jmeno,
                     String prijmeni,
                     String email,
                     String telefon,
                     String ulice,
                     String cisloPopisne,
                     String mesto,
                     String psc,
                     int zamestnanecID,
                     LocalDate datumOd,
                     LocalDate datumDo,
                     List<Integer> vybaveniIds) {

        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.email = email;
        this.telefon = telefon;
        this.ulice = ulice;
        this.cisloPopisne = cisloPopisne;
        this.mesto = mesto;
        this.psc = psc;
        this.zamestnanecID = zamestnanecID;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.vybaveniIds = vybaveniIds;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getUlice() {
        return ulice;
    }

    public String getCisloPopisne() {
        return cisloPopisne;
    }

    public String getMesto() {
        return mesto;
    }

    public String getPsc() {
        return psc;
    }

    public int getZamestnanecID() {
        return zamestnanecID;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public List<Integer> getVybaveniIds() {
        return vybaveniIds;
    }
}