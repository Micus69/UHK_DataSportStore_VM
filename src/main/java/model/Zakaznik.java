package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Zakaznik")
public class Zakaznik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZakaznikID")
    private Integer zakaznikID;

    @Column(name = "ZamestnanecID", nullable = false)
    private Integer zamestnanecID;

    @Column(name = "Ulice", nullable = false, length = 100)
    private String ulice;

    @Column(name = "CisloPopisne", nullable = false, length = 20)
    private String cisloPopisne;

    @Column(name = "Mesto", nullable = false, length = 100)
    private String mesto;

    @Column(name = "PSC", nullable = false, length = 20)
    private String psc;

    @Column(name = "DatumRegistrace", nullable = false)
    private LocalDate datumRegistrace;

    @Column(name = "Email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "Jmeno", nullable = false, length = 100)
    private String jmeno;

    @Column(name = "Prijmeni", nullable = false, length = 100)
    private String prijmeni;

    @Column(name = "Telefon", nullable = false, length = 30)
    private String telefon;

    public Zakaznik() {
    }

    public Zakaznik(Integer zamestnanecID, String ulice, String cisloPopisne, String mesto,
                    String psc, LocalDate datumRegistrace, String email,
                    String jmeno, String prijmeni, String telefon) {
        this.zamestnanecID = zamestnanecID;
        this.ulice = ulice;
        this.cisloPopisne = cisloPopisne;
        this.mesto = mesto;
        this.psc = psc;
        this.datumRegistrace = datumRegistrace;
        this.email = email;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.telefon = telefon;
    }

    public Integer getZakaznikID() {
        return zakaznikID;
    }

    public void setZakaznikID(Integer zakaznikID) {
        this.zakaznikID = zakaznikID;
    }

    public Integer getZamestnanecID() {
        return zamestnanecID;
    }

    public void setZamestnanecID(Integer zamestnanecID) {
        this.zamestnanecID = zamestnanecID;
    }

    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public String getCisloPopisne() {
        return cisloPopisne;
    }

    public void setCisloPopisne(String cisloPopisne) {
        this.cisloPopisne = cisloPopisne;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public LocalDate getDatumRegistrace() {
        return datumRegistrace;
    }

    public void setDatumRegistrace(LocalDate datumRegistrace) {
        this.datumRegistrace = datumRegistrace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}