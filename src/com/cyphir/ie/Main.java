package com.cyphir.ie;

import javax.swing.*;
import java.awt.*;

// Główna klasa programu, w której tworzony jest ekran początkowy
class Main extends JPanel{

    private static JFrame jFrame;

    // Zwraca główne okno programu
    static JFrame getJFrame() {
        return jFrame;
    }

    // Tworzenie interfejsu programu
    static void createGUI() {
        // Tworzenie głównego okna programu (oraz ustawianie go na środku ekranu)
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame = new JFrame("Cyphir");
        jFrame.setUndecorated(true);
        jFrame.setSize(1280, 720);
        jFrame.getContentPane().setBackground(Color.decode("#1e1e1e"));
        jFrame.setResizable(false);
        jFrame.setLocation(dim.width/2-jFrame.getSize().width/2, dim.height/2-jFrame.getSize().height/2);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(null);

        JPanel titleBar = new titleBar();
        jFrame.add(titleBar);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        createGUI();
        getJFrame().getContentPane().add(new loginGUI());
        getJFrame().revalidate();
        getJFrame().repaint();
    }
}
