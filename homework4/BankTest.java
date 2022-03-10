import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    private ByteArrayOutputStream outputStream;
    @BeforeEach
    public void setUp(){
        outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        System.setOut(ps);
    }
    @Test
    public void basic() throws InterruptedException {
        Bank.main(new String[]{});
        assertEquals("Args: transaction-file [num-workers [limit]]\n",outputStream.toString());
    }

    @Test
    public void smallNonConcurrentTest() throws InterruptedException {
        Bank.main(new String[]{"small.txt"});
        assertEquals("acct:0 bal:999 trans:1 \n" + "acct:1 bal:1001 trans:1 \n" +
                "acct:2 bal:999 trans:1 \n" + "acct:3 bal:1001 trans:1 \n" + "acct:4 bal:999 trans:1 \n" + "acct:5 bal:1001 trans:1 \n" +
                "acct:6 bal:999 trans:1 \n" + "acct:7 bal:1001 trans:1 \n" +
                "acct:8 bal:999 trans:1 \n" + "acct:9 bal:1001 trans:1 \n" + "acct:10 bal:999 trans:1 \n" + "acct:11 bal:1001 trans:1 \n" +
                "acct:12 bal:999 trans:1 \n" + "acct:13 bal:1001 trans:1 \n" +
                "acct:14 bal:999 trans:1 \n" + "acct:15 bal:1001 trans:1 \n" +
                "acct:16 bal:999 trans:1 \n" + "acct:17 bal:1001 trans:1 \n" +
                "acct:18 bal:999 trans:1 \n" + "acct:19 bal:1001 trans:1 \n",outputStream.toString());
    }
    @Test
    public void normalNonConcurrentTest() throws InterruptedException {
        Bank.main(new String[]{"5k.txt"});
        assertEquals("acct:0 bal:1000 trans:518 \n" + "acct:1 bal:1000 trans:444 \n" + "acct:2 bal:1000 trans:522 \n" + "acct:3 bal:1000 trans:492 \n" + "acct:4 bal:1000 trans:526 \n" + "acct:5 bal:1000 trans:526 \n" +
                "acct:6 bal:1000 trans:474 \n" + "acct:7 bal:1000 trans:472 \n" +
                "acct:8 bal:1000 trans:436 \n" + "acct:9 bal:1000 trans:450 \n" +
                "acct:10 bal:1000 trans:498 \n" + "acct:11 bal:1000 trans:526 \n" +
                "acct:12 bal:1000 trans:488 \n" + "acct:13 bal:1000 trans:482 \n" +
                "acct:14 bal:1000 trans:516 \n" + "acct:15 bal:1000 trans:492 \n" +
                "acct:16 bal:1000 trans:520 \n" + "acct:17 bal:1000 trans:528 \n" +
                "acct:18 bal:1000 trans:586 \n" + "acct:19 bal:1000 trans:504 \n", outputStream.toString());
    }

    @Test
    public void simpleConcurrentTest() throws InterruptedException {
        Bank.main(new String[]{"small.txt","1"});
        assertEquals("acct:0 bal:999 trans:1 \n" + "acct:1 bal:1001 trans:1 \n" +
                "acct:2 bal:999 trans:1 \n" + "acct:3 bal:1001 trans:1 \n" + "acct:4 bal:999 trans:1 \n" + "acct:5 bal:1001 trans:1 \n" +
                "acct:6 bal:999 trans:1 \n" + "acct:7 bal:1001 trans:1 \n" +
                "acct:8 bal:999 trans:1 \n" + "acct:9 bal:1001 trans:1 \n" + "acct:10 bal:999 trans:1 \n" + "acct:11 bal:1001 trans:1 \n" +
                "acct:12 bal:999 trans:1 \n" + "acct:13 bal:1001 trans:1 \n" +
                "acct:14 bal:999 trans:1 \n" + "acct:15 bal:1001 trans:1 \n" +
                "acct:16 bal:999 trans:1 \n" + "acct:17 bal:1001 trans:1 \n" +
                "acct:18 bal:999 trans:1 \n" + "acct:19 bal:1001 trans:1 \n",outputStream.toString());
    }

    @Test
    public void basicmultiThreadingTest() throws InterruptedException {
        Bank.main(new String[]{"small.txt","8"});
        assertEquals("acct:0 bal:999 trans:1 \n" + "acct:1 bal:1001 trans:1 \n" +
                "acct:2 bal:999 trans:1 \n" + "acct:3 bal:1001 trans:1 \n" + "acct:4 bal:999 trans:1 \n" + "acct:5 bal:1001 trans:1 \n" +
                "acct:6 bal:999 trans:1 \n" + "acct:7 bal:1001 trans:1 \n" +
                "acct:8 bal:999 trans:1 \n" + "acct:9 bal:1001 trans:1 \n" + "acct:10 bal:999 trans:1 \n" + "acct:11 bal:1001 trans:1 \n" +
                "acct:12 bal:999 trans:1 \n" + "acct:13 bal:1001 trans:1 \n" +
                "acct:14 bal:999 trans:1 \n" + "acct:15 bal:1001 trans:1 \n" +
                "acct:16 bal:999 trans:1 \n" + "acct:17 bal:1001 trans:1 \n" +
                "acct:18 bal:999 trans:1 \n" + "acct:19 bal:1001 trans:1 \n",outputStream.toString());
    }

    @Test
    public void mediumMultiThreadingTest() throws InterruptedException {
        Bank.main(new String[]{"5k.txt", "10"});
        assertEquals("acct:0 bal:1000 trans:518 \n" + "acct:1 bal:1000 trans:444 \n" + "acct:2 bal:1000 trans:522 \n" +
                "acct:3 bal:1000 trans:492 \n" + "acct:4 bal:1000 trans:526 \n" +
                "acct:5 bal:1000 trans:526 \n" + "acct:6 bal:1000 trans:474 \n" +
                "acct:7 bal:1000 trans:472 \n" + "acct:8 bal:1000 trans:436 \n" +
                "acct:9 bal:1000 trans:450 \n" + "acct:10 bal:1000 trans:498 \n" +
                "acct:11 bal:1000 trans:526 \n" + "acct:12 bal:1000 trans:488 \n" +
                "acct:13 bal:1000 trans:482 \n" + "acct:14 bal:1000 trans:516 \n" + "acct:15 bal:1000 trans:492 \n" +
                "acct:16 bal:1000 trans:520 \n" + "acct:17 bal:1000 trans:528 \n" +
                "acct:18 bal:1000 trans:586 \n" + "acct:19 bal:1000 trans:504 \n", outputStream.toString());
    }

    @Test
    public void bigMultiThreadingTest() throws InterruptedException {
        Bank.main(new String[]{"100k.txt", "100"});
        assertEquals("acct:0 bal:1000 trans:10360 \n" + "acct:1 bal:1000 trans:8880 \n" +
                "acct:2 bal:1000 trans:10440 \n" + "acct:3 bal:1000 trans:9840 \n" +
                "acct:4 bal:1000 trans:10520 \n" + "acct:5 bal:1000 trans:10520 \n" +
                "acct:6 bal:1000 trans:9480 \n" + "acct:7 bal:1000 trans:9440 \n" +
                "acct:8 bal:1000 trans:8720 \n" + "acct:9 bal:1000 trans:9000 \n" +
                "acct:10 bal:1000 trans:9960 \n" + "acct:11 bal:1000 trans:10520 \n" +
                "acct:12 bal:1000 trans:9760 \n" + "acct:13 bal:1000 trans:9640 \n" +
                "acct:14 bal:1000 trans:10320 \n" + "acct:15 bal:1000 trans:9840 \n" +
                "acct:16 bal:1000 trans:10400 \n" + "acct:17 bal:1000 trans:10560 \n" +
                "acct:18 bal:1000 trans:11720 \n" + "acct:19 bal:1000 trans:10080 \n", outputStream.toString());
    }
}
