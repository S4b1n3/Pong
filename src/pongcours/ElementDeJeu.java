package pongcours;

public class ElementDeJeu {
    int x;
    int y;
    int vx;
    int vy;
    int Sensx;
    int Sensy;

    public void deplacement(Terrain jeu){
        x = x+vx*Sensx;
        y = y+vy*Sensy;
    }

    public ElementDeJeu(int x, int y, int vx, int vy, int sensx, int sensy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        Sensx = sensx;
        Sensy = sensy;
    }
}
