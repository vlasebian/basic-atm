package Market;

import Gestiune.Factura;
import Gestiune.Produs;
import Gestiune.ProdusComandat;

import java.util.Vector;

public class MediumMarket extends Magazin {

    @Override
    public int calculScutiriTaxe() {
        Double totalVanzari = getTotalCuTaxe();

        // Jumatate din vanzarile totale
        totalVanzari /= 2;

        for (Factura i: dosar) {
            Vector<ProdusComandat> listaProduse = i.getListaProduse();
            for (ProdusComandat j: listaProduse) {
                Produs p = j.getProdus();
                Double totalCategorie = getTotalCategorie(p.getCategorie());

                if (totalCategorie > totalVanzari) {
                    return 5;
                }
            }
        }

        return 0;
    }
}
