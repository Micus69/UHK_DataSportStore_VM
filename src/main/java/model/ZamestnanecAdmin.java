package model;

import java.time.LocalDate;

public class ZamestnanecAdmin {

    private int zamestnanecID;
    private LocalDate datumNastupu;
    private String heslo;
    private String jmeno;
    private String login;
    private String pozice;
    private String prijmeni;
    private String role;

    public ZamestnanecAdmin(int zamestnanecID, LocalDate datumNastupu, String heslo,
                            String jmeno, String login, String pozice,
                            String prijmeni, String role) {
        this.zamestnanecID = zamestnanecID;
        this.datumNastupu = datumNastupu;
        this.heslo = heslo;
        this.jmeno = jmeno;
        this.login = login;
        this.pozice = pozice;
        this.prijmeni = prijmeni;
        this.role = role;
    }

    public int getZamestnanecID() { return zamestnanecID; }
    public LocalDate getDatumNastupu() { return datumNastupu; }
    public String getHeslo() { return heslo; }
    public String getJmeno() { return jmeno; }
    public String getLogin() { return login; }
    public String getPozice() { return pozice; }
    public String getPrijmeni() { return prijmeni; }
    public String getRole() { return role; }
}