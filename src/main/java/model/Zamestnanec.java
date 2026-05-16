package model;

public class Zamestnanec {

    private int zamestnanecID;
    private String login;
    private String role;
    private String jmeno;
    private String prijmeni;

    public Zamestnanec(int zamestnanecID, String login, String role, String jmeno, String prijmeni) {
        this.zamestnanecID = zamestnanecID;
        this.login = login;
        this.role = role;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
    }

    public int getZamestnanecID() {
        return zamestnanecID;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }
}