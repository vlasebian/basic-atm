package Gestiune;

import Market.Magazin;
import Market.MarketFactory;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Reader {
    private Vector<Produs> listaProduse;
    private Vector<Magazin> listaMagazine;
    // <Tara, <Categorie, Procent>>
    private Hashtable<String, Hashtable<String, Integer>> taxe;

    ArrayList<String> tari;
    ArrayList<String> tariNeordonate;
    Vector<String> categorii;

    public Reader(File fisierProduse, File fisierTaxe, File fisierFacturi) {
        listaProduse = new Vector<Produs>();
        listaMagazine = new Vector<Magazin>();
        taxe = new Hashtable<String, Hashtable<String, Integer>>();
        tari = new ArrayList<String>();
        tariNeordonate = new ArrayList<String>();
        categorii = new Vector<String>();

        readProducts(fisierProduse);
        readTaxes(fisierTaxe);
        readBills(fisierFacturi);
    }

    public Vector<Produs> getListaProduse() {
        return listaProduse;
    }

    public Vector<Magazin> getListaMagazine() {
        return listaMagazine;
    }

    public Hashtable<String, Hashtable<String, Integer>> getTaxe() {
        return taxe;
    }

    public ArrayList<String> getTari() {
        return tari;
    }

    public ArrayList<String> getTariNeordonate() {
        return tariNeordonate;
    }

    public Vector<String> getCategorii() {
        return categorii;
    }

    // Funcția citește din fișierul produse.txt, creează obiectele Produs corespunzătaore
    // și le adaugă în vectorul listaProduse
    public void readProducts(File fisier) {
        try {
            Scanner s = new Scanner(fisier);
            // Am ales ArrayList deoarece am nevoie să mențin ordinea de inserare a codului țărilor
            ArrayList<String> tari = new ArrayList<String>();
            String crtToken;

            // Citesc țarile
            String crtLine = s.nextLine();
            StringTokenizer str = new StringTokenizer(crtLine);

            // Sar peste primele două cuvinte descriptive
            crtToken = str.nextToken();
            crtToken = str.nextToken();

            // Salvez codul țarilor în lista tari
            while (str.hasMoreTokens()) {
                String taraCrt = str.nextToken();
                tari.add(taraCrt);
                tariNeordonate.add(taraCrt);
            }

            // Citesc produsele și le salvez în vectorul listaProduse
            while (s.hasNextLine()) {
                int crtProduct = 0;
                crtLine = s.nextLine();
                str = new StringTokenizer(crtLine);

                String produs = str.nextToken();
                String categorie = str.nextToken();
                if (categorii.contains(categorie) == false) {
                    categorii.add(categorie);
                }

                while (str.hasMoreTokens()) {
                    double pretNeimpozitat = Double.parseDouble(str.nextToken());
                    Produs p = new Produs(produs, categorie, tari.get(crtProduct), pretNeimpozitat);
                    crtProduct ++;
                    listaProduse.add(p);
                }
            }

            s.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fișierul taxe.txt nu a fost găsit!");
            System.exit(1);
        }
    }

    public void readTaxes(File fisier) {
        try {
            Scanner s = new Scanner(fisier);
            ArrayList<Hashtable<String, Integer>> hashtables = new ArrayList<Hashtable<String, Integer>>();
            String crtToken;

            // Citesc țarile
            String crtLine = s.nextLine();
            StringTokenizer str = new StringTokenizer(crtLine);

            // Sar peste primul cuvânt descriptiv
            crtToken = str.nextToken();

            // Salvez codul țarilor în lista tari
            while (str.hasMoreTokens()) {
                tari.add(str.nextToken());
                hashtables.add(new Hashtable<String, Integer>());
            }

            // Citesc taxele și salvez perechea <categorie, procent> într-un hashtable
            // dintr-un vector(hashtable care se află în vectorul hashtables pe aceeași
            // poziție cu țara corespunzătoare din vectorul tari)
            while (s.hasNextLine()) {
                int crtTax = 0;
                crtLine = s.nextLine();
                str = new StringTokenizer(crtLine);

                String categorie = str.nextToken();


                while (str.hasMoreTokens()) {
                    Integer procent = Integer.parseInt(str.nextToken());
                    Hashtable<String, Integer> crtHashtable = hashtables.get(crtTax);
                    crtHashtable.put(categorie, procent);
                    crtTax ++;
                }
            }

            for (int i = 0; i < tari.size(); i ++) {
                taxe.put(tari.get(i), hashtables.get(i));
            }

            Collections.sort(tari);
            s.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fișierul produse.txt nu a fost găsit!");
            System.exit(1);
        }
    }

    public void readBills(File fisier) {
        try {
            Scanner s = new Scanner(fisier);
            Magazin ultimulMagazin = null;
            Factura ultimaFactura = null;

            while (s.hasNextLine()) {
                StringTokenizer crtLine = new StringTokenizer(s.nextLine(), " :");

                if (crtLine.hasMoreTokens()) {
                    String crtToken = crtLine.nextToken();

                    if (crtToken.equalsIgnoreCase("Magazin")) {
                        Magazin m = MarketFactory.createMarket(crtLine.nextToken());
                        m.setNume(crtLine.nextToken());
                        listaMagazine.add(m);
                        ultimulMagazin = m;
                        ultimaFactura = null;
                        continue;
                    }

                    if (crtToken.startsWith("Factura")) {
                        Factura f = new Factura(crtToken);
                        ultimulMagazin.addFactura(f);
                        ultimaFactura = f;
                        continue;
                    }

                    if (crtToken.equalsIgnoreCase("DenumireProdus")) {
                        continue;
                    }

                    // În crtToken va fi numele produsului, citesc țara și cantitatea
                    String tara = crtLine.nextToken();
                    int cantitate = Integer.parseInt(crtLine.nextToken());

                    // Caut prețul și categoria după nume și țară
                    double pret = 0;
                    String categorie = null;
                    Produs crtProdus = new Produs();

                    for (Produs p:listaProduse) {
                        if (p.getDenumire().equalsIgnoreCase(crtToken) && p.getTaraOrigine().equalsIgnoreCase(tara)) {
                            categorie = p.getCategorie();
                            pret = p.getPret();

                            // Pun datele în noul produs
                            crtProdus.setCategorie(categorie);
                            crtProdus.setDenumire(crtToken);
                            crtProdus.setPret(pret);
                            crtProdus.setTaraOrigine(tara);
                            break;
                        }
                    }

                    // Caut procentul asociat cateogriei de produs și țării
                    Hashtable<String, Integer> h = taxe.get(tara);
                    double procent =  (h.get(categorie)).doubleValue();
                    // Calculez taxa
                    double taxa = (procent / 100) * (double)crtProdus.getPret();
                    ProdusComandat pc = new ProdusComandat(crtProdus, cantitate, taxa);
                    ultimaFactura.addProdusComandat(pc);
                }

            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fișierul facturi.txt nu a fost găsit!");
            System.exit(1);
        }

    }

}
