package jeu_pong;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Scores extends  JFrame implements ActionListener, Variables_Jeu {
    private JButton bouton;
    private JButton bouton2;

    private int y = 200;
    private String score;

    ArrayList<String> scores = new ArrayList();

    public Scores() throws IOException{
        super();
        lectureScores();
        build();
    }

    public void lectureScores() {
        FileReader monFichier = null;
        BufferedReader tampon = null;

        try {
            monFichier = new FileReader("scores.txt");
            tampon = new BufferedReader(monFichier);

            while (true) {
                // Lit une ligne de scores.txt
                String ligne = tampon.readLine();
                // Vérifie la fin de fichier
                if (ligne == null) {
                    break;
                }
                scores.add(ligne);
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                tampon.close();
                monFichier.close();
            }
            catch(IOException exception1) {
                exception1.printStackTrace();
            }
        }
        for(String s : scores){
            score = score + "\n\n" + s;
        }
    }




    private void build() throws IOException{
        setTitle("Scores Pong"); //On donne un titre à l'application
        setSize(550, 600); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setBackground(Color.black);
        setResizable(true); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());
        setVisible(true);
    }

    private JPanel buildContentPane() throws IOException{


        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(null);

        JLabel label = new JLabel(new ImageIcon("scores.png"));
        label.setBounds(10, 10, 500, 100);
        panel.add(label);

        JTextArea area = new JTextArea();
        area.setBounds(50, 130, 450, 300);
        area.setForeground(Color.orange);
        area.setBackground(Color.black);
        area.setText(score);
        panel.add(area);


        bouton = new JButton(new ImageIcon("balle.png"));
        bouton.setBounds(50, 450, 100, 100);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setFocusPainted(false);
        panel.add(bouton);
        bouton.addActionListener(this);

        bouton2 = new JButton(new ImageIcon("balle2.png"));
        bouton2.setBounds(370, 450, 100, 100);
        bouton2.setBorderPainted(false);
        bouton2.setContentAreaFilled(false);
        bouton2.setFocusPainted(false);
        panel.add(bouton2);
        bouton2.addActionListener(this);

        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bouton){
            try {
                new Menu();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getSource()==bouton2){
            System.exit(0);
        }
    }
}
