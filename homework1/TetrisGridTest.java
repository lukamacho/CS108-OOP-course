import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TetrisGridTest extends TestCase {

	// Provided simple clearRows() test
	// width 2, height 3 grid
	@Test
	public void testClear1() {
		boolean[][] before =
				{
						{true, true, false, },
						{false, true, true, }
				};

		boolean[][] after =
				{
						{true, false, false},
						{false, true, false}
				};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()));
	}
	@Test
	public void testDifficult(){
		boolean [][] before = {
				{true,true,false,true,true},
				{true,true,true,false,false},
				{true,true,false,false,true}
		};
		boolean [][] after ={
				{false,true,true,false,false},
				{true,false,false,false,false},
				{false,false,true,false,false}
		};
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue( Arrays.deepEquals(after, tetris.getGrid()));
	}

	@Test
	public void TestEdgeCases(){
		boolean [][] before = {
				{true,true,false,true,true,false},
				{true,true,false,true,true,false},
				{true,true,false,true,true,false},
				{true,true,false,true,true,false},
		};
		boolean [][] after = {
				{false,false,false,false,false,false},
				{false,false,false,false,false,false},
				{false,false,false,false,false,false},
				{false,false,false,false,false,false},
		};
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue( Arrays.deepEquals(after, tetris.getGrid()));
	}
}