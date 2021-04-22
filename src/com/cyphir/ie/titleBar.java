package com.cyphir.ie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Klasa zawierająca górny pasek programu (minimalizacja, zakończenie programu, logo)
class titleBar extends JPanel {

    private int posX, posY;

    // Tworzenie paska tytułowego programu z przyciskami nawigacyjnymi
    titleBar() {
        setOpaque(true);
        setBounds(0, 0, 1280, 50);
        setPreferredSize(new Dimension(1280, 50));
        setBackground(Color.decode("#131313"));
        setLayout(null);

        // Przesuwanie całego okna myszką
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Main.getJFrame().setLocation(e.getLocationOnScreen().x-posX, e.getLocationOnScreen().y-posY);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                posX = e.getLocationOnScreen().x-Main.getJFrame().getLocationOnScreen().x;
                posY = e.getLocationOnScreen().y-Main.getJFrame().getLocationOnScreen().y;
            }
        };
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);

        // Logo aplikacji w prawym górnym rogu okna
        ImageIcon cyphirLogo = new ImageIcon(new ImageIcon(getClass().getResource("/images/cyphirLogo.png")).getImage());
        JLabel jLabel_cyphirLogo = new JLabel(cyphirLogo);
        jLabel_cyphirLogo.setLayout(new FlowLayout(FlowLayout.CENTER));
        jLabel_cyphirLogo.setOpaque(true);
        jLabel_cyphirLogo.setBounds(0, 0, 50, 50);
        jLabel_cyphirLogo.setPreferredSize(new Dimension(50, 50));
        jLabel_cyphirLogo.setBackground(new Color(30,30,30,0));

        // Tworzenie przycisku wyłączającego aplikację
        ImageIcon closeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/images/iconClose.png")).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        JLabel jLabel_close = new JLabel(closeIcon);
        jLabel_close.setLayout(new FlowLayout(FlowLayout.CENTER));
        jLabel_close.setOpaque(true);
        jLabel_close.setBounds(1230, 0, 50, 50);
        jLabel_close.setPreferredSize(new Dimension(50, 50));
        jLabel_close.setBackground(Color.decode("#131313"));
        jLabel_close.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Listenery odpowiedzialne za zmianę tła i funkcjonalność przycisku
        jLabel_close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel_close.setBackground(Color.decode("#0e0e0e"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel_close.setBackground(Color.decode("#131313"));
            }
        });

        // Przycisk do minimalizowania aplikacji
        ImageIcon minimizeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/images/iconMinimize.png")).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        JLabel jLabel_minimize = new JLabel(minimizeIcon);
        jLabel_minimize.setLayout(new FlowLayout(FlowLayout.CENTER));
        jLabel_minimize.setOpaque(true);
        jLabel_minimize.setBounds(1180, 0, 50, 50);
        jLabel_minimize.setPreferredSize(new Dimension(50, 50));
        jLabel_minimize.setBackground(Color.decode("#131313"));
        jLabel_minimize.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jLabel_minimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Main.getJFrame().setState(Frame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel_minimize.setBackground(Color.decode("#0e0e0e"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel_minimize.setBackground(Color.decode("#131313"));
            }
        });

        this.add(jLabel_cyphirLogo);
        this.add(jLabel_close);
        this.add(jLabel_minimize);
    }
}
