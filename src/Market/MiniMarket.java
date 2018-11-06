package Market;

import Gestiune.Factura;
import Gestiune.Produs;
import Gestiune.ProdusComandat;
import java.util.Vector;

public class MiniMarket extends Magazin {

    @Override
    public int calculScutiriTaxe() {
        Double totalVanzari = getTotalCuTaxe();

        // Jumatate din vanzarile totale
        totalVanzari /= 2;

        for (Factura i: dosar) {
            Vector<ProdusComandat> listaProduse = i.getListaProduse();
            for (ProdusComandat j: listaProduse) {
                Produs p = j.getProdus();
                double totalTara = getTotalTaraCuTaxe(p.getTaraOrigine());
                if (totalTara > totalVanzari)
                    return 10;
            }
        }

        return 0;
    }
}
