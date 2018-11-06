package Interfata;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logare extends JPanel {

    JLabel etichetaUtilizator = new JLabel("Utilizator:", JLabel.CENTER);
    JLabel etichetaParola = new JLabel("Parola:", JLabel.CENTER);

    JTextField campUtilizator = new JTextField("", 10);
    JTextField campParola = new JTextField("", 10);

    JButton butonLogare = new JButton("Logare");
    JLabel status = new JLabel();

    JPanel panouPrincipal;
    CardLayout cl;

    public Logare(JPanel p, CardLayout cl) {
        super();
        panouPrincipal = p;
        this.cl = cl;
        butonLogare.addActionListener(new ButonLogare(campUtilizator, campParola, status));

        JPanel panouButon = new JPanel(new FlowLayout());
        panouButon.add(butonLogare, JPanel.BOTTOM_ALIGNMENT);

        setLayout(new GridLayout(4,1));

        JPanel grupeazaCampuri = new JPanel();
        grupeazaCampuri.setLayout(new GridLayout(2, 2, 2, 2));
        grupeazaCampuri.setBorder(BorderFactory.createTitledBorder(getBorder(), "Logare",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Default",Font.PLAIN,10), Color.gray));

        JLabel mesaj = new JLabel("Bine ați venit!", JLabel.CENTER);
        grupeazaCampuri.add(etichetaUtilizator);
        grupeazaCampuri.add(campUtilizator);
        grupeazaCampuri.add(etichetaParola);
        grupeazaCampuri.add(campParola);


        add(mesaj);
        add(grupeazaCampuri);
        add(panouButon);
        add(status);

    }

    public JButton getButonLogare() {
        return butonLogare;
    }

    class ButonLogare implements ActionListener {
        JTextField utilizator;
        JTextField parola;
        JLabel status;

        public ButonLogare(JTextField utilizator, JTextField parola, JLabel status) {
            this.utilizator = utilizator;
            this.parola = parola;
            this.status = status;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (utilizator.getText().equals("admin") && parola.getText().equals("admin")) {
                cl.show(panouPrincipal, "meniu");
            }
            else {
                status.setText("Utilizator sau parolă greșită!");
                status.setHorizontalAlignment(JLabel.CENTER);
            }
        }
    }


}
