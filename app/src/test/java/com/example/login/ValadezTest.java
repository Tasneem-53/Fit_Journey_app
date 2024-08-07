package com.example.login;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.daclink.fitjourney.Database.entities.User;
public class ValadezTest {
    User testUser1 = null;
    String testName1 = "testName1";
    String testPassword1 ="testPassword1";
    User testUser2 = null;
    String testName2 = "testName2";
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
    public void TestingUseName(){
         assertEquals(testName1, testUser1.getUsername());
         assertNotEquals(testPassword2,testUser1.getUsername());
         //assertEquals(isAdmin,testUser.getAdmin());
    }

}
