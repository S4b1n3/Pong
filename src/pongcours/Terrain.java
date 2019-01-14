package pongcours;

import java.awt.*;

public class Terrain {
    public Balle getBalle(){
        Balle b = new Balle();
        return b;
    }
    public int getLargeurInterface(){
        return 1;
    }
    public int getHauteurInterface(){
        return 1;
    }
    public int getNumeroToucheEnfoncee(){
        return 1;
    }
    public Point getPositionSouris(){
    }

}
