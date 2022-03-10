import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    public void basics(){
        Bank bank = new Bank();
        Account first = new Account(bank,1,1);
        assertEquals("acct:1 bal:1 trans:0 ",first.toString());
        Account  second = new Account(bank,2,3);
        assertEquals("acct:2 bal:3 trans:0 ",second.toString());
        Account third = new Account(bank, 5, 5);
        assertEquals("acct:5 bal:5 trans:0 ",third.toString());
    }

    @Test
    public void additionTest(){
        Bank bank = new Bank();
        Account first = new Account(bank,1,0);
        first.addMoney(100);
        assertEquals("acct:1 bal:100 trans:1 ",first.toString());
        Account second = new Account(bank,2,100);
        second.addMoney(1000);
        assertEquals("acct:2 bal:1100 trans:1 ",second.toString());
        second.addMoney(11);
        assertEquals("acct:2 bal:1111 trans:2 ",second.toString());
    }
    @Test
    public void loseMoneyTest(){
        Bank bank = new Bank();
        Account first = new Account(bank,1,1000);
        first.loseMoney(100);
        assertEquals("acct:1 bal:900 trans:1 ",first.toString());
        Account second = new Account(bank,2,200);
        second.loseMoney(200);
        assertEquals("acct:2 bal:0 trans:1 ",second.toString());
        first.loseMoney(800);
        first.loseMoney(100);
        assertEquals("acct:1 bal:0 trans:3 ",first.toString());
    }
    @Test
    public void compositionTest(){
        Bank bank = new Bank();
        Account first = new Account(bank,1,0);
        first.addMoney(100);
        assertEquals("acct:1 bal:100 trans:1 ",first.toString());
        first.loseMoney(50);
        assertEquals("acct:1 bal:50 trans:2 ",first.toString());
        first.loseMoney(50);
        assertEquals("acct:1 bal:0 trans:3 ",first.toString());
    }
}
