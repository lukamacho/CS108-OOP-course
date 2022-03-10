import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

public class SudokuTest {

    @Test
    public void simpleStringTest(){
            int [][] expected;
            int [][] returned;

            returned = Sudoku.stringsToGrid("0 1 2");
            expected = new int [][]{{0,1,2}};
            assertTrue(Arrays.deepEquals(returned,expected));

            returned = Sudoku.stringsToGrid("0 1 \n","1 2");
            expected = new int [][] {{0,1},{1,2}};
            assertTrue(Arrays.deepEquals(returned,expected));

            /*Empty grid test*/
            returned = Sudoku.stringsToGrid("","","","");
            expected = new int [][] {{},{},{},{}};
            assertTrue(Arrays.deepEquals(returned,expected));

            returned = Sudoku.stringsToGrid("1 2 3 4 3 \n"," 4 5 6 2 1");
            expected = new int [][] {{1,2,3,4,3},{4,5,6,2,1}};
            assertTrue(Arrays.deepEquals(expected,returned));
    }

    @Test
    public void textTest(){
        int [][] expected;
        int [][] returned;
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < 81;i++){
            sb.append("0");
        }
        returned = Sudoku.textToGrid(sb.toString());
        expected = new int [9][9];
        for(int i = 0;i < 9;i++){
            for(int j = 0; j < 9;j++){
                expected[i][j] = 0;
            }
        }
        assertTrue(Arrays.deepEquals(returned,expected));
        sb.replace(1,2,"1");
        returned = Sudoku.textToGrid(sb.toString());
        expected[0][1] = 1;
        assertTrue(Arrays.deepEquals(returned,expected));
        sb.replace(2,3,"4");
        returned = Sudoku.textToGrid(sb.toString());
        expected[0][2] = 4;
        assertTrue(Arrays.deepEquals(returned,expected));
    }

    @Test
    public void testStringToInts(){
        int[] result;
        int[] expected;

        result = Sudoku.stringToInts("1 2 3 4 5");
        expected = new int [] {1,2,3,4,5};
        assertTrue(Arrays.equals(expected,result));

        result = Sudoku.stringToInts("1 2 4      8");
        expected = new int [] {1,2,4,8};
        assertTrue(Arrays.equals(expected,result));

        //Edge case empty string;
        result = Sudoku.stringToInts("");
        expected = new int []{};
        assertTrue(Arrays.equals(expected,result));

        result = Sudoku.stringToInts("0 1 2 3 4 5 7 5 3");
        expected = new int [] {0,1,2,3,4,5,7,5,3};
        assertTrue(Arrays.equals(expected,result));
    }
    @Test
    public void gameTest(){
        Sudoku game;
        int numSolutions = 0;

        game = new Sudoku(Sudoku.easyGrid);
        numSolutions = game.solve();
        assertEquals(1,numSolutions);
        assertEquals(game.getSolutionText(),"1 6 4 7 9 5 3 8 2 \n" +
                "2 8 7 4 6 3 9 1 5 \n" +
                "9 3 5 2 8 1 4 6 7 \n" +
                "3 9 1 8 7 6 5 2 4 \n" +
                "5 4 6 1 3 2 7 9 8 \n" +
                "7 2 8 9 5 4 1 3 6 \n" +
                "8 1 9 6 4 7 2 5 3 \n" +
                "6 7 3 5 2 9 8 4 1 \n" +
                "4 5 2 3 1 8 6 7 9 ");
        assertTrue(100 > game.getElapsed());
        game = new Sudoku(Sudoku.hardGrid);
        numSolutions = game.solve();
        assertEquals(1,numSolutions);
        assertEquals(game.getSolutionText(),"3 7 5 1 6 2 4 8 9 \n"+
                "8 6 1 4 9 3 5 2 7 \n"+
                "2 4 9 7 8 5 1 6 3 \n"+
                "4 9 3 8 5 7 6 1 2 \n"+
                "7 1 6 2 4 9 8 3 5 \n"+
                "5 2 8 3 1 6 7 9 4 \n"+
                "6 5 7 9 2 1 3 4 8 \n"+
                "1 8 2 5 3 4 9 7 6 \n"+
                "9 3 4 6 7 8 2 5 1 " );
        assertTrue(100  > game.getElapsed());
        game = new Sudoku(Sudoku.mediumGrid);
        numSolutions = game.solve();
        assertEquals(1,numSolutions);
        assertTrue(100 > game.getElapsed());
        game = new Sudoku(Sudoku.myGrid);
        numSolutions = game.solve();
        assertEquals(numSolutions,5);
        assertTrue(game.getElapsed() > 1);

    }
    @Test
    public void sudokuMainTest(){
        String [] arr = new String[3];
        Sudoku.main(arr);
    }
}
