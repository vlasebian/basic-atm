package Gestiune;

public class ProdusComandat {
    private Produs produs;
    private int cantitate;
    private Double taxa;

    public ProdusComandat(Produs p, int c, Double t) {
        this.produs = p;
        this.cantitate = c;
        this.taxa = t;
    }

    public ProdusComandat() {
        this(null,0, 0.0);
    }

    public void setProdus(Produs p) {
        this.produs = p;
    }

    public Produs getProdus() {
        return this.produs;
    }

    public void setCantitate(int x) {
        this.cantitate = x;
    }

    public int getCantitate() {
        return this.cantitate;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Double getTaxa() {
        return taxa;
    }

    @Override
    public String toString() {
       return "Produs: " + produs + " Taxa: " + taxa + " Cantitate: " + cantitate;
    }
}
