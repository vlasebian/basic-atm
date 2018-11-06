package Interfata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import Gestiune.Gestiune;
import Gestiune.Factura;
import Market.Magazin;

public class Statistici extends JPanel {
    private JPanel panouPrincipal;
    private CardLayout cl;
    private JButton inapoi = new JButton("Înapoi la meniu");
    private Gestiune g;

    private JLabel magazin = new JLabel("Magazinul cu cele mai mari vânzări");
    private JTextArea vanzariMari = new JTextArea();
    private JLabel tari = new JLabel("Magazinele cu cele mai mari vânzări pe țară");
    private JTextArea vanzariTari = new JTextArea();
    private JLabel categorii = new JLabel("Magazinele cu cele mai mari vânzări pe categorii");
    private JTextArea vanzariCategorii = new JTextArea();
    private JLabel factura = new JLabel("Factura cea mai mare");
    private JTextArea facturaCeaMaiMare = new JTextArea();

    private DecimalFormat df = new DecimalFormat("#.####");


    public Statistici(JPanel p, CardLayout cl, Gestiune g) {
        super();
        panouPrincipal = p;
        this.cl = cl;
        this.g = g;

        JPanel panouSecundar = new JPanel();

        AscultaButoane a = new AscultaButoane();
        inapoi.addActionListener(a);

        vanzariMari.setEditable(false);
        vanzariMari.setOpaque(true);
        vanzariTari.setEditable(false);
        vanzariTari.setOpaque(true);
        vanzariCategorii.setEditable(false);
        vanzariCategorii.setOpaque(true);
        facturaCeaMaiMare.setEditable(false);
        facturaCeaMaiMare.setOpaque(true);

        panouSecundar.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 2;
        gc.weighty = 2;
        gc.gridx = 0;
        gc.gridy = 0;

        panouSecundar.add(inapoi, gc);
        gc.gridy ++;
        panouSecundar.add(magazin, gc);
        gc.gridy ++;
        panouSecundar.add(vanzariMari, gc);
        gc.gridy ++;
        panouSecundar.add(tari, gc);
        gc.gridy ++;
        panouSecundar.add(vanzariTari, gc);
        gc.gridy ++;
        panouSecundar.add(categorii, gc);
        gc.gridy ++;
        panouSecundar.add(vanzariCategorii, gc);
        gc.gridy ++;
        panouSecundar.add(factura, gc);
        gc.gridy ++;
        panouSecundar.add(facturaCeaMaiMare, gc);
        gc.gridy ++;

        JScrollPane scrollStatistici = new JScrollPane(panouSecundar);
        setLayout(new BorderLayout());
        add(scrollStatistici, BorderLayout.CENTER);
    }

    public void afiseazaStatistici() {
        Magazin m = g.magazinMaxim();
        vanzariMari.append(m.getNume());
        vanzariMari.append("\n");
        vanzariMari.append("Total fără taxe: ");
        vanzariMari.append(String.valueOf(df.format(m.getTotalFaraTaxe())));
        vanzariMari.append("\n");
        vanzariMari.append("Total cu taxe: ");
        vanzariMari.append(String.valueOf(df.format(m.getTotalCuTaxe())));
        vanzariMari.append("\n");
        vanzariMari.append("Total cu taxe scutite: ");
        vanzariMari.append(String.valueOf(df.format(m.getTotalCuTaxeScutite())));
        vanzariMari.append("\n");

        for (String i: g.getTari()){
            Magazin n = g.magazinMaximTara(i);

            vanzariTari.append(i);
            vanzariTari.append("\n");
            vanzariTari.append(m.getNume());
            vanzariTari.append("\n");
            vanzariTari.append("Total fără taxe: ");
            vanzariTari.append(String.valueOf(df.format(m.getTotalFaraTaxe())));
            vanzariTari.append("\n");
            vanzariTari.append("Total cu taxe: ");
            vanzariTari.append(String.valueOf(df.format(m.getTotalCuTaxe())));
            vanzariTari.append("\n");
            vanzariTari.append("Total cu taxe scutite: ");
            vanzariTari.append(String.valueOf(df.format(m.getTotalCuTaxeScutite())));
            vanzariTari.append("\n");

        }

        for (String i: g.getCategorii()){
            Magazin n = g.magazinMaximCategorie(i);

            vanzariCategorii.append(i);
            vanzariCategorii.append("\n");
            vanzariCategorii.append(m.getNume());
            vanzariCategorii.append("\n");
            vanzariCategorii.append("Total fără taxe: ");
            vanzariCategorii.append(String.valueOf(df.format(m.getTotalFaraTaxe())));
            vanzariCategorii.append("\n");
            vanzariCategorii.append("Total cu taxe: ");
            vanzariCategorii.append(String.valueOf(df.format(m.getTotalCuTaxe())));
            vanzariCategorii.append("\n");
            vanzariCategorii.append("Total cu taxe scutite: ");
            vanzariCategorii.append(String.valueOf(df.format(m.getTotalCuTaxeScutite())));
            vanzariCategorii.append("\n");

        }

        Factura f = g.facturaMaxima();
        facturaCeaMaiMare.append(f.getDenumire());
        facturaCeaMaiMare.append("\n");
        facturaCeaMaiMare.append("Total cu taxe:");
        facturaCeaMaiMare.append("\n");
        facturaCeaMaiMare.append(String.valueOf(df.format(f.getTotalCuTaxe())));
        facturaCeaMaiMare.append("\n");
        facturaCeaMaiMare.append("Total fără taxe:");
        facturaCeaMaiMare.append("\n");
        facturaCeaMaiMare.append(String.valueOf(df.format(f.getTotalFaraTaxe())));
        facturaCeaMaiMare.append("\n");
    }

    class AscultaButoane implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == inapoi) {
                cl.show(panouPrincipal, "meniu");
            }
        }
    }

}
