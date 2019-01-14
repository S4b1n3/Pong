package pongcours;

public class RaqIA extends Raquette{
    public RaqIA(int x, int y, int vx, int vy, int sensx, int sensy, int largeur, int hauteur) {
        super(x, y, vx, vy, sensx, sensy, largeur, hauteur);
    }

    public void deplacer(Terrain jeu){
        Balle b = jeu.getBalle();
        if(b.Sensy<0){
            this.Sensy = -1;
        }
        else {
            this.Sensy = 1;
        }
        super.deplacement(jeu);
    }
}
