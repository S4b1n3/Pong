package pongcours;

import java.awt.*;

public class Terrain {
    private int largeur;
    private int hauteur;
    private Balle b;
    private Raquette r1;
    private Raquette r2;

    public Terrain(int largeur, int hauteur, Balle b, Raquette r1, Raquette r2) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.b = b;
        this.r1 = r1;
        this.r2 = r2;
    }

    public Balle getBalle(){
        return b;
    }
    public int getLargeurInterface(){
        return largeur;
    }
    public int getHauteurInterface(){
        return hauteur;
    }
    public int getNumeroToucheEnfoncee(){
        return 1;
    }
    //public Point getPositionSouris(){ }

}
