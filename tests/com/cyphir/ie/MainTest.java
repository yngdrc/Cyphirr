package com.cyphir.ie;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Before
    public void setUp() throws Exception {
        Main.createGUI();
    }

    @Test
    public void getJFrame() {
        Assert.assertNotNull(Main.getJFrame());
    }

}