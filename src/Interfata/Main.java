package Interfata;

import Gestiune.Gestiune;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame {

    private JPanel panouPrincipal;
    private CardLayout cl;

    private Meniu panouMeniu;
    private Logare panouLogare;
    private Incarcare panouIncarcare;
    private Administrare panouAdministrare;
    private Statistici panouStatistici;

    public Main(String titlu) {
        super(titlu);

        // Exit, dimensiuni, centrare
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 400));
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // Clasa de gestionare a taxelor
        Gestiune g = Gestiune.getInstance();

        // Panoul principal al aplicatiei pe care il voi folosi pentru
        // a schimba panoul curent
        cl = new CardLayout();
        panouPrincipal = new JPanel(cl);

        // Panoul de încărcare a fișierelor
        panouIncarcare = new Incarcare(panouPrincipal, cl, g);
        panouPrincipal.add(panouIncarcare, "incarcare");

        // Panoul de administrare și afișare
        panouAdministrare = new Administrare(panouPrincipal, cl, panouIncarcare, g);
        panouPrincipal.add(panouAdministrare, "administrare");

        // Panoul de statistici
        panouStatistici = new Statistici(panouPrincipal, cl, g);
        panouPrincipal.add(panouStatistici, "statistici");

        // Panoul de meniu
        panouMeniu = new Meniu(panouPrincipal, panouAdministrare, panouStatistici, cl, g);
        panouPrincipal.add(panouMeniu, "meniu");

        // Panoul de logare
        panouLogare = new Logare(panouPrincipal, cl);
        panouPrincipal.add(panouLogare, "logare");

        add(panouPrincipal);
        panouLogare.getRootPane().setDefaultButton(panouLogare.getButonLogare());
        cl.show(panouPrincipal, "logare");
        setVisible(true);
    }

    public static void main(String[] args) {
        Main m = new Main("Sistem de facturi fiscale");
    }
}
