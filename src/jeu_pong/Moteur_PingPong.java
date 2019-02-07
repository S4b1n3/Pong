package jeu_pong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import static java.lang.Math.abs;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static jeu_pong.Variables_Jeu.BALLE_Y_MIN;

/**
 * Cette classe représente le moteur du jeu, toutes les actions et mouvements y sont gérés
 * (balle, raquette, gestion souris...)
 */
public class Moteur_PingPong implements Variables_Jeu, MouseMotionListener, Runnable, KeyListener{



    Table_PingPong table;

    public int raquetteJoueur_Y = RAQUETTE_Y;
    private int raquetteOrdi_Y = RAQUETTE_ORDI_Y;


    public int score_Joueur = 0;
    public int score_Ordi = 0;

    private int balle_X;
    private int balle_Y;
    private int sens_X;
    private int sens_Y;

    private volatile boolean balle_Service = false;
    private boolean deplacement_Gauche = true;

    private JOptionPane infoQuit;
    private JOptionPane rejouer;



    /**
     * Constructeur de la classe
     * @param tablePingPong référence de la table
     */
    public Moteur_PingPong(Table_PingPong tablePingPong) {

        table = tablePingPong;

        /* Création d'un thread qui va assurer le déroulement du jeu (trajectoire balle, ordi...) */
        Thread jeu = new Thread(this);
        jeu.start();
    }


    /**
     * Méthode appelée lorsqu'un clic de souris à lieu sur l'interface de jeu
     * @param e l'évènement
     */
    public void mousePressed(MouseEvent e) {

        /* Récupération de la position du pointeur de la souris
           pour y placer un rectangle */
        //table.point.x = e.getX();
        //table.point.y = e.getY();

        /* actualisation de l'interface */
        //table.repaint();


    }

