package com.cyphir.ie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class menuGUITest {

    @Before
    public void setUp() throws Exception {
        Main.getJFrame().getContentPane().add(new menuGUI());
    }

    @Test
    public void getImage() {
        Assert.assertNotNull(menuGUI.getImage());
    }

    @Test
    public void getPath() {
        Assert.assertNotNull(menuGUI.getPath());
    }
}