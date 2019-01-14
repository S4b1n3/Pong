package pongcours;

public class RaqClavier extends Raquette{
    public RaqClavier(int x, int y, int vx, int vy, int sensx, int sensy, int largeur, int hauteur) {
        super(x, y, vx, vy, sensx, sensy, largeur, hauteur);
    }

    public void deplacement(Terrain jeu){
        if(jeu.getNumeroToucheEnfoncee() == 38){
            this.Sensy = -1;
        }
        else if(jeu.getNumeroToucheEnfoncee()== 40){
            this.Sensy = 1;
        }
        else {
            this.Sensy = 0;
        }
        super.deplacement(jeu);
    }
}
