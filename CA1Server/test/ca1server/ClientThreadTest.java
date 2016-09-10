/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca1server;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class ClientThreadTest {

    public ClientThreadTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class ClientThread.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        ClientThread instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsername method, of class ClientThread.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        ClientThread instance = null;
        String expResult = "";
        String result = instance.getUsername();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUsername method, of class ClientThread.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "testName";
        ClientThread instance = null;
        instance.setUsername(username);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals("testName", instance.getUsername());
    }

    /**
     * Test of update method, of class ClientThread.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Observable o = null;
        Object arg = null;
        ClientThread instance = null;
        instance.update(o, arg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
