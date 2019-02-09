package jeu_pong;

import sun.plugin.viewer.context.IExplorerAppletContext;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame implements ActionListener{
    private JButton bouton;
    private JButton bouton2;
    public int joueur;

    public Menu()throws IOException{
        super();
        build();
    }

    public static JPanel setBackgroundImage(JFrame frame, final File img) throws IOException {
        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1;
            private BufferedImage buf = ImageIO.read(img);
            @Override
            protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(buf, 0,0, null);
                }
            };
            frame.setContentPane(panel);
            return panel;
    }

    private void build() throws IOException {
        setTitle("Menu Pong"); //On donne un titre à l'application
        setSize(520, 400); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran

        setResizable(true); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());
        setVisible(true);
    }

    private JPanel buildContentPane() throws IOException{


        JFrame frame = new JFrame();
        JPanel panel = setBackgroundImage(frame, new File("background.jpg"));
        panel.setLayout(null);

        JLabel label = new JLabel(new ImageIcon("pong.png"));
        label.setBounds(10, 10, 500, 100);
        panel.add(label);

        bouton = new JButton(new ImageIcon("raquette1.png"));
        bouton.setBounds(50, 300, 150, 50);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setFocusPainted(false);
        panel.add(bouton);
        bouton.addActionListener(this);

        bouton2 = new JButton(new ImageIcon("raquette2.png"));
        bouton2.setBounds(300, 300, 150, 50);
        bouton2.setBorderPainted(false);
        bouton2.setContentAreaFilled(false);
        bouton2.setFocusPainted(false);
        panel.add(bouton2);
        bouton2.addActionListener(this);

        return panel;
    }



    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bouton){
            joueur = 1;
            try {
                new Main(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getSource()==bouton2){
            joueur = 2;
            try {
                new Main(true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}