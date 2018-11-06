package Market;

public interface IMagazin {
    public double getTotalFaraTaxe();
    public double getTotalCuTaxe();
    public double getTotalCuTaxeScutite();
    public double getTotalTaraFaraTaxe(String tara);
    public double getTotalTaraCuTaxe(String tara);
    public double getTotalTaraCuTaxeScutite(String tara);
    public int calculScutiriTaxe();
}
