import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;


public class CrackerTest {
    private ByteArrayOutputStream outputStream;
    @BeforeEach
    public void setUp(){
        outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        System.setOut(ps);
    }
    @Test
    public void basicTest() throws InterruptedException, NoSuchAlgorithmException {
        String empty [] ={};
        Cracker.main(empty);
        assertEquals("Args: target length [workers]\n",outputStream.toString());
    }
    @Test
    public void simpleGenarationTest() throws InterruptedException, NoSuchAlgorithmException {
        String simple[] = new String[1];
        simple[0] = "a";
        Cracker.main(simple);
        assertEquals("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8\n",outputStream.toString());
    }
    @Test
    public void twoCharTest() throws InterruptedException, NoSuchAlgorithmException {
        Cracker.main(new String[]{"fm"});
        assertEquals("adeb6f2a18fe33af368d91b09587b68e3abcb9a7\n",outputStream.toString());
    }
    @Test
    public void bigStringTest() throws InterruptedException, NoSuchAlgorithmException {
        Cracker.main(new String[]{"xyz"});
        assertEquals("66b27417d37e024c46526c2f6d358a754fc552f3\n",outputStream.toString());
    }

    @Test
    public void simpleCrackingTest() throws InterruptedException, NoSuchAlgorithmException {
        Cracker.main(new String[]{"86f7e437faa5a7fce15d1ddcb9eaeaea377667b8","1"});
        assertEquals("a\n",outputStream.toString());
    }
    @Test
    public void hardCrackingTest() throws InterruptedException, NoSuchAlgorithmException {
        Cracker.main(new String[]{"f51b71376517116b66ed3f5812566e1bc535c44a","3"});
        assertEquals("fma\n",outputStream.toString());
    }

    @Test
    public void simpleMultiThreadedCrackingTest() throws InterruptedException, NoSuchAlgorithmException {
        Cracker.main(new String[]{"86f7e437faa5a7fce15d1ddcb9eaeaea377667b8","1","5"});
        assertEquals("a\n",outputStream.toString());
    }
    @Test
    public void normalMultiThreadingCrackingTest() throws InterruptedException, NoSuchAlgorithmException {
        Cracker.main(new String[]{"f51b71376517116b66ed3f5812566e1bc535c44a","3","5"});
        assertEquals("fma\n",outputStream.toString());
    }
    @Test
    public void hardMultiThreadingCrackingTest() throws InterruptedException, NoSuchAlgorithmException {
        Cracker.main(new String[]{"4ff88aaddbd209d8026924c2cc2836b408698823","4","1000"});
        assertEquals("free\n",outputStream.toString());
    }
}
