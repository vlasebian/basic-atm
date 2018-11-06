package Gestiune;

import Market.*;
import java.io.*;
import java.util.*;
import java.util.Collections;
import java.text.DecimalFormat;

public class Gestiune {
    private Vector<Produs> listaProduse;
    private Vector<Magazin> listaMagazine;
    private Hashtable<String, Hashtable<String, Integer>> taxe;

    private ArrayList<String> tari;
    private ArrayList<String> tariNeordonate;
    private Vector<String> categorii;

    // Implementarea clasei folosind modelul Singleton
    private static Gestiune inst = null;

    private Gestiune() { }

    public static Gestiune getInstance() {
        if (inst == null) {
            inst = new Gestiune();
            return inst;
        }
        else {
            return inst;
        }
    }

    public Vector<Produs> getListaProduse() {
        return listaProduse;
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

    public Vector<Magazin> getListaMagazine () {
        return listaMagazine;
    }

    // readFiles() realizează citirea din fișiere și salvarea informațiilor citite în
    // vectorii respectivi
    public void readFiles(File fisierProduse, File fisierTaxe, File fisierFacturi) {
        Reader r = new Reader(fisierProduse, fisierTaxe, fisierFacturi);

        listaProduse = r.getListaProduse();
        listaMagazine = r.getListaMagazine();
        taxe = r.getTaxe();
        tari = r.getTari();
        categorii = r.getCategorii();
        tariNeordonate = r.getTariNeordonate();
    }

    // Caută factura cea mai mare
    public Factura facturaMaxima() {
        Factura f = null;
        double max = 0.0;
        for (Magazin i: listaMagazine) {
            for (Factura j: i.getFacturi()) {
                if (j.getTotalFaraTaxe() > max) {
                    max = j.getTotalFaraTaxe();
                    f = j;
                }
            }
        }
        return f;
    }

    // Caută magazinul cu cele mai mari costuri
    public Magazin magazinMaxim() {
        Magazin m = null;
        double max = 0.0;

        for (Magazin i: listaMagazine) {
            if (i.getTotalCuTaxe() > max) {
                max = i.getTotalCuTaxe();
                m = i;
            }
        }
        return m;
    }

    // Caută magazinul cu cele mai mari costuri pentru o anumită
    // tară
    public Magazin magazinMaximTara(String tara) {
        Magazin m = null;
        double max = 0.0;
        for (Magazin i: listaMagazine) {
            if (i.getTotalTaraCuTaxe(tara) > max) {
                max = i.getTotalCuTaxe();
                m = i;
            }
        }
        return m;
    }

    // Caută magazinul cu cele mai mari costuri pentru o anumită
    // categorie
    public Magazin magazinMaximCategorie(String categorie) {
        Magazin m = null;
        double max = 0.0;
        for (Magazin i: listaMagazine) {
            if (i.getTotalCategorie(categorie) > max) {
                max = i.getTotalCategorie(categorie);
                m = i;
            }
        }

        return m;
    }

    public void ordoneazaProduseAlfabetic() {
        Collections.sort(listaProduse, new Comparator<Produs>() {
            @Override
            public int compare(Produs a, Produs b) {
                return a.getDenumire().compareTo(b.getDenumire());
            }
        });
    }

    public void ordoneazaProduseDupaTara() {
        Collections.sort(listaProduse, new Comparator<Produs>() {
            @Override
            public int compare(Produs a, Produs b) {
                return a.getTaraOrigine().compareTo(b.getTaraOrigine());
            }
        });
    }

    public void writeOutputToFile() {
        // Format pentru afisarea numerelor
        DecimalFormat df = new DecimalFormat("#.####");

        // Creez 3 vectori pentru afișarea magazinelor în out.txt
        Vector<Magazin> miniMarkets = new Vector<Magazin>();
        Vector<Magazin> mediumMarkets = new Vector<Magazin>();
        Vector<Magazin> hyperMarkets = new Vector<Magazin>();

        for (Magazin i: listaMagazine) {
            if (i instanceof MiniMarket) {
                miniMarkets.add(i);
            }
            if (i instanceof MediumMarket) {
                mediumMarkets.add(i);
            }
            if (i instanceof HyperMarket) {
                hyperMarkets.add(i);
            }
        }

        // Comparator pentru magazine în funcție de totalul fără taxe
        class ComparaMagazine implements Comparator<Magazin> {
            @Override
            public int compare(Magazin m1, Magazin m2) {
                if (m1.getTotalFaraTaxe() > m2.getTotalFaraTaxe()) {
                    return 1;
                } else if (m1.getTotalFaraTaxe() < m2.getTotalFaraTaxe()) {
                    return -1;
                }

                return 0;
            }
        }

        // Comparator pentru facturi în funcție de totalul cu taxe
        class ComparaFacturi implements Comparator<Factura> {
            @Override
            public int compare(Factura a, Factura b) {
                if (a.getTotalCuTaxe() < b.getTotalCuTaxe()) {
                    return -1;
                } else if (a.getTotalCuTaxe() > b.getTotalCuTaxe()) {
                    return 1;
                }

                return 0;
            }
        }

        // Sortez magazinele dupa suma totala fara taxe
        Collections.sort(miniMarkets, new ComparaMagazine());
        Collections.sort(mediumMarkets, new ComparaMagazine());
        Collections.sort(hyperMarkets, new ComparaMagazine());

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));

