package com.cyphir.ie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class imageGUITest {

    imageGUI imageGUI;

    @Before
    public void setUp() throws Exception {
        Main.createGUI();
        Assert.assertNotNull(Main.getJFrame());
        imageGUI = new imageGUI("Encode");
        Main.getJFrame().getContentPane().add(imageGUI);
    }

    @Test
    public void getKey() {
        Assert.assertNotNull(imageGUI.getKey());
    }

    @Test
    public void getImage() {
        Assert.assertNotNull(imageGUI.getImage());
    }

    @Test
    public void getArrayRGB() throws IOException {
        BufferedImage image = ImageIO.read(new File("/images/testImage.png"));
        Assert.assertNotNull(image);
        Color c;
        int w = image.getWidth();
        int h = image.getHeight();
        Assert.assertNotNull(w);
        Assert.assertNotNull(h);
        ArrayList<Integer> R = new ArrayList<>();
        ArrayList<Integer> G = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();
        for (int i = 0; i<w; i++) {
            Assert.assertNotSame(i, w);
            for (int y = 0; y<h; y++) {
                Assert.assertNotSame(y, h);
                c = new Color(image.getRGB(i,y), true);
                Assert.assertNotNull(c);
            }
        }
    }

    @Test
    public void clearPixels() throws IOException {
        BufferedImage image = ImageIO.read(new File("/images/testImage.png"));
        Assert.assertNotNull(image);
        int w = image.getWidth();
        int h = image.getHeight();
        Assert.assertNotNull(w);
        Assert.assertNotNull(h);
        String code = "123456";
        Assert.assertEquals(6, code.length());
        for (int i = 1; i<code.length()+1; i++) {
            Assert.assertNotSame(i, code.length()+1);
            for (int y = 1; y<h-1; y++) {
                Assert.assertNotSame(y, h-1);
            }
        }
    }

    @Test
    public void generateEncoded() throws IOException {
        BufferedImage image = ImageIO.read(new File("/images/testImage.png"));
        Assert.assertNotNull(image);
        int y = 0;
        int z = 0;
        int i;
        String code = "123456";
        String[] key = {"12", "34", "56"};
        Assert.assertEquals(key.length*2, code.length());
        Assert.assertEquals(6, code.length());
        for (i = 1; i<code.length()+1; i++) {
            Assert.assertNotSame(i, code.length()+1);
            int toAscii = (int) code.charAt(y);
            if (z==3) {
                z=0;
            }
            image.setRGB(i, toAscii+Integer.parseInt(key[z])*2, new Color(1, 1, 1, 255).getRGB());
            Assert.assertNotEquals(z, 4);
            z++;
            y++;
        }
        if (z==3) {
            z=0;
        }
        Assert.assertEquals(z, 0);
        image.setRGB(i, Integer.parseInt(key[z])*2, new Color(1, 1, 1, 253).getRGB());
    }

    @Test
    public void decipherImage() throws IOException {
        Color c;
        BufferedImage image = ImageIO.read(new File("/images/testImage.png"));
        Assert.assertNotNull(image);
        int w = image.getWidth();
        int h = image.getHeight();
        Assert.assertNotNull(w);
        Assert.assertNotNull(h);
        int z = 0;
        for (int i = 1; i<w-1; i++) {
            if (z==3) {
                z=0;
            }
            Assert.assertNotEquals(z, 4);
            for (int y = 1; y<h-1; y++) {
                c = new Color(image.getRGB(i,y), true);
                Assert.assertNotNull(c);
            }
            z++;
        }
    }
}