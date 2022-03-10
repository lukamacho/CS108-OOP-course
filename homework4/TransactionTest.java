import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class TransactionTest {

    @Test
    public void basicTest(){
        Transaction first = new Transaction(1,2,100);
        assertEquals("from:1 to:2 amt:100", first.toString());

        Transaction second = new Transaction(2,3,4);
        assertEquals("from:2 to:3 amt:4", second.toString());

        Transaction third = new Transaction(3,4,5);
        assertEquals("from:3 to:4 amt:5", third.toString());
    }
    @Test
    public void fromAcctest(){
        Transaction first = new Transaction(1,2,100);
        assertEquals(1,first.fromAcc());

        Transaction second = new Transaction(2,3,100);
        assertEquals(2,second.fromAcc());

        Transaction third = new Transaction(3,4,5);
        assertEquals(3,third.fromAcc());
    }

    @Test
    public void toAccTest(){
        Transaction first = new Transaction(1,2,100);
        assertEquals(2,first.toAcc());

        Transaction second = new Transaction(2,3,100);
        assertEquals(3,second.toAcc());

        Transaction third = new Transaction(3,4,5);
        assertEquals(4,third.toAcc());
    }
    @Test
    public void getAmountTest(){
        Transaction first = new Transaction(1,2,100);
        assertEquals(100,first.getAmount());

        Transaction second = new Transaction(2,3,10);
        assertEquals(10,second.getAmount());

        Transaction third = new Transaction(3,4,5);
        assertEquals(5,third.getAmount());
    }

    @Test
    public void compositionTest(){
        Transaction first = new Transaction(1,2,1000);
        assertEquals(1,first.fromAcc());
        assertEquals(2,first.toAcc());
        assertEquals(1000,first.getAmount());

        Transaction second = new Transaction(2,4,500);
        assertEquals(2,second.fromAcc());
        assertEquals(4,second.toAcc());
        assertEquals(500,second.getAmount());
    }
}
