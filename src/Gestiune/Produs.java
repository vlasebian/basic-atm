package Gestiune;

import java.util.StringTokenizer;

public class Produs {
    private String denumire;
    private String categorie;
    private String taraOrigine;
    private double pret;

    public Produs(String denumire, String categorie, String taraOrigine, double pret) {
        this.denumire = denumire;
        this.categorie = categorie;
        this.taraOrigine = taraOrigine;
        this.pret = pret;
    }

    public Produs() {
        this(null , null, null, 0);
    }

    public void setDenumire(String s) {
        this.denumire = s;
    }

    public String getDenumire() {
        return this.denumire;
    }

    public void setCategorie(String s) {
        this.categorie = s;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public void setTaraOrigine(String s) {
        this.taraOrigine = s;
    }

    public String getTaraOrigine() {
        return this.taraOrigine;
    }

    public void setPret(double x) {
        this.pret = x;
    }

    public double getPret() {
        return this.pret;
    }

    @Override
    public String toString() {
        return denumire + ' ' + categorie + ' ' + taraOrigine +
                ' ' + pret;
    }
}
