package Market;

import Gestiune.Factura;
import Gestiune.Produs;
import Gestiune.ProdusComandat;

import java.util.Vector;

public class HyperMarket extends Magazin {

    @Override
    public int calculScutiriTaxe() {
        Double totalVanzari = getTotalCuTaxe();

        // Jumatate din vanzarile totale
        totalVanzari /= 10;

        for (Factura i: dosar) {
            if (i.getTotalCuTaxe() > totalVanzari) {
                    return 1;
            }
        }

        return 0;
    }
}
