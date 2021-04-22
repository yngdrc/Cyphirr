package com.cyphir.ie;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnTest {

    @Test
    public void getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cyphir", "root", "");
            String server = Conn.getServer();
            Assert.assertNotNull(server);
            int port = Conn.getPort();
            Assert.assertEquals(port, 21);
            String user = Conn.getUser();
            String pass = Conn.getPass();
            Assert.assertNotNull(user);
            Assert.assertNotNull(pass);
            FTPClient ftpClient = new FTPClient();
            Assert.assertNotNull(ftpClient);
            ftpClient.connect(server, port);
            boolean success = ftpClient.login(user, pass);
            Assert.assertTrue(success);
        } catch (Exception ignored) {
        }
        Assert.assertNotNull(c);
    }
}