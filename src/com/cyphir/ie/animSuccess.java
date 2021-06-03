package com.cyphir.ie;

import org.apache.commons.net.ftp.FTP;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

class animSuccess extends JLayeredPane {

    private static Timer timer;
    private static JLayeredPane jLayeredPane_top;

    // Konstruktor odpowiedzialny za tworzenie ekranu końcowego
    animSuccess() {
        setOpaque(true);
        setBackground(Color.decode("#1e1e1e"));
        setBounds(0, 50, 1280, 670);
        setPreferredSize(new Dimension(1280, 670));

        // Tworzenie obrazu 'Success'
        ImageIcon successIcon = new ImageIcon(getClass().getResource("/images/success.png"));
        JLabel jLabel_success = new JLabel(successIcon);
        jLabel_success.setBounds(533, 190, 212, 267);
        jLabel_success.setBackground(new Color(30,30,30,0));
        jLabel_success.setPreferredSize(new Dimension(212, 267));
        jLabel_success.setOpaque(true);
        add(jLabel_success);

        // Tworzenie przycisk, który umożliwia zapisanie zaszyfrowanego obrazu do pliku
        ImageIcon saveIcon = new ImageIcon(getClass().getResource("/images/save.png"));
        JLabel jLabel_save = new JLabel(saveIcon);
        jLabel_save.setBounds(800, 280, 46, 59);
        jLabel_save.setBackground(new Color(30,30,30,0));
        jLabel_save.setPreferredSize(new Dimension(46, 59));
        jLabel_save.setOpaque(true);
        jLabel_save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(jLabel_save);

        // 'Nasłuchiwanie' kliknięcia przycisku 'Save' i zapisanie do pliku
        jLabel_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    String userHomeFolder = System.getProperty("user.home") + "\\Desktop\\";
                    File outputfile = new File(userHomeFolder + imageGUI.getKey()[0] + imageGUI.getKey()[1] + imageGUI.getKey()[2] + "." + getFileExtension(new File(menuGUI.getPath())));
                    ImageIO.write(imageGUI.getImage(), getFileExtension(new File(menuGUI.getPath())), outputfile);
                    Conn.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    InputStream inputStream = new FileInputStream(outputfile);
                    boolean done = Conn.ftpClient.storeFile(loginGUI.getUsername() + '_' + imageGUI.getKey()[0] + imageGUI.getKey()[1] + imageGUI.getKey()[2] + '.' + getFileExtension(new File(menuGUI.getPath())), inputStream);
                    inputStream.close();
                    if (done) {
                        System.out.println("The file has been uploaded successfully.");
                    }
                } catch (IOException ignored) {
                    System.out.println(ignored);
                }
            }
        });

        // Tworzenie przycisku umożliwiającego powrót do ekranu startowego
        ImageIcon gobackIcon = new ImageIcon(getClass().getResource("/images/goback.png"));
        JLabel jLabel_goback = new JLabel(gobackIcon);
        jLabel_goback.setBounds(430, 270, 46, 61);
        jLabel_goback.setBackground(new Color(30,30,30,0));
        jLabel_goback.setPreferredSize(new Dimension(46, 61));
        jLabel_goback.setOpaque(true);
        jLabel_goback.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(jLabel_goback);

        jLabel_goback.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Main.getJFrame().getContentPane().remove(animSuccess.this);
                Main.getJFrame().getContentPane().add(new menuGUI());
                Main.getJFrame().revalidate();
                Main.getJFrame().repaint();
            }
        });

        jLayeredPane_top = new JLayeredPane();
        jLayeredPane_top.setOpaque(true);
        jLayeredPane_top.setBounds(0, 0, 1280, 670);
        jLayeredPane_top.setBackground(new Color(0,0,0,255));
        add(jLayeredPane_top);
        repaint();

        new SuccessAnimation();
    }

    // Animowany ekran 'Success'
    class SuccessAnimation {
        SuccessAnimation() {
            timer = new Timer(3,new ActionListener(){
                int i = 255;
                public void actionPerformed(ActionEvent ae)
                {
                    moveToFront(jLayeredPane_top);
                    jLayeredPane_top.setBackground(new Color(30,30,30, i));
                    i--;
                    if (i>0) {
                        i--;
                    } else { timer.stop(); moveToBack(jLayeredPane_top);}
                }
            });
            timer.start();
        }

    }

    // Zwracanie rozszerzenia pliku
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
