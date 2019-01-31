package pongcours;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fenetre extends JFrame implements ActionListener {
    JButton b1, b2;
    JTextField t;
    JLabel l;
    public Fenetre(){
        setSize(800, 600);
        setTitle("Test");
        JPanel p =new JPanel();
        Container contenu = this.getContentPane();
        contenu.add(p);

        Dimension dim = new Dimension(400, 40);

        l = new JLabel("Nom");
        l.setPreferredSize(dim);
        p.add(l);

        t = new JTextField();
        p.add(t);


        b1 = new JButton("Valider");
        p.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Annuler");
        p.add(b2);
        b2.addActionListener(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            System.out.println("Appui sur Valider");
        }
        else if(e.getSource()==b2){
            System.out.println("Appui sur annuler");
        }
    }
}