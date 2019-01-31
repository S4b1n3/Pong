package pongcours;

import java.awt.*;

public class RaqSouris extends Raquette{
    public RaqSouris(int x, int y, int vx, int vy, int sensx, int sensy, int largeur, int hauteur) {
        super(x, y, vx, vy, sensx, sensy, largeur, hauteur);
    }

    /*
    public void deplacement(Terrain jeu){
        Point p = jeu.getPositionSouris();
        if(p.y > this.y){
            this.Sensy = 1;
        }
        else if (p.y < this.y){
            this.Sensy = -1;
        }
        else {
            this.Sensy = 0;
        }
        super.deplacement(jeu);
    }
    */
}
