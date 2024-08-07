package com.example.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.daclink.fitjourney.Database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KhokhaTest {

    User testUser1 = null;
    String testName1 = "testUser1";
    String testPassword1 ="testPassword1";
    User testUser2 = null;
    String testName2 = "testUser2";
    String testPassword2 ="testPassword2";
    boolean isAdmin = true;

    @Before
    public void setup(){
        testUser1= new User(testName1, testPassword1);
        testUser1.setAdmin(isAdmin);
        testUser2 = new User(testName2, testPassword2);
    }

    @After
    public void tearDown(){
        testUser1 = null;
        testUser2 = null;
    }

    @Test
    public void TestingUserId(){
        int id = 7;
        testUser1.setId(id);
        assertEquals(id, testUser1.getId());

        id = 10;
        assertNotEquals(id, testUser1.getId());

        id = 19;
        testUser2.setId(id);
        assertEquals(id, testUser2.getId());

        id = 21;
        assertNotEquals(id, testUser2.getId());

    }

    @Test
    public void TestingIsAdmin(){
        assertEquals(isAdmin, testUser1.getAdmin());
        assertNotEquals(isAdmin, testUser2.getAdmin());
    }

}
