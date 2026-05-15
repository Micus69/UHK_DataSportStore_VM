package model;

public class StavVybaveni {

    private int stavVybaveniID;
    private boolean jeDostupneProPujceni;
    private String nazevStavu;
    private String popisStavu;

    public StavVybaveni() {
    }

    public StavVybaveni(int stavVybaveniID,
                        boolean jeDostupneProPujceni,
                        String nazevStavu,
                        String popisStavu) {

        this.stavVybaveniID = stavVybaveniID;
        this.jeDostupneProPujceni = jeDostupneProPujceni;
        this.nazevStavu = nazevStavu;
        this.popisStavu = popisStavu;
    }

    public int getStavVybaveniID() {
        return stavVybaveniID;
    }

    public void setStavVybaveniID(int stavVybaveniID) {
        this.stavVybaveniID = stavVybaveniID;
    }

    public boolean isJeDostupneProPujceni() {
        return jeDostupneProPujceni;
    }

    public void setJeDostupneProPujceni(boolean jeDostupneProPujceni) {
        this.jeDostupneProPujceni = jeDostupneProPujceni;
    }

    public String getNazevStavu() {
        return nazevStavu;
    }

    public void setNazevStavu(String nazevStavu) {
        this.nazevStavu = nazevStavu;
    }

    public String getPopisStavu() {
        return popisStavu;
    }

    public void setPopisStavu(String popisStavu) {
        this.popisStavu = popisStavu;
    }

    @Override
    public String toString() {
        return nazevStavu;
    }
}