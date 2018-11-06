package Interfata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import Gestiune.Gestiune;

public class Incarcare extends JPanel {

    private JLabel etichetaProduse = new JLabel("Fișier produse:");
    private JButton incarcaProduse = new JButton("Încarcă produse");

    private JLabel etichetaTaxe = new JLabel("Fișier taxe:");
    private JButton incarcaTaxe = new JButton("Încarcă taxe");

    private JLabel etichetaFacturi = new JLabel("Fișier facturi:");
    private JButton incarcaFacturi = new JButton("Încarcă facturi");

    private File fisierProduse;
    private File fisierTaxe;
    private File fisierFacturi;

    private JButton creeazaOutput = new JButton("Creează fișierul out.txt");
    private JButton inapoi = new JButton("Înapoi la meniu");

    public Incarcare(JPanel panouPrincipal, CardLayout cl,  Gestiune g) {
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == inapoi) {
                    cl.show(panouPrincipal, "meniu");
                }
            }
        });

        AlegeFisiere ascultator = new AlegeFisiere();

        incarcaProduse.addActionListener(ascultator);
        incarcaTaxe.addActionListener(ascultator);
        incarcaFacturi.addActionListener(ascultator);

        creeazaOutput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fisierProduse != null && fisierTaxe != null && fisierFacturi != null) {
                    g.readFiles(fisierProduse, fisierTaxe, fisierFacturi);
                    g.writeOutputToFile();
                }
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;

        add(etichetaProduse, gc);
        gc.gridy ++;
        add(etichetaTaxe, gc);
        gc.gridy ++;
        add(etichetaFacturi, gc);
        gc.gridy ++;
        add(creeazaOutput, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        add(incarcaProduse, gc);
        gc.gridy ++;
        add(incarcaTaxe, gc);
        gc.gridy ++;
        add(incarcaFacturi, gc);
        gc.gridy ++;
        add(inapoi, gc);

    }

    class AlegeFisiere implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser alegeFisier = new JFileChooser();
            JButton open = new JButton();
            alegeFisier.setCurrentDirectory(new File("./in"));
            alegeFisier.setDialogTitle("Alege fișier");
            alegeFisier.setFileSelectionMode(JFileChooser.FILES_ONLY);


            if (alegeFisier.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
                if (actionEvent.getSource() == incarcaProduse) {
                    fisierProduse = alegeFisier.getSelectedFile();
                } else if (actionEvent.getSource() == incarcaTaxe) {
                    fisierTaxe = alegeFisier.getSelectedFile();
                } else if (actionEvent.getSource() == incarcaFacturi) {
                    fisierFacturi = alegeFisier.getSelectedFile();
                }
            }
        }
    }

    public File getFisierProduse() {
        return fisierProduse;
    }
}
