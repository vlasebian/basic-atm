package Market;

import Gestiune.Factura;
import java.util.Vector;

public abstract class Magazin implements IMagazin {
    protected String nume;
    protected Vector<Factura> dosar;

    public Magazin(String s) {
        this.nume = s;
        dosar = new Vector<Factura>();
    }

    public Magazin() {
        this(null);
    }

    public void setNume(String s) {
        nume = s;
    }

    public String getNume() {
        return nume;
    }

    public void addFactura(Factura f) {
        dosar.add(f);
    }

    public Vector<Factura> getFacturi() {
        return this.dosar;
    }

    @Override
    public double getTotalFaraTaxe() {
        double totalFaraTaxe = 0.0;

        for (Factura i: dosar) {
            totalFaraTaxe += i.getTotalFaraTaxe();
        }

        return totalFaraTaxe;
    }

    @Override
    public double getTotalCuTaxe() {
        double totalCuTaxe = 0.0;

        for (Factura i: dosar) {
            totalCuTaxe += i.getTotalCuTaxe();
        }

        return totalCuTaxe;
    }

    @Override
    public double getTotalCuTaxeScutite() {
        double totalCuTaxeScutite = 0.0;
        double taxe = 0.0;
        double procentScutire = (int)calculScutiriTaxe();

        if (procentScutire == 0) {
            return getTotalCuTaxe();
        }

        // Suma totala
        totalCuTaxeScutite = getTotalCuTaxe();

        // Scad taxele scutite
        totalCuTaxeScutite -= totalCuTaxeScutite * (procentScutire / 100);


        return totalCuTaxeScutite;
    }

    @Override
    public double getTotalTaraFaraTaxe(String tara) {
        double totalTaraFaraTaxe = 0.0;

        for (Factura i: dosar) {
            totalTaraFaraTaxe += i.getTotalTaraFaraTaxe(tara);
        }

        return totalTaraFaraTaxe;
    }

    @Override
    public double getTotalTaraCuTaxe(String tara) {
        double totalTaraCuTaxe = 0.0;

        for (Factura i:dosar) {
            totalTaraCuTaxe += i.getTotalTaraCuTaxe(tara);
        }

        return totalTaraCuTaxe;
    }

    @Override
    public double getTotalTaraCuTaxeScutite(String tara) {
        double totalTaraCuTaxeScutite = getTotalTaraCuTaxe(tara);

        double scutiri = (double)calculScutiriTaxe();
        totalTaraCuTaxeScutite -= totalTaraCuTaxeScutite * (scutiri / 100);

        return totalTaraCuTaxeScutite;
    }

    public Double getTotalCategorie(String categorie) {
        Double totalCategorie = 0.0;

        for (Factura i: dosar) {
            totalCategorie += i.getTotalCategorie(categorie);
        }

        return totalCategorie;
    }

    @Override
    public abstract int calculScutiriTaxe();

    @Override
    public String toString() {
        return nume + " " + dosar.toString();
    }
}
