package Gestiune;

import java.util.Vector;

public class Factura {
    private String denumire;
    private Vector<ProdusComandat> listaProduse;

    public Factura(String denumire) {
        this.denumire = denumire;
        listaProduse = new Vector<ProdusComandat>();
    }

    public Factura() {
        this(null);
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDenumire() {
        return this.denumire;
    }

    public Vector<ProdusComandat> getListaProduse() {
        return listaProduse;
    }

    public void addProdusComandat(ProdusComandat p) {
        listaProduse.add(p);
    }

    public double getTotalFaraTaxe() {
        double total = 0.0;

        for (ProdusComandat i : listaProduse) {
            total += i.getProdus().getPret() * i.getCantitate();
        }

        return total;
    }

    public double getTotalCuTaxe() {
        double total = getTotalFaraTaxe();
        double taxe = 0.0;

        for (ProdusComandat i : listaProduse) {
            taxe += i.getTaxa() * i.getCantitate();
        }

        return total + taxe;
    }

    public double getTaxe() {
        double taxe = 0.0;

        for (ProdusComandat i: listaProduse) {
            taxe += i.getTaxa() * i.getCantitate();
        }

        return taxe;
    }

    public double getTotalTaraFaraTaxe(String tara) {
        double totalTaraFaraTaxe = 0.0;

        for (ProdusComandat i: listaProduse) {
            if (i.getProdus().getTaraOrigine().equalsIgnoreCase(tara)) {
               totalTaraFaraTaxe += i.getProdus().getPret() * i.getCantitate();
            }
        }

        return totalTaraFaraTaxe;
    }

    public double getTotalTaraCuTaxe(String tara) {
        double totalTaraFaraTaxe = getTotalTaraFaraTaxe(tara);
        double totalTaraTaxe = 0.0;

        for (ProdusComandat i: listaProduse) {
            if (i.getProdus().getTaraOrigine().equalsIgnoreCase(tara)) {
                totalTaraTaxe += i.getTaxa() * i.getCantitate();
            }
        }

        return totalTaraFaraTaxe + totalTaraTaxe;
    }

    public double getTaxeTara(String tara) {
        double totalTaraTaxe = 0.0;

        for (ProdusComandat i: listaProduse) {
            if (i.getProdus().getTaraOrigine().equalsIgnoreCase(tara)) {
                totalTaraTaxe += i.getTaxa() * i.getCantitate();
            }
        }

        return totalTaraTaxe;

    }

    public Double getTotalCategorie(String categorie) {
        double totalCategorie = 0.0;

        for (ProdusComandat i: listaProduse) {
            if (i.getProdus().getCategorie().equalsIgnoreCase(categorie)) {
                totalCategorie += (i.getTaxa() + i.getProdus().getPret()) * i.getCantitate();
            }
        }

        return totalCategorie;
    }

    @Override
    public String toString() {
       return denumire + " " + listaProduse.toString();
    }
}
