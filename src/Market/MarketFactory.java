package Market;

public class MarketFactory {

    public static Magazin createMarket(String type) {
        if (type.equalsIgnoreCase("MiniMarket")) {
            return new MiniMarket();
        } else if (type.equalsIgnoreCase("MediumMarket")) {
            return new MediumMarket();
        } else if(type.equalsIgnoreCase("HyperMarket")) {
            return new HyperMarket();
        } else {
            throw new IllegalArgumentException("Tipul de magazin '" + type + "' este incorect");
        }
    }

}
