package jeu_pong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecouteur implements ActionListener {

    private int action;
    public int joueur = 1;


    public Ecouteur (int id){
        action = id;
    }

    public void actionPerformed(ActionEvent e){
        if(action == 1){
            joueur = 1;
        }
        else if (action == 2){
            joueur = 2;
        }
    }
}