            // Afisez magazinele de tip MiniMarket
            writer.write("MiniMarket");
            writer.newLine();
            for (Magazin i: miniMarkets) {
                writer.write(i.getNume());
                writer.newLine();
                writer.newLine();

                writer.write("Total " + df.format(i.getTotalFaraTaxe()) + " " + df.format(i.getTotalCuTaxe()) +
                        " " + df.format(i.getTotalCuTaxeScutite()));
                writer.newLine();
                writer.newLine();


                writer.write("Tara");
                writer.newLine();
                for (String j : tari) {
                    if (i.getTotalTaraFaraTaxe(j) != 0) {
                        writer.write(j + " " + df.format(i.getTotalTaraFaraTaxe(j)) + " " + df.format(i.getTotalTaraCuTaxe(j)) +
                                 " " + df.format(i.getTotalTaraCuTaxeScutite(j)));
                        writer.newLine();
                    } else {
                        writer.write(j + " 0");
                        writer.newLine();
                    }
                }
                writer.newLine();

                // Ordonare facturi
                Vector<Factura> v = i.getFacturi();
                Collections.sort(v, new ComparaFacturi());

                for (Factura j : i.getFacturi()) {
                    writer.write(j.getDenumire());
                    writer.newLine();
                    writer.newLine();
                    writer.write("Total " + df.format(j.getTotalFaraTaxe()) + " " + df.format(j.getTotalCuTaxe()));
                    writer.newLine();
                    writer.newLine();
                    writer.write("Tara");
                    writer.newLine();
                    for (String k : tari) {
                        if (j.getTotalTaraFaraTaxe(k) != 0) {
                            writer.write(k + " " + df.format(j.getTotalTaraFaraTaxe(k)) + " " + df.format(j.getTotalTaraCuTaxe(k)));
                            writer.newLine();
                        } else {
                            writer.write(k + " 0");
                            writer.newLine();
                        }
                    }
                    writer.newLine();
                }
            }

            // Afisez magazinele de tip MediumMarket
            writer.write("MediumMarket");
            writer.newLine();
            for (Magazin i: mediumMarkets) {
                writer.write(i.getNume());
                writer.newLine();
                writer.newLine();

                writer.write("Total " + df.format(i.getTotalFaraTaxe()) + " " + df.format(i.getTotalCuTaxe()) +
                        " " + df.format(i.getTotalCuTaxeScutite()));
                writer.newLine();
                writer.newLine();


                writer.write("Tara");
                writer.newLine();
                for (String j : tari) {
                    if (i.getTotalTaraFaraTaxe(j) != 0) {
                        writer.write(j + " " + df.format(i.getTotalTaraFaraTaxe(j)) + " " + df.format(i.getTotalTaraCuTaxe(j)) +
                                " " + df.format(i.getTotalTaraCuTaxeScutite(j)));
                        writer.newLine();
                    } else {
                        writer.write(j + " 0");
                        writer.newLine();
                    }
                }
                writer.newLine();

                Vector<Factura> v = i.getFacturi();
                Collections.sort(v, new ComparaFacturi());

                for (Factura j : i.getFacturi()) {
                    writer.write(j.getDenumire());
                    writer.newLine();
                    writer.newLine();
                    writer.write("Total " + df.format(j.getTotalFaraTaxe()) + " " + df.format(j.getTotalCuTaxe()));
                    writer.newLine();
                    writer.newLine();
                    writer.write("Tara");
                    writer.newLine();
                    for (String k : tari) {
                        if (j.getTotalTaraFaraTaxe(k) != 0) {
                            writer.write(k + " " + df.format(j.getTotalTaraFaraTaxe(k)) + " " + df.format(j.getTotalTaraCuTaxe(k)));
                            writer.newLine();
                        } else {
                            writer.write(k + " 0");
                            writer.newLine();
                        }
                    }
                    writer.newLine();
                }
            }

            writer.write("HyperMarket");
            writer.newLine();
            for (Magazin i: hyperMarkets) {
                writer.write(i.getNume());
                writer.newLine();
                writer.newLine();

                writer.write("Total " + df.format(i.getTotalFaraTaxe()) + " " + df.format(i.getTotalCuTaxe()) +
                        " " + df.format(i.getTotalCuTaxeScutite()));
                writer.newLine();
                writer.newLine();


                writer.write("Tara");
                writer.newLine();
                for (String j : tari) {
                    if (i.getTotalTaraFaraTaxe(j) != 0) {
                        writer.write(j + " " + df.format(i.getTotalTaraFaraTaxe(j)) + " " + df.format(i.getTotalTaraCuTaxe(j)) +
                                " " + df.format(i.getTotalTaraCuTaxeScutite(j)));
                        writer.newLine();
                    } else {
                        writer.write(j + " 0");
                        writer.newLine();
                    }
                }
                writer.newLine();

                Vector<Factura> v = i.getFacturi();
                Collections.sort(v, new ComparaFacturi());

                for (Factura j : i.getFacturi()) {
                    writer.write(j.getDenumire());
                    writer.newLine();
                    writer.newLine();
                    writer.write("Total " + df.format(j.getTotalFaraTaxe()) + " " + df.format(j.getTotalCuTaxe()));
                    writer.newLine();
                    writer.newLine();
                    writer.write("Tara");
                    writer.newLine();
                    for (String k : tari) {
                        if (j.getTotalTaraFaraTaxe(k) != 0) {
                            writer.write(k + " " + df.format(j.getTotalTaraFaraTaxe(k)) + " " + df.format(j.getTotalTaraCuTaxe(k)));
                            writer.newLine();
                        } else {
                            writer.write(k + " 0");
                            writer.newLine();
                        }
                    }
                    writer.newLine();
                }
            }

            writer.close();

        } catch (IOException e) {
            System.err.println("[Eroare]Nu s-a putut crea un fișier de output!");
            System.exit(3);
        }
    }
}