    /* Ensemble de méthodes implémentées par l'interface MouseListener */
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}




    /* Ensemble de méthodes implémentées par l'interface MouseMotionListener */
    public void mouseDragged(MouseEvent e) {}


    /**
     * Méthode appelée lorsque le curseur de la souris se trouve sur l'interface de jeu
     * @param e l'évènement
     */
    public void mouseMoved(MouseEvent e) {

        /* Récupération de la position du pointeur de la souris */
        int posSourisY = e.getY();

        /* Si le pointeur est au dessus de la raquette du joueur et
           que celle-ci n'a pas dépassé la limite de la table */
        if (posSourisY < raquetteJoueur_Y && raquetteJoueur_Y > HAUT_TABLE) {

            /* Déplacement de la raquette vers le haut de la table */
            raquetteJoueur_Y = raquetteJoueur_Y - INCR_RAQUETTE;
        }
        /* Sinon déplacement de la raquette vers le bas de la table */
        else if (raquetteJoueur_Y < table.bas_Table) {

            raquetteJoueur_Y = raquetteJoueur_Y + INCR_RAQUETTE;
        }

        /* Transmisson de la nouvelle position de la raquette à la table
           (à la classe Table_PingPong plus précisement) */
        table.mouvementRaquetteJoueur(raquetteJoueur_Y);

        /* actualisation de l'interface */
        //table.repaint();
    }


    /**
     * Instructions réalisées par le thread créé
     */
    public void run() {

        boolean rebondBalleX = false;
        boolean rebondBalleY = false;

        int angle = 0;



        while (true) {

            /* Si la balle est en jeu (en mouvement) */
            if (balle_Service) {

                rebondBalleY = (balle_Y < 10 || balle_Y > table.hauteur_Table-10-DIAM_BALLE ? true : false);

                // Si celle-ci se déplace vers la gauche
                if (deplacement_Gauche && balle_X > BALLE_X_MIN) {

                    rebondBalleX = (balle_Y >= raquetteOrdi_Y && balle_Y < (raquetteOrdi_Y + LONGUEUR_RAQUETTE)
                            ? true : false);
                    sens_X = -1;
                    if(rebondBalleY){
                        sens_Y = sens_Y * -1;
                    }
                    balle_X = balle_X + INCR_BALLE_X*sens_X;
                    balle_Y = balle_Y + INCR_BALLE_Y*sens_Y;
                    table.positionBalle(balle_X, balle_Y);

                    // Si la balle rebondi
                    if (balle_X <= RAQUETTE_ORDI_X && rebondBalleX) {


                        Thread playWave=new AePlayWave("pong.wav");
                        playWave.start();


                        deplacement_Gauche = false;
                        angle = balle_Y - (raquetteOrdi_Y + LONGUEUR_RAQUETTE/2);
                        if (balle_Y > raquetteOrdi_Y + LONGUEUR_RAQUETTE / 2) {

                            sens_Y = 1+abs(angle/5);
                        }
                        else {

                            sens_Y = -1-abs(angle/5);
                        }

                    }
                }



                // Si celle-ci se déplace vers la droite
                if (!deplacement_Gauche && balle_X <= table.balle_x_max) {

                    rebondBalleX = (balle_Y >= raquetteJoueur_Y && balle_Y < (raquetteJoueur_Y + LONGUEUR_RAQUETTE)
                            ? true : false);
                    sens_X = 1;
                    if(rebondBalleY){
                        sens_Y = sens_Y * -1;
                    }

                    // Mise à jour de la position de la balle sur la table
                    balle_X = balle_X + INCR_BALLE_X*sens_X;
                    balle_Y = balle_Y + INCR_BALLE_Y*sens_Y;
                    table.positionBalle(balle_X, balle_Y);

                    // Si la balle rebondi
                    if (balle_X >= table.place_Raquette && rebondBalleX) {



                        Thread playWave=new AePlayWave("pong.wav");
                        playWave.start();

                        deplacement_Gauche = true;
                        angle = balle_Y - (raquetteJoueur_Y + LONGUEUR_RAQUETTE/2);
                        if (balle_Y > raquetteJoueur_Y + LONGUEUR_RAQUETTE / 2) {

                            sens_Y = 1+abs(angle/5);
                        }
                        else {

                            sens_Y = -1-abs(angle/5);
                        }

                    }
                }




                /* Déplacement de la raquette de l'ordinateur pour taper la balle */
                if (raquetteOrdi_Y < balle_Y && raquetteOrdi_Y < table.bas_Table) {

                    /* Déplacement vers le haut */
                    raquetteOrdi_Y = raquetteOrdi_Y + INCR_RAQUETTE;
                }
                else if (raquetteOrdi_Y > HAUT_TABLE) {

                    /* Déplacement vers le bas */
                    raquetteOrdi_Y = raquetteOrdi_Y - INCR_RAQUETTE;
                }

                /* Mise à jour de la position de la raquette de l'ordinateur */
                table.mouvementRaquetteOrdi(raquetteOrdi_Y);




                /* mise à jour du score lors d'un service */
                if (balleEnJeu()) {

                    if (balle_X > table.balle_x_max) {

                        score_Ordi++;
                        affichageScore();
                    }
                    else if (balle_X < BALLE_X_MIN) {

                        score_Joueur++;
                        affichageScore();
                    }
                }
            }

            /* ralentissement du thread en cas d'ordinateur puissant où s'exécute le jeu */
            try {

                Thread.sleep(table.vitesse_JEU);
            } catch (InterruptedException exception) {

                exception.printStackTrace();
            }
        }
    }


    /* ensemble de méthodes implémentées par l'interface KeyListener */
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}


    /**
     * Méthode appelée pour la gestion des évènements clavier
     * @param e l'évènement
     */
    public void keyPressed(KeyEvent e) {

        /* récupération de la touche tapée du clavier */
        char touche = e.getKeyChar();

        /* traitement des évènements en fonction de la touche clavier tapée */
        if ('n' == touche || 'N' == touche) {

            nouvellePartie();
        }
        else if ('q' == touche || 'Q' == touche) {

            finJeu();
        }
        else if ('s' == touche || 'S' == touche) {

            serviceJeu();
        }

        int fleche = e.getKeyCode();
        if (fleche == KeyEvent.VK_UP){
            raquetteJoueur_Y -= INCR_RAQUETTE;
        } else if (fleche == KeyEvent.VK_DOWN){
            raquetteJoueur_Y += INCR_RAQUETTE;
        }
        table.mouvementRaquetteJoueur(
                raquetteJoueur_Y);

    }


    /**
     * Méthode pour démarrer une nouvelle partie
     */
    public void nouvellePartie() {

        score_Joueur = 0;
        score_Ordi = 0;

        table.messagesJeu("Scores - Ordinateur : 0  " + " Joueur : 0");
        serviceJeu();

    }


    /**
     * Méthode pour quitter le jeu à partir de la touche 'q' du clavier
     */
    public void finJeu() {

        /* Demande de confirmation à l'utilisateur pour quitter l'application */
        infoQuit = new JOptionPane();
        @SuppressWarnings("static-access")
        int choix = infoQuit.showConfirmDialog(null, "Voulez-vous vraiment quitter l'application ?", "Confirmation", JOptionPane.YES_NO_OPTION);


        if (choix == JOptionPane.YES_OPTION) {

            System.exit(0);
        }
    }

    public void rejouer(){
        //Demande à l'utilisateur si il veut commencer une nouvelle partie
        rejouer = new JOptionPane();
        @SuppressWarnings("static-access")
        int choix = rejouer.showConfirmDialog(null, "Voulez vous rejouer ?", "Rejouer", JOptionPane.YES_NO_OPTION);
        if(choix == JOptionPane.YES_OPTION){
            nouvellePartie();
        }
        else if (choix == JOptionPane.NO_OPTION){
            System.exit(0);
        }
    }

    /**
     * Méthode permettant au joueur de servir en 1 er dans le jeu
     */
    private void serviceJeu() {

        balle_Service = true;
        balle_X = table.place_Raquette - 1;
        balle_Y = raquetteJoueur_Y;
        Thread playWave=new AePlayWave("pong.wav");
        playWave.start();

        if (balle_Y > table.hauteur_Table / 2) {

            sens_Y = -1;
        }
        else {

            sens_Y = 1;
        }
        table.positionBalle(balle_X, balle_Y);
        table.mouvementRaquetteJoueur(raquetteJoueur_Y);
    }


    /**
     * Méthode gérant l'affichage du score du jeu
     */
    private void affichageScore () {

        balle_Service = false;

        /* si l'ordinateur atteint le score de 11 points */
        if (score_Ordi == SCORE_GAGNANT) {

            table.messagesJeu("Vous avez perdu " + score_Ordi + ":" + score_Joueur + " !");
            rejouer();
        }
        /* si c'est le joueur qui atteint le score de 11 points */
        else if (score_Joueur == SCORE_GAGNANT) {

            table.messagesJeu("Vous avez gagné " + score_Joueur + ":" + score_Ordi+" !");
            rejouer();
        }
        /* sinon affichage classique des scores */
        else {

            table.messagesJeu("Ordinateur : " + score_Ordi + " Joueur : " + score_Joueur);

        }
    }


    /**
     * Méthode pour indiquer si la balle est sortie de la table ou non
     * @return true : balle sortie,  false : balle en jeu
     */
    private boolean balleEnJeu () {

        if (balle_Y >= BALLE_Y_MIN && balle_Y <= table.balle_y_max) {

            return true;
        }
        else {

            return false;
        }
    }



}
