package com.cyphir.ie;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.naming.Context;
import java.io.IOException;
import java.sql.*;

// Klasa odpowiedzialna za połączenie z bazą danych oraz serwerem FTP
class Conn {
    private static String user = "root", pass = "", server = "127.0.0.1";
    private static int port = 21;
    static FTPClient ftpClient = null;


    static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cyphir", "root", "");
            ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            boolean success = ftpClient.login(user, pass);
            if (!success) {
                System.out.println("Could not connect to the server");
            } else {
                System.out.println("Connected to the ftp server");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }

    static int getPort() {
        return port;
    }

    static String getUser() {
        return user;
    }

    static String getPass() {
        return pass;
    }

    static String getServer() {
        return server;
    }

}