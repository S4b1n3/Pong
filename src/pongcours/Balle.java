package pongcours;

public class Balle extends ElementDeJeu{
    int rayon;

    public Balle(int x, int y, int vx, int vy, int Sensx, int Sensy, int rayon){
        super(x, y, vx, vy, Sensx, Sensy);
        this.rayon=rayon;
    }

}
