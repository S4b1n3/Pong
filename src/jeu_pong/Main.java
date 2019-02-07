package jeu_pong;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

public class Main extends JFrame implements jeu_pong.Variables_Jeu, ActionListener {

    public JMenuBar barreMenu;
    public JMenu fichier;
    public JMenu options;
    public JMenuItem nouvellePartie;
    public JMenuItem quitter;
    public JMenu niveau, vitess_Balle;
    public ButtonGroup nivJeu, nivBalle;
    public JRadioButton niv1, niv2, niv3, nivB1, nivB2, nivB3;
    public JOptionPane infoQuit, infoTerrain;

    Moteur_PingPong moteur;
    Table_PingPong table;

    /**
     * Méthode pour initialiser tous les composants graphiques de l'interface
     */
    private void initComposants(){

        barreMenu = new JMenuBar();
        fichier = new JMenu();
        options = new JMenu();
        nouvellePartie = new JMenuItem();
        quitter = new JMenuItem();
        niveau = new JMenu();
        vitess_Balle = new JMenu();
        nivJeu = new ButtonGroup();
        nivBalle = new ButtonGroup();
        niv1 = new JRadioButton();
        niv2 = new JRadioButton();
        niv3 = new JRadioButton();
        nivB1 = new JRadioButton();
        nivB2 = new JRadioButton();
        nivB3 = new JRadioButton();

        this.setTitle("Jeu Pong");

        fichier.setText("Fichier");
        fichier.setMnemonic('F');
        nouvellePartie.setText("Nouvelle Partie");
        quitter.setText("Quitter");
        options.setText("Options");
        options.setMnemonic('O');
        niveau.setText("Niveau du jeu");
        vitess_Balle.setText("Vitesse de la balle");
        niv1.setText("Petite table");
        niv2.setText("Table normale");
        niv3.setText("Grande table");
        nivB1.setText("Rapide");
        nivB2.setText("Normale");
        nivB3.setText("Lente");


        /* Raccourci clavier pour l'option "Quitter" (ctrl + n) */
        nouvellePartie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));

        /* Raccourci clavier pour l'option "Quitter" (ctrl + q) */
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));


        /* Ecouteurs d'évènements sur les options du menu */
        nouvellePartie.addActionListener(this);
        quitter.addActionListener(this);
        niv1.addActionListener(this);
        niv2.addActionListener(this);
        niv3.addActionListener(this);
        nivB1.addActionListener(this);
        nivB2.addActionListener(this);
        nivB3.addActionListener(this);

        /* Ajout de l'option "Nouvelle Partie" et "Quitter" dans l'onglet "Fichier" */
        fichier.add(nouvellePartie);
        fichier.add(quitter);

        /* Utilisation d'un objet ButtonGroup pour éviter la multi-sélection */
        nivJeu.add(niv1);
        nivJeu.add(niv2);
        nivJeu.add(niv3);

        nivBalle.add(nivB1);
        nivBalle.add(nivB2);
        nivBalle.add(nivB3);

        /* Difficulté "Normal" et vitesse de la balle "Normale" sélectionné par defaut */
        niv2.setSelected(true);
        nivB2.setSelected(true);

        /* Ajout de l'option "Niveau de jeu" dans l'onglet "Options" */
        options.add(niveau);
        options.add(vitess_Balle);

        /* Ajout des niveaux de difficulté au sous-menu "Niveau de jeu" */
        niveau.add(niv1);
        niveau.add(niv2);
        niveau.add(niv3);

        /* Ajout des niveaux de vitesse de la balle au sous-menu "Vitesse de la balle" */
        vitess_Balle.add(nivB1);
        vitess_Balle.add(nivB2);
        vitess_Balle.add(nivB3);


        /* Ajout de l'onglet "Fichier" et "Options" dans la barre de menu de l'interface */
        barreMenu.add(fichier);
        barreMenu.add(options);

        /* Ajout de la barre de menu à l'interface */
        this.setJMenuBar(barreMenu);


    }


    public Main () {


        initComposants();


        /* Evènements lors de la fermeture de l'application par la croix en haut à droite */
        this.addWindowListener(new WindowAdapter() {


            /**
             * Méthode appelée lors de la fermeture de l'application
             */
            @Override
            public void windowClosing(WindowEvent e) {

                /* Demande de confirmation à l'utilisateur pour quitter l'application */
                infoQuit = new JOptionPane();
                @SuppressWarnings("static-access")
                int choix = infoQuit.showConfirmDialog(null, "Voulez-vous vraiment quitter le jeu ?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (choix == JOptionPane.YES_OPTION) {

                    System.exit(0);
                }
                else {

                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }

        });

        table = new Table_PingPong();
        table.ajoutInterface(this.getContentPane());
        moteur = new Moteur_PingPong(table);


        this.pack();
        this.setBounds(0, 0, table.larg_Table + 90 , table.hauteur_Table + 80);

        /* Place au milieu de l'écran la fenêtre d'application */
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }


    /**
     * Méthode gérant les évènements sur les éléments graphiques
     * @param e l'élément en question
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nouvellePartie) {

            moteur.nouvellePartie();
        }


        if (e.getSource() == quitter) {

            moteur.finJeu();

        }


        /* Sélection de la vitesse de la balle */

        /* Balle rapide */
        if (e.getSource() == nivB1) {

            table.vitesse_JEU = 5;
        }

        /* Balle normale */
        if (e.getSource() == nivB2) {

            table.vitesse_JEU = 10;
        }

        /* Balle lente */
        if (e.getSource() == nivB3) {

            table.vitesse_JEU = 20;
        }


        /* Sélection de la petite table */
        if (e.getSource() == niv1) {

            /* Demande de confirmation à l'utilisateur pour quitter l'application */
            infoTerrain = new JOptionPane();
            @SuppressWarnings("static-access")
            int choix = infoTerrain.showConfirmDialog(null, "Le changement de table redémarrera une nouvelle partie. Voulez-vous vraiment changer de table ?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {

                /* Modification des dimensions de la table */
                table.larg_Table = 240;
                table.hauteur_Table = 220;
                table.bas_Table = BAS_TABLE;

                /* Modification du traçage des lignes de la table */
                table.larg_Line = 220;
                table.long_Ligne = 200;
                table.x1 = 120;
                table.x2 = 120;
                table.ligne_Mediane = 210;

                /* Placement de la raquette */
                table.place_Raquette = 220;

                /* Zone de déplacement de la balle */
                table.balle_x_max = table.larg_Table - INCR_BALLE_Y;
                table.balle_y_max = table.hauteur_Table - INCR_BALLE_Y;


                /* Redimensionnement de la fenêtre */
                this.setBounds(0, 0, table.larg_Table + 90 , table.hauteur_Table + 80);
                this.setLocationRelativeTo(null);


                /* Démarrage d'une nouvelle partie en simulant un appui sur la touche N */
                try {

                    Robot simulateur = new Robot();
                    simulateur.setAutoDelay(100);
                    simulateur.keyPress(KeyEvent.VK_N);

                }catch (AWTException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                table.repaint();
                table.revalidate();
            }

            else if (table.larg_Line == 300) {

                niv2.setSelected(true);
            }
            else {

                niv3.setSelected(true);
            }

        }

        /* Sélection de la table normale (par defaut) */
        if (e.getSource() == niv2) {

            /* Demande de confirmation à l'utilisateur pour quitter l'application */
            infoTerrain = new JOptionPane();
            @SuppressWarnings("static-access")
            int choix = infoTerrain.showConfirmDialog(null, "Le changement de table redémarrera une nouvelle partie. Voulez-vous vraiment changer de table ?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {

                /* Modification des dimensions de la table */
                table.larg_Table = LARGEUR_TABLE;
                table.hauteur_Table = HAUTEUR_TABLE;
                table.bas_Table = BAS_TABLE;

                /* Modification du traçage des lignes de la table */
                table.larg_Line = 300;
                table.long_Ligne = 200;
                table.x1 = 160;
                table.x2 = 160;
                table.ligne_Mediane = 210;

                /* Placement de la raquette */
                table.place_Raquette = 300;

                /* Zone de déplacement de la balle */
                table.balle_x_max = BALLE_X_MAX;
                table.balle_y_max = BALLE_Y_MAX;



                /* Redimension de la fenêtre */
                this.setBounds(0, 0, table.larg_Table + 90 , table.hauteur_Table + 80);
                this.setLocationRelativeTo(null);


                /* Démarrage d'une nouvelle partie en simulant un appui sur la touche N */
                try {

                    Robot simulateur = new Robot();
                    simulateur.setAutoDelay(100);
                    simulateur.keyPress(KeyEvent.VK_N);

                }catch (AWTException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                table.repaint();
                table.revalidate();

            }

            else if (table.larg_Line == 220) {

                niv1.setSelected(true);
            }
            else {

                niv3.setSelected(true);
            }

        }

        /* Sélection de la grande table */
        if (e.getSource() == niv3) {

            /* Demande de confirmation à l'utilisateur pour quitter l'application */
            infoTerrain = new JOptionPane();
            @SuppressWarnings("static-access")
            int choix = infoTerrain.showConfirmDialog(null, "Le changement de table redémarrera une nouvelle partie. Voulez-vous vraiment changer de table ?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {

                /* Modification des dimensions de la table */
                table.larg_Table = 540;
                table.hauteur_Table = 340;
                table.bas_Table = 300;

                /* Modification du traçage des lignes de la table */
                table.larg_Line = 520;
                table.long_Ligne = 320;
                table.x1 = 270;
                table.x2 = 270;
                table.ligne_Mediane = 330;

                /* Placement de la raquette */
                table.place_Raquette = 520;

                /* Zone de déplacement de la balle */
                table.balle_x_max = table.larg_Table - INCR_BALLE_Y;
                table.balle_y_max = table.hauteur_Table - INCR_BALLE_Y;


                /* Redimensionnement de la fenêtre */
                this.setBounds(0, 0, table.larg_Table + 90 , table.hauteur_Table + 80);
                this.setLocationRelativeTo(null);


                /* Démarrage d'une nouvelle partie en simulant un appui sur la touche N */
                try {

                    Robot simulateur = new Robot();
                    simulateur.setAutoDelay(100);
                    simulateur.keyPress(KeyEvent.VK_N);

                }catch (AWTException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                table.repaint();
                table.revalidate();

            }

            else if (table.larg_Line == 220) {

                niv1.setSelected(true);
            }
            else {

                niv2.setSelected(true);
            }

        }
    }


    public static void main(String[] args) {

        new Main();
    }
}
