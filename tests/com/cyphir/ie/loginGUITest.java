package com.cyphir.ie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class loginGUITest {

    @Before
    public void setUp() throws Exception {
        Main.createGUI();
        Main.getJFrame().getContentPane().add(new loginGUI());
    }

    @Test
    public void getUsername() {
        Assert.assertNotNull(loginGUI.getUsername());
    }
}