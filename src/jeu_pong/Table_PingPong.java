

package jeu_pong;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Table_PingPong extends JPanel implements Variables_Jeu {
    public JLabel label;
    public int larg_Table = 320;
    public int hauteur_Table = 220;
    public int larg_Line = 300;
    public int long_Ligne = 200;
    public int x1 = 160;
    public int x2 = 160;
    public int ligne_Mediane = 210;
    public int place_Raquette = 300;
    public int bas_Table = 180;
    public int balle_x_max = 316;
    public float balle_y_max = 216;
    public int vitesse_JEU = 10;
    public int raquetteOrdi_Y = 100;
    private int raquetteJoueur_Y = 100;
    private int balle_X = 160;
    private float balle_Y = 110f;
    Dimension tailleTable = new Dimension(320, 220);


    public Table_PingPong(boolean duo) {
        if(duo == true){
            Moteur_Duo moteur2 = new Moteur_Duo(this);
            this.addMouseMotionListener(moteur2);
            this.addMouseListener(moteur2);
            this.addKeyListener(moteur2);
        }
        else {
            Moteur_PingPong moteur = new Moteur_PingPong(this);
            this.addMouseMotionListener(moteur);
            this.addKeyListener(moteur);
        }

    }

    public void ajoutInterface(Container conteneur) {
        conteneur.setLayout(new BoxLayout(conteneur, 1));
        conteneur.add(this);
        this.label = new JLabel("N : Nouvelle partie    S : servir    Q : quitter");
        conteneur.add(this.label);
    }

    public Dimension getPreferredSize() {
        return this.tailleTable;
    }

    public void paintComponent(Graphics Graphisme) {
        super.paintComponent(Graphisme);
        Graphisme.setColor(Color.blue);
        Graphisme.fillRect(0, 0, this.larg_Table, this.hauteur_Table);
        Graphisme.setColor(Color.red);
        Graphisme.fillRect(this.place_Raquette, this.raquetteJoueur_Y, 5, 30);
        Graphisme.setColor(Color.black);
        Graphisme.fillRect(15, this.raquetteOrdi_Y, 5, 30);
        Graphisme.setColor(Color.orange);
        Graphisme.fillOval(this.balle_X, (int)this.balle_Y, DIAM_BALLE, DIAM_BALLE);
        Graphisme.setColor(Color.WHITE);
        Graphisme.drawRect(10, 10, this.larg_Line, this.long_Ligne);
        Graphisme.drawLine(this.x1, 10, this.x2, this.ligne_Mediane);
        this.requestFocus();
    }

    public int positionRaquetteJoueur() {
        return this.raquetteJoueur_Y;
    }

    public void mouvementRaquetteJoueur(int deplacement) {
        this.raquetteJoueur_Y = deplacement;
        this.repaint();
    }

    public void mouvementRaquetteOrdi(int deplacement) {
        this.raquetteOrdi_Y = deplacement;
        this.repaint();
    }

    public void messagesJeu(String mess) {
        this.label.setText(mess);
        this.repaint();
    }

    public void positionBalle(int x, float y) {
        this.balle_X = x;
        this.balle_Y = y;
        this.repaint();
    }


}
