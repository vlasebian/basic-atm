package Interfata;

import javax.smartcardio.Card;
import javax.sound.sampled.BooleanControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Gestiune.Gestiune;

public class Meniu extends JPanel {
    private JLabel banner = new JLabel("Meniu");
    private JButton incarcare = new JButton("Încărcare fișiere");
    private JButton administrare = new JButton("Administrare");
    private JButton statistici = new JButton("Statistici");
    private JButton iesire = new JButton("Ieșire");

    private JPanel panouPrincipal;
    private Administrare panouAdministrare;
    private Statistici panouStatistici;
    private CardLayout cl;
    private Gestiune g;

    public Meniu(JPanel p, Administrare a, Statistici s, CardLayout c, Gestiune g) {
        panouPrincipal = p;
        panouAdministrare = a;
        panouStatistici = s;
        cl = c;
        this.g = g;

        AscultatorButoane ascultator = new AscultatorButoane();
        incarcare.addActionListener(ascultator);
        administrare.addActionListener(ascultator);
        statistici.addActionListener(ascultator);
        iesire.addActionListener(ascultator);

        banner.setHorizontalAlignment(JLabel.CENTER);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;


        add(banner, gc);
        gc.gridy ++;
        gc.gridy ++;
        add(incarcare, gc);
        gc.gridy ++;
        add(administrare, gc);
        gc.gridy ++;
        add(statistici, gc);
        gc.gridy ++;
        add(iesire, gc);
    }

    class AscultatorButoane implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == iesire) {
                System.exit(0);
            } else if (actionEvent.getSource() == incarcare) {
                cl.show(panouPrincipal, "incarcare");
            } else if (actionEvent.getSource() == administrare) {
                if (g.getListaProduse() == null) {
                    JOptionPane.showMessageDialog(panouPrincipal, "Nu ați încărcat fișierele!", "Eroare", JOptionPane.ERROR_MESSAGE);
                } else {
                    panouAdministrare.acutalizeazaModel();
                    cl.show(panouPrincipal, "administrare");
                }
            } else if (actionEvent.getSource() == statistici) {
                if (g.getListaMagazine() == null) {
                    JOptionPane.showMessageDialog(panouPrincipal, "Nu ați încărcat fișierele!", "Eroare", JOptionPane.ERROR_MESSAGE);
                } else {
                    panouStatistici.afiseazaStatistici();
                    cl.show(panouPrincipal, "statistici");
                }
            }
        }
    }
}
