package com.cyphir.ie;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Klasa odpowiedzialna za wszelkie działania na obrazie i tworzenie nowego interfejsu
class imageGUI extends JLayeredPane {

    private ArrayList<Integer> R, G, B;
    private JTextArea jTextArea_text;
    private static String[] key;
    private static BufferedImage image;
    private Color c;
    private int left_top, top, right_top, left, right, left_bottom, bottom, right_bottom, avgR, avgG, avgB, w, h, pos;

    // Zwracanie klucza do obrazu
    static String[] getKey() {
        return key;
    }

    // Zwracanie obrazu
    static BufferedImage getImage() {
        return image;
    }

    // Konstruktor tworzący ekran główny programu
    imageGUI(String type) {
        setLayout(null);
        setOpaque(true);
        setBounds(0,50,1280,670);
        setBackground(Color.decode("#1e1e1e"));

            // Tworzenie panelu z obrazem
            JPanel jPanel_img = new menuGUI.cImage();
            add(jPanel_img);

            // Tworzenie głównego panelu
            JPanel jPanel_settings = new JPanel();
            jPanel_settings.setBounds(1045, 65, 185, 540);
            jPanel_settings.setOpaque(true);
            jPanel_settings.setBackground(Color.decode("#ffffff"));
            jPanel_settings.setLayout(null);
            add(jPanel_settings);
            image = menuGUI.getImage();

        // Tworzenie interfejsu 'Encode'
        if (type.equals("Encode")) {

            // Tworzenie kontenera z kluczem potrzebnym do rozszyfrowania obrazu
            JLabel jLabel_key = new JLabel("The key will appear here", JLabel.CENTER);
            jLabel_key.setOpaque(true);
            jLabel_key.setBackground(Color.WHITE);
            jLabel_key.setForeground(Color.BLACK);
            jLabel_key.setBounds(10, 10, 165, 40);
            jLabel_key.setPreferredSize(new Dimension(165, 40));
            jPanel_settings.add(jLabel_key);

            // Kopiowanie klucza do schowka
            jLabel_key.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (!jLabel_key.getText().equals("The key will appear here")) {
                        StringSelection stringSelection = new StringSelection(jLabel_key.getText());
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                    }
                }
            });

            // Tworzenie pola tekstowego, w którym wpisywany jest tekst do zaszyfrowania
            JTextArea jTextArea_text = new JTextArea();
            jTextArea_text.setOpaque(true);
            jTextArea_text.setWrapStyleWord(true);
            jTextArea_text.setBorder(BorderFactory.createCompoundBorder(
                    jTextArea_text.getBorder(),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            jTextArea_text.setBackground(Color.WHITE);
            jTextArea_text.setForeground(Color.BLACK);
            jTextArea_text.setPreferredSize(new Dimension(165, 240));
            jTextArea_text.setBounds(10, 60, 165, 400);
            jTextArea_text.setLineWrap(true);
            jPanel_settings.add(jTextArea_text);

            // Tworzenie przycisku 'Encode'
            JLabel jLabel_button = new JLabel("Encode", JLabel.CENTER);
            jLabel_button.setBounds(10, 480, 165, 40);
            jLabel_button.setPreferredSize(new Dimension(165, 40));
            jLabel_button.setOpaque(true);
            jLabel_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jLabel_button.setForeground(Color.WHITE);
            jLabel_button.setBackground(Color.decode("#1e1e1e"));
            jPanel_settings.add(jLabel_button);

            // 'Nasłuchiwanie' przycisku 'Encode' i wykonywanie funkcji odpowiedzialnych za szyfrowanie
            jLabel_button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    // Tworzenie tablicy z wartościami RGB szyfrowanego obrazu
                    getArrayRGB(image);

                    // Zmiana wartości RGBA na podstawie wygenerowanego klucza w celu wyczyszczenia pixeli, które w przeszkadzałyby w rozszyfrowywaniu obrazu
                    clearPixels(jTextArea_text.getText(), image);

                    // Tworzenie klucza potrzebnego do rozszyfrowania obrazu
                    String rand = String.valueOf((Math.floor(Math.random() * 900000) + 100000));
                    String key1 = rand.charAt(0) + "" + rand.charAt(1);
                    String key2 = rand.charAt(1) + "" + rand.charAt(2);
                    String key3 = rand.charAt(3) + "" + rand.charAt(4);
                    key = new String[]{key1, key2, key3};

                    JLayeredPane jPanel_success_anim = new animSuccess();
                    Main.getJFrame().getContentPane().remove(imageGUI.this);
                    Main.getJFrame().getContentPane().add(jPanel_success_anim);

                    // Generowanie zmodyfikowanego, zaszyfrowanego obrazu na podstawie wygenerowanego klucza
                    generateEncoded(jTextArea_text.getText(), key, image);
                    repaint();
                    revalidate();
                }
            });

        // Tworzenie interfejsu 'Decode'
        } else if (type.equals("Decode")) {

            // Pole do którego wprowadzany będzie kod
            JTextPane jTextPane_key = new JTextPane();
            jTextPane_key.setText("Insert the key here");
            jTextPane_key.setOpaque(true);
            jTextPane_key.setBackground(Color.WHITE);
            jTextPane_key.setForeground(Color.BLACK);
            jTextPane_key.setBounds(10, 10, 165, 40);
            jTextPane_key.setPreferredSize(new Dimension(165, 40));

            StyledDocument doc = jTextPane_key.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            jTextPane_key.setBorder(BorderFactory.createCompoundBorder(
                    jTextPane_key.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            jPanel_settings.add(jTextPane_key);

            // Pole w którym wyświetlany będzie rozszyfrowany tekst
            jTextArea_text = new JTextArea();
            jTextArea_text.setOpaque(true);
            jTextArea_text.setWrapStyleWord(true);
            jTextArea_text.setBorder(BorderFactory.createCompoundBorder(
                    jTextArea_text.getBorder(),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            jTextArea_text.setBackground(Color.WHITE);
            jTextArea_text.setForeground(Color.BLACK);
            jTextArea_text.setPreferredSize(new Dimension(165, 400));
            jTextArea_text.setBounds(10, 60, 165, 400);
            jTextArea_text.setLineWrap(true);
            jPanel_settings.add(jTextArea_text);

            // Tworzenie przycisku 'Decode'
            JLabel jLabel_button = new JLabel("Decode", JLabel.CENTER);
            jLabel_button.setBounds(10, 480, 165, 40);
            jLabel_button.setPreferredSize(new Dimension(165, 40));
            jLabel_button.setOpaque(true);
            jLabel_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jLabel_button.setForeground(Color.WHITE);
            jLabel_button.setBackground(Color.decode("#1e1e1e"));
            jPanel_settings.add(jLabel_button);

            // Rozszyfrowywanie obrazu po kliknięciu przycisku 'Decode'
            jLabel_button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    getArrayRGB(image);
                    String key1 = jTextPane_key.getText().charAt(0) + "" + jTextPane_key.getText().charAt(1);
                    String key2 = jTextPane_key.getText().charAt(2) + "" + jTextPane_key.getText().charAt(3);
                    String key3 = jTextPane_key.getText().charAt(4) + "" + jTextPane_key.getText().charAt(5);
                    String[] key = new String[]{key1, key2, key3};
                    jTextArea_text.setText("");
                    decipherImage(key, image);
                }
            });
        }
    }

    // Zapisywanie wartości RGB do tablicy
    void getArrayRGB(BufferedImage image) {
        w = image.getWidth();
        h = image.getHeight();
        R = new ArrayList<>();
        G = new ArrayList<>();
        B = new ArrayList<>();
        for (int i = 0; i<w; i++) {
            for (int y = 0; y<h; y++) {
                c = new Color(image.getRGB(i,y), true);
                R.add(c.getRed());
                G.add(c.getGreen());
                B.add(c.getBlue());
            }
        }
    }

    // Zwraca średnią wartość otaczających pixeli
    private int getAVG(ArrayList<Integer> RGB, int a, int b, int c, int d, int e, int f, int g, int h) {
        return Math.round(( RGB.get(a) + RGB.get(b) + RGB.get(c) + RGB.get(d) + RGB.get(e) + RGB.get(f) + RGB.get(g) + RGB.get(h) )/9);
    }

    // Ustawia pozycję pixeli
    private void setPos(int i, int h, int y) {
        pos = i*h+y;
        left_top = pos-h-1;
        top = pos-h;
        right_top = pos-h+1;
        left = pos-1;
        right = pos+1;
        left_bottom = pos+h-1;
        bottom = pos+h;
        right_bottom = pos+h+1;
    }

    /*
    Czyszczenie obrazu z niepożądanych pixeli przed szyfrowaniem
    Metoda wylicza średnią wartość RGB dla otaczających pixeli i zmienia przezroczystość wszystkich niepożądanych pixeli z wartości 255 do 254
     */
    void clearPixels(String code, BufferedImage image) {
        for (int i = 1; i<code.length()+1; i++) {
            for (int y = 1; y<h-1; y++) {
                setPos(i, h, y);
                avgR = getAVG(R, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
                avgG = getAVG(G, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
                avgB = getAVG(B, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);

                c = new Color(image.getRGB(i,y), true);
                if (c.getRed()==avgR && c.getGreen()==avgG && c.getBlue()==avgB && c.getAlpha()==255) {
                    image.setRGB(i, y, new Color(avgR, avgG, avgB, 254).getRGB());
                    repaint();
                    revalidate();
                }
            }
        }
    }

    /*
    Generowanie zaszyfrowanego obrazu
    Metoda ustawia wartości RGBA pixela (którego pozycja jest ustawiana na podstawie wartości ASCII znaku do zaszyfrowania)
    RGB to średnia otaczających pixeli, natomiast A ustawiane jest do wartości 255
     */
    void generateEncoded(String code, String[] key, BufferedImage image) {
        int y = 0;
        int z = 0;
        int i;

        for (i = 1; i<code.length()+1; i++) {
            int toAscii = (int) code.charAt(y);
            if (z==3) {
                z=0;
            }
            setPos(i, h, toAscii+Integer.parseInt(key[z])*2);
            avgR = getAVG(R, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
            avgG = getAVG(G, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
            avgB = getAVG(B, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
            image.setRGB(i, toAscii+Integer.parseInt(key[z])*2, new Color(avgR, avgG, avgB, 255).getRGB());

            repaint();
            revalidate();
            z++;
            y++;
        }
        if (z==3) {
            z=0;
        }
        setPos(i, h, Integer.parseInt(key[z])*2);
        avgR = getAVG(R, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
        avgG = getAVG(G, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
        avgB = getAVG(B, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
        image.setRGB(i, Integer.parseInt(key[z])*2, new Color(avgR, avgG, avgB, 253).getRGB());
    }

    // Metoda rozszyfrowująca obraz
    void decipherImage(String[] key, BufferedImage image) {
        int z = 0;
        for (int i = 1; i<w-1; i++) {
            if (z==3) {
                z=0;
            }
            for (int y = 1; y<h-1; y++) {
                setPos(i, h, y);
                avgR = getAVG(R, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
                avgG = getAVG(G, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
                avgB = getAVG(B, left_top, top, right_top, left, right, left_bottom, bottom, right_bottom);
                c = new Color(image.getRGB(i,y), true);
                if (c.getRed()==avgR && c.getGreen()==avgG && c.getBlue()==avgB && c.getAlpha()==255) {
                    int asciiCode = (y-(Integer.parseInt(key[z])*2));
                    jTextArea_text.setText(jTextArea_text.getText()+Character.toString((char)asciiCode));
                    y=h-1;
                } else if (c.getRed()==avgR && c.getGreen()==avgG && c.getBlue()==avgB && c.getAlpha()==253) {
                    y=h-1;
                    i=w-1;
                }
            }
            z++;
        }
    }
}
