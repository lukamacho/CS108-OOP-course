
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class CharGridTest extends TestCase {

	@Test
	public void testCharArea1() {
		char[][] grid = new char[][]{
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
		};


		CharGrid cg = new CharGrid(grid);

		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}

	@Test
	public void testCharArea2() {
		char[][] grid = new char[][]{
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}

	@Test
	public void testCharArea3() {
		char[][] grid = new char[][]{
				{'a', 'b'},
				{'c', 'c'}
		};
		CharGrid cur = new CharGrid(grid);
		assertEquals(1, cur.charArea('a'));
		assertEquals(0, cur.charArea('e'));
		assertEquals(2, cur.charArea(('c')));
	}

	@Test
	public void testCharAreaAndPlusses() {
		char[][] grid = new char[][]{
				{'a', 'b', 'c'},
				{'b', 'b', 'b'},
				{'e', 'b', 'k'}
		};
		CharGrid cur = new CharGrid(grid);
		assertEquals(9, cur.charArea('b'));
		assertEquals(0, cur.charArea('m'));
		assertEquals(1, cur.charArea('a'));
		assertEquals(1, cur.countPlus());
	}

	@Test
	public void testPlus() {
		char[][] grid = new char[][]{
				{'a', 'b', 'c', 'd', 'e'},
				{'b', 'b', 'b', 'e', 'm'},
				{'k', 'b', 'e', 'e', 'e'},
				{'k', 'm', 'j', 'e', 'k'},
		};
		CharGrid cur = new CharGrid(grid);
		assertEquals(2, cur.countPlus());
		assertEquals(10, cur.charArea('k'));
		assertEquals(12, cur.charArea('m'));
	}

	@Test
	public void testBigGrid() {
		char[][] grid = new char[][]{
				{'a', 'b', 'b', 'c', 'd', 'e', 'f', 'g', 'j'},
				{'b', 'b', 'b', 'b', 'b', 'f', 'f', 'f', 'l'},
				{'b', 'b', 'b', 'b', 'b', 'l', 'f', 'k', 'h'},
				{'k', 'm', 'b', 'j', 'l', 'l', 'l', 't', 'm'},
				{'i', 't', 'b', 'p', 'q', 'l', 'a', 'b', 'y'}
		};
		CharGrid cur = new CharGrid(grid);
		assertEquals(3, cur.countPlus());
		assertEquals(1, cur.charArea('y'));
		assertEquals(0, cur.charArea('z'));
		assertEquals(40, cur.charArea('b'));
	}
}