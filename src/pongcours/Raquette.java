package pongcours;

public class Raquette extends ElementDeJeu {
    int largeur;
    int hauteur;

    public Raquette(int x, int y, int vx, int vy, int sensx, int sensy, int largeur, int hauteur) {
        super(x, y, vx, vy, sensx, sensy);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public boolean Collision(Balle b){
        int dx = this.x - b.x;
        int dy = this.y - b.y;
        if(Math.abs(dx)<b.rayon+largeur/2 && Math.abs(dy)<b.rayon+hauteur/2){
            return true;
        }
        else {
            return false;
        }
    }
}
