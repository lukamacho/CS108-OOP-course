package servlets;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AccountManagerTest {
    private AccountManager accountManager;


    @Test
    private void basicAccountExistenceTest(){
        accountManager = new AccountManager();
        assertEquals(false,accountManager.exists("Luka"));
        assertEquals(true,accountManager.exists("Molly"));
        assertEquals(false,accountManager.exists("bigenti"));
        assertEquals(true,accountManager.exists("Patrick"));
    }
    @Test
    private void checkUserTest(){
        accountManager = new AccountManager();

        assertEquals(false,accountManager.correctUser("Luka","Macho"));
        assertEquals(false,accountManager.correctUser("Akaki","Macho"));
        assertEquals(true,accountManager.correctUser("Patrick","1234"));
        assertEquals(true,accountManager.correctUser("Molly","FloPup"));
    }

    @Test
    private void additionTest(){
        accountManager = new AccountManager();

        accountManager.createNewAccount("Tapu","tapu");
        assertEquals(true,accountManager.correctUser("Tapu","tapu"));
        assertEquals(false,accountManager.correctUser("Tapu","Tapu"));
        accountManager.createNewAccount("Luka","Macho");
        assertEquals(false,accountManager.correctUser("Luka","xacho"));
        assertEquals(true,accountManager.correctUser("Luka","Macho"));
    }
}
