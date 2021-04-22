package com.cyphir.ie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Klasa odpowiedzialna za tworzenie głównego menu programu
class menuGUI extends JLayeredPane {

    private static Font font = new Font("Arial", Font.PLAIN, 20);
    private static BufferedImage image;
    private static JLabel jLabel_encode, jLabel_decipher;
    private static String path = null;

    // Zwracanie obrazu
    static BufferedImage getImage() {
        return image;
    }

    // Zwracanie ścieżki do pliku
    static String getPath() {
        return path;
    }

    // Klasa odpowiedzialna za 'rysowanie' plików graficznych w oknie
    static class cImage extends JPanel {

        cImage() {
            setBounds(50,65,960,540);
            setOpaque(true);
            setBackground(Color.WHITE);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(resize(getImage(), 960, 540), 0, 0, this);
        }

        private static BufferedImage resize(BufferedImage img, int width, int height) {
            Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            return resized;
        }
    }

    // Metoda służąca do wybrania obrazu który ma zostać zaszyfrowany/odszyfrowany
    private static void getFile() {
        FileDialog dialog = new FileDialog((Frame) null, "Select a file to open");
        dialog.setFile("*.jpg;*.jpeg;*.png;"); // dialog.setFile("*.jpg;*.jpeg;*.png"); przez kompresję formatów innych niż .png szyfrowanie nie zawsze jest skuteczne
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        path = dialog.getDirectory() + dialog.getFile();
        if (dialog.getDirectory() != null && dialog.getFile() != null) {
            String file = path;
            try {
                image = ImageIO.read(new File(file));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    // Konstruktor tworzący menu główne
    menuGUI() {
        setLayout(null);
        setOpaque(true);
        setBounds(0,50,1280,670);
        setBackground(Color.decode("#1e1e1e"));

        JPanel jPanel_settings = new JPanel();
        jPanel_settings.setBounds(440, 185, 400, 300);
        jPanel_settings.setOpaque(true);
        jPanel_settings.setBackground(Color.decode("#121212"));
        jPanel_settings.setLayout(null);
        add(jPanel_settings);

        ImageIcon icon = new ImageIcon(Main.class.getResource("/images/encode.png"));
        jLabel_encode = new JLabel("Encode", icon, JLabel.CENTER);
        jLabel_encode.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel_encode.setHorizontalTextPosition(JLabel.CENTER);
        jLabel_encode.setVerticalTextPosition(JLabel.BOTTOM);
        jLabel_encode.setFont(font);
        jLabel_encode.setIconTextGap(25);
        jLabel_encode.setLayout(new FlowLayout(FlowLayout.CENTER));
        jLabel_encode.setBounds(0, 50, 640, 670);
        jLabel_encode.setPreferredSize(new Dimension(640, 670));
        jLabel_encode.setOpaque(true);
        jLabel_encode.setBackground(Color.decode("#1e1e1e"));
        jLabel_encode.setForeground(Color.WHITE);

        ImageIcon icon2 = new ImageIcon(Main.class.getResource("/images/decode.png"));
        jLabel_decipher = new JLabel("Decode", icon2, JLabel.CENTER);
        jLabel_decipher.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel_decipher.setHorizontalTextPosition(JLabel.CENTER);
        jLabel_decipher.setVerticalTextPosition(JLabel.BOTTOM);
        jLabel_decipher.setFont(font);
        jLabel_decipher.setIconTextGap(25);
        jLabel_decipher.setLayout(new FlowLayout(FlowLayout.CENTER));
        jLabel_decipher.setBounds(640, 50, 640, 670);
        jLabel_decipher.setPreferredSize(new Dimension(640, 670));
        jLabel_decipher.setOpaque(true);
        jLabel_decipher.setBackground(Color.decode("#1e1e1e"));
        jLabel_decipher.setForeground(Color.WHITE);

        Main.getJFrame().getContentPane().add(jLabel_encode);
        Main.getJFrame().getContentPane().add(jLabel_decipher);

        jLabel_encode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getFile();

                // Zmiana zawartości okna jeśli wybrano plik
                if (path!=null) {
                    Main.getJFrame().getContentPane().remove(menuGUI.this);
                    Main.getJFrame().getContentPane().remove(jPanel_settings);
                    Main.getJFrame().getContentPane().remove(jLabel_decipher);
                    Main.getJFrame().getContentPane().remove(jLabel_encode);
                    Main.getJFrame().getContentPane().add(new imageGUI("Encode"));
                    Main.getJFrame().revalidate();
                    Main.getJFrame().repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel_encode.setBackground(Color.decode("#121212"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel_encode.setBackground(Color.decode("#1e1e1e"));
            }
        });
        jLabel_decipher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getFile();

                // Zmiana zawartości okna jeśli wybrano plik
                if (path!=null) {
                    Main.getJFrame().getContentPane().remove(menuGUI.this);
                    Main.getJFrame().getContentPane().remove(jPanel_settings);
                    Main.getJFrame().getContentPane().remove(jLabel_decipher);
                    Main.getJFrame().getContentPane().remove(jLabel_encode);
                    Main.getJFrame().getContentPane().add(new imageGUI("Decode"));
                    Main.getJFrame().revalidate();
                    Main.getJFrame().repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel_decipher.setBackground(Color.decode("#121212"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel_decipher.setBackground(Color.decode("#1e1e1e"));
            }
        });
    }
}
