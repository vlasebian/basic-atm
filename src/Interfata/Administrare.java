package Interfata;

import javax.print.DocFlavor;
import javax.swing.*;
import Gestiune.Gestiune;
import Gestiune.Produs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Administrare extends JPanel {
    JList<Produs> lista;
    DefaultListModel<Produs> modelLista;
    JScrollPane scroll;
    Vector<Produs> listaProduse;

    JButton ordonareAlfabetica = new JButton("Ordonare alfabetică");
    JButton ordonareTara = new JButton("Ordonare după țară");
    JButton adaugaProdus = new JButton("Adaugă produs");
    JButton stergeProdus = new JButton("Șterge produs");

    JPanel panouPrincipal;
    Incarcare panouIncarcare;
    Gestiune g;
    JButton inapoi = new JButton("Înapoi la meniu");
    CardLayout cl;

    public Administrare(JPanel p, CardLayout cl, Incarcare p2, Gestiune g) {
        panouPrincipal = p;
        this.cl = cl;
        this.g = g;
        panouIncarcare = p2;

        lista = new JList<Produs>();
        scroll = new JScrollPane(lista);

        AscultaButoane b = new AscultaButoane();
        inapoi.addActionListener(b);

        ordonareAlfabetica.addActionListener(b);
        ordonareTara.addActionListener(b);
        adaugaProdus.addActionListener(b);
        stergeProdus.addActionListener(b);

        setLayout(new GridLayout(2, 0));

        JPanel grupButoane = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;

        grupButoane.add(ordonareAlfabetica, gc);
        gc.gridy ++;
        grupButoane.add(ordonareTara, gc);
        gc.gridy ++;
        grupButoane.add(adaugaProdus, gc);
        gc.gridy ++;
        grupButoane.add(stergeProdus, gc);
        gc.gridy ++;
        grupButoane.add(inapoi, gc);

        add(scroll);
        add(grupButoane);
    }

    public void creeazaModel() {
        modelLista = new DefaultListModel<Produs>();
        for (Produs i: g.getListaProduse()) {
            modelLista.addElement(i);
        }
    }

    public void acutalizeazaModel() {
        creeazaModel();
        lista.setModel(modelLista);
    }

    public void ordoneazaAlfabetic() {
        ArrayList<Produs> v = new ArrayList<Produs>();
        v.addAll(Collections.list(modelLista.elements()));
        Collections.sort(v, new Comparator<Produs>() {
            @Override
            public int compare(Produs a, Produs b) {
                return a.getDenumire().compareTo(b.getDenumire());
            }
        });
        modelLista.clear();
        for (Produs i: v) {
            modelLista.addElement(i);
        }
    }

    public void ordoneazaDupaTara() {
        ArrayList<Produs> v = new ArrayList<Produs>();
        v.addAll(Collections.list(modelLista.elements()));
        Collections.sort(v, new Comparator<Produs>() {
            @Override
            public int compare(Produs a, Produs b) {
                return a.getTaraOrigine().compareTo(b.getTaraOrigine());
            }
        });
        modelLista.clear();
        for (Produs i: v) {
            modelLista.addElement(i);
        }
    }

    public void adaugaProdus() {
        JPanel panouInput = new JPanel();

        JLabel etichetaDenumire = new JLabel("Denumire produs: ");
        JTextField denumireProdus = new JTextField("", 10);
        panouInput.add(etichetaDenumire);
        panouInput.add(denumireProdus);

        JComboBox<String> alegeCategorie = new JComboBox<String>(g.getCategorii());
        panouInput.add(alegeCategorie);

        JLabel roLabel = new JLabel("RO:");
        JTextField roInput = new JTextField("", 10);
        JLabel itLabel = new JLabel("IT:");
        JTextField itInput = new JTextField("", 10);
        JLabel spLabel = new JLabel("SP:");
        JTextField spInput = new JTextField("", 10);

        panouInput.add(roLabel);
        panouInput.add(roInput);
        panouInput.add(itLabel);
        panouInput.add(itInput);
        panouInput.add(spLabel);
        panouInput.add(spInput);

        int result = JOptionPane.showConfirmDialog(panouPrincipal, panouInput, "Adăugare produs", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String intrareNoua = denumireProdus.getText() + " " + alegeCategorie.getSelectedItem() + " " + roInput.getText() +
                                " " + itInput.getText() + " " + spInput.getText();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(panouIncarcare.getFisierProduse(), true));
                writer.write(intrareNoua);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                System.err.println("Eroare la deschiderea fișierului produse.txt!");
            }

            Produs p1 = new Produs(denumireProdus.getText(), alegeCategorie.getSelectedItem().toString(), "RO", Double.parseDouble(roInput.getText()));
            Produs p2 = new Produs(denumireProdus.getText(), alegeCategorie.getSelectedItem().toString(), "IT", Double.parseDouble(itInput.getText()));
            Produs p3 = new Produs(denumireProdus.getText(), alegeCategorie.getSelectedItem().toString(), "SP", Double.parseDouble(spInput.getText()));

            modelLista.addElement(p1);
            modelLista.addElement(p2);
            modelLista.addElement(p3);
        }
    }

    public void stergeProdus() {
        JPanel panouInput = new JPanel();

        JLabel etichetaDenumire = new JLabel("Denumire produs: ");
        JTextField denumireProdus = new JTextField("", 10);
        panouInput.add(etichetaDenumire);
        panouInput.add(denumireProdus);

        int result = JOptionPane.showConfirmDialog(panouPrincipal, panouInput, "Adăugare produs", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String denumire = denumireProdus.getText();

            ArrayList<Produs> v = new ArrayList<Produs>();
            v.addAll(Collections.list(modelLista.elements()));
            modelLista.clear();
            Iterator<Produs> i = v.iterator();
            while (i.hasNext()) {
                if (i.next().getDenumire().equalsIgnoreCase(denumire)) {
                    i.remove();
                }
            }

            for (Produs j: v) {
                modelLista.addElement(j);
            }
        }

    }

    class AscultaButoane implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == inapoi) {
                cl.show(panouPrincipal, "meniu");
            }

            if (actionEvent.getSource() == ordonareAlfabetica) {
                ordoneazaAlfabetic();
            }

            if (actionEvent.getSource() == ordonareTara) {
                ordoneazaDupaTara();
            }

            if (actionEvent.getSource() == adaugaProdus) {
                adaugaProdus();
            }

            if (actionEvent.getSource() == stergeProdus) {
                stergeProdus();
            }
        }
    }

}
