package com.cyphir.ie;

import javax.swing.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Klasa odpowiedzialna za stworzenie interfejsu logowanie i pobranie danych logowania
class loginGUI extends JLayeredPane {
    private JTextArea jTextArea_login;
    private JTextArea jTextArea_password;
    private static String username, password;

    // Zwracanie nazwy użytkownika
    static String getUsername() {
        return username;
    }

    // Metoda tworząca pola tekstowe ekranu logowania
    private JTextArea getJTextArea(String inputType, int y) {
        JTextArea jTextArea_text = new JTextArea();
        jTextArea_text.setOpaque(true);
        jTextArea_text.setWrapStyleWord(true);
        jTextArea_text.setBorder(BorderFactory.createCompoundBorder(
                jTextArea_text.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        jTextArea_text.setBackground(Color.WHITE);
        jTextArea_text.setForeground(Color.BLACK);
        jTextArea_text.setPreferredSize(new Dimension(300, 40));
        jTextArea_text.setBounds(10, y, 380, 40);
        jTextArea_text.setLineWrap(true);
        if (inputType.equals("username")) {
            jTextArea_login = jTextArea_text;
        } else jTextArea_password = jTextArea_text;
        return jTextArea_text;
    }

    // Konstruktor tworzący interfejs logowania
    loginGUI() {
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

        ImageIcon cyphirLogo = new ImageIcon(new ImageIcon(getClass().getResource("/images/cyphirLogo.png")).getImage());
        JLabel jLabel_cyphirLogo = new JLabel(cyphirLogo);
        jLabel_cyphirLogo.setLayout(new FlowLayout(FlowLayout.CENTER));
        jLabel_cyphirLogo.setOpaque(true);
        jLabel_cyphirLogo.setBounds(175, 10, 50, 50);
        jLabel_cyphirLogo.setPreferredSize(new Dimension(50, 50));
        jLabel_cyphirLogo.setBackground(new Color(30,30,30,0));

        jPanel_settings.add(jLabel_cyphirLogo);
        jPanel_settings.add(getJTextArea("username", 70));
        jPanel_settings.add(getJTextArea("password", 120));

        JLabel jLabel_button = new JLabel("Log in", JLabel.CENTER);
        jLabel_button.setBounds(10, 170, 380, 60);
        jLabel_button.setPreferredSize(new Dimension(380, 60));
        jLabel_button.setOpaque(true);
        jLabel_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel_button.setForeground(Color.WHITE);
        jLabel_button.setBackground(Color.decode("#1e1e1e"));

        jLabel_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                username = jTextArea_login.getText();
                password = jTextArea_password.getText();

                PreparedStatement ps;
                ResultSet rs;

                String query = "SELECT * FROM `usersCyphir` WHERE `username` =? AND `password` =?";

                try {
                    ps = Conn.getConnection().prepareStatement(query);

                    ps.setString(1, username);
                    ps.setString(2, password);

                    rs = ps.executeQuery();

                    if(rs.next())
                    {
                        Main.getJFrame().getContentPane().remove(loginGUI.this);
                        Main.getJFrame().getContentPane().add(new menuGUI());
                        Main.getJFrame().revalidate();
                        Main.getJFrame().repaint();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect Username Or Password", "Login Failed", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel_button.setBackground(Color.decode("#E50914"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel_button.setBackground(Color.decode("#1e1e1e"));
            }
        });

        jPanel_settings.add(jLabel_button);
    }
}
