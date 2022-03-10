import org.junit.jupiter.api.*;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;

public class metropolisTest {

    MetropolisModel metropolisModel;

    @BeforeEach
    public void setup() throws SQLException, ClassNotFoundException {
        metropolisModel = new MetropolisModel();
    }

    @Test
    public void basicTest() throws SQLException, ClassNotFoundException {
       assertEquals(0,metropolisModel.getRowCount());
       assertEquals(3,metropolisModel.getColumnCount());

       assertEquals("Metropolis",metropolisModel.getColumnName(0));
       assertEquals("Continent",metropolisModel.getColumnName(1));
       assertEquals("Population",metropolisModel.getColumnName(2));
    }
    @Test
    public void basicAddTest() throws SQLException {
        metropolisModel.addMetropolis("Tbilisi","Europe","13412");
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("Chiatura","Europe","20000");
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("Sachkhere","Europe","20000");
        assertEquals(1,metropolisModel.getRowCount());
        assertEquals(3,metropolisModel.getColumnCount());
    }

    @Test
    public void basicSearchTest() throws SQLException {
        metropolisModel.addMetropolis("Chiatura","Europe","20000");
        metropolisModel.searchMetropolis("","","124345",false,false);
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.searchMetropolis("Sachkere","Asia","51132",true,true);
        assertEquals(0,metropolisModel.getRowCount());
        metropolisModel.searchMetropolis("","","",false,true);
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("Tbilisi","Europe","1000000");
        metropolisModel.searchMetropolis("Tbilisi","","10000001",true,false);
        assertEquals(0,metropolisModel.getRowCount());
        metropolisModel.searchMetropolis("Tbilisi","Europe","1000",true,false);
        assertEquals(1,metropolisModel.getRowCount());
    }


    @Test
    public void getTest(){
        metropolisModel.addMetropolis("jdfka","gs","ds");
        metropolisModel.addMetropolis("Chiatura","Europe","2020");
        assertEquals(1,metropolisModel.getRowCount());
        assertEquals("Chiatura",metropolisModel.getValueAt(0,0));
    }

    @Test
    public void mainBigTest() throws SQLException {
        metropolisModel.addMetropolis("Mumbai","Asia","20400000");
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("New York","North America","21295000");
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("San Francisco","North America","5780000");
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("London","Europe","8580000");
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("Rome","Europe","2715000");
        assertEquals(1,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("Melbourne","Australia","3900000");
        assertEquals(1,metropolisModel.getRowCount());
        assertEquals("3900000",metropolisModel.getValueAt(0,2));
        metropolisModel.addMetropolis("San Jose","North America","7354555");
        assertEquals(1,metropolisModel.getRowCount());
        assertEquals("North America",metropolisModel.getValueAt(0,1));
        metropolisModel.addMetropolis("Rostov-on-Don","Europe","1052000");
        assertEquals(1,metropolisModel.getRowCount());
        assertEquals("Rostov-on-Don",metropolisModel.getValueAt(0,0));

        metropolisModel.searchMetropolis("","","",true,false);
        assertEquals(8,metropolisModel.getRowCount());
        assertEquals("Mumbai",metropolisModel.getValueAt(0,0));
        assertEquals("North America",metropolisModel.getValueAt(1,1));
        assertEquals("5780000",metropolisModel.getValueAt(2,2));
    }

    @Test
    public void illegalFormatTest(){
        metropolisModel.addMetropolis("","Europe","1");
        assertEquals(0,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("","","");
        assertEquals(0,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("Chiatura","Europe","0");
        assertEquals(0,metropolisModel.getRowCount());
        metropolisModel.addMetropolis("Tbilisi","Europe","-189348932");
        assertEquals(0,metropolisModel.getRowCount());
    }

}
