import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
	Board b,mainBoard;
	private Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;
	private Piece stick,stickR;
	private Piece square;
	private Piece lType;


	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@BeforeEach
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		mainBoard = new Board(10,12);
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		stick = new Piece(Piece.STICK_STR);
		stickR = stick.computeNextRotation();
		square = new Piece(Piece.SQUARE_STR);
		lType = new Piece(Piece.L1_STR);

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		b.place(pyr1, 0, 0);

	}

	@Test
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}

	@Test
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}

	@Test
	public void simpleBoardWidthHeightTest(){
		assertEquals(3,b.getWidth());
		assertEquals(6,b.getHeight());
		assertEquals(10,mainBoard.getWidth());
		assertEquals(12,mainBoard.getHeight());
	}

	@Test
	public void WidthHeightTest(){
		int result = mainBoard.place(stick,0,0);
		mainBoard.commit();
		int secondResult = mainBoard.place(pyr1, 1,1);
		assertEquals(mainBoard.PLACE_OK,result);
		assertEquals(mainBoard.PLACE_OK,secondResult);
		assertEquals(4,mainBoard.getMaxHeight());
		assertEquals(4,mainBoard.getColumnHeight(0));
		assertEquals(1,mainBoard.getRowWidth(0));
		assertEquals(4,mainBoard.getRowWidth(1));
		assertEquals(2,mainBoard.getRowWidth(2));
		assertEquals(3,mainBoard.getColumnHeight(2));
	}
	@Test
	public void gridGetTest(){
		b.commit();
		assertTrue(b.getGrid(0,0));
		assertFalse(b.getGrid(0,1));
		assertTrue(b.getGrid(-1,9));

		mainBoard.place(stick,0,0);
		mainBoard.commit();
		assertTrue(mainBoard.getGrid(0,0));
		assertTrue(mainBoard.getGrid(0,1));
		assertFalse(mainBoard.getGrid(1,0));
		assertFalse(mainBoard.getGrid(5,4));

		mainBoard.place(pyr1,1,0);
		mainBoard.commit();
		assertTrue(mainBoard.getGrid(1,0));
		assertTrue(mainBoard.getGrid(2,1));
		assertFalse(mainBoard.getGrid(1,1));

		mainBoard.place(square,3,2);
		assertFalse(mainBoard.getGrid(3,6));
		assertTrue(mainBoard.getGrid(3,2));
		assertTrue(mainBoard.getGrid(4,3));
	}
	@Test
	public void maxHeightTest(){
		assertEquals(0,mainBoard.getMaxHeight());
		mainBoard.place(stick,0,0);
		mainBoard.commit();
		assertEquals(4,mainBoard.getMaxHeight());

		mainBoard.place(pyr1,3,3);
		assertEquals(5,mainBoard.getMaxHeight());
		mainBoard.commit();

		mainBoard.place(square,4,0);
		assertEquals(5,mainBoard.getMaxHeight());
		mainBoard.commit();
		mainBoard.place(lType,4,5);
		assertEquals(8,mainBoard.getMaxHeight());
	}

	@Test
	public void dropHeightTest(){
		mainBoard.place(stick,1,0);
		assertEquals(4,mainBoard.dropHeight(pyr1,0));
		mainBoard.commit();

		mainBoard.place(square,2,0);
		assertEquals(2,mainBoard.dropHeight(stick,2));
		mainBoard.commit();

		assertEquals(0,mainBoard.dropHeight(pyr1,4));
		assertEquals(4,mainBoard.dropHeight(pyr1,0));

		mainBoard.place(lType,5,0);
		assertEquals(3,mainBoard.dropHeight(stick,5));
	}

	@Test
	public void clearRowTest(){
		int clearedRowsNum = 0;
		mainBoard.place(stickR,0,0);
		clearedRowsNum = mainBoard.clearRows();
		assertEquals(0,clearedRowsNum);
		mainBoard.commit();

		mainBoard.place(stickR,4,0);
		clearedRowsNum = mainBoard.clearRows();
		assertEquals(0,clearedRowsNum);
		mainBoard.commit();
		mainBoard.place(square,8,0);
		mainBoard.commit();
		clearedRowsNum = mainBoard.clearRows();
		assertEquals(1,clearedRowsNum);
		mainBoard.commit();

		clearedRowsNum = b.clearRows();
		assertEquals(1,clearedRowsNum);
		b.commit();

		assertEquals(1,b.getMaxHeight());
		assertEquals(1,mainBoard.getMaxHeight());
		assertEquals(0,mainBoard.getColumnHeight(2));
		assertEquals(0,b.getColumnHeight(0));

		int result = mainBoard.place(stickR,0,0);
		assertEquals(mainBoard.PLACE_OK,result);
		mainBoard.commit();
		mainBoard.place(stickR,4,0);
		mainBoard.commit();
		mainBoard.place(pyr1,0,1);
		mainBoard.commit();
		mainBoard.place(stickR,3,1);
		mainBoard.commit();
		mainBoard.place(pyr1,7,1);
		assertEquals(10,mainBoard.getRowWidth(0));
		assertEquals(10,mainBoard.getRowWidth(1));
		assertEquals(2,mainBoard.getRowWidth(2));
		assertEquals(3,mainBoard.getMaxHeight());
		assertEquals(3,mainBoard.getMaxHeight());
		clearedRowsNum = mainBoard.clearRows();
		assertEquals(2,clearedRowsNum);
		assertEquals(1,mainBoard.getMaxHeight());
		assertEquals(0,mainBoard.getColumnHeight(0));
		assertEquals(1,mainBoard.getColumnHeight(1));
	}
	@Test
	public void undoTest(){
		mainBoard.place(pyr4,3,5);
		mainBoard.undo();
		for(int i = 0;i < 10; i++){
			assertEquals(0,mainBoard.getRowWidth(i));
		}

		mainBoard.place(pyr1,1,1);
		mainBoard.commit();
		mainBoard.undo();
		for(int i=0;i<10;i++){
			if(i==1){
				assertEquals(3,mainBoard.getRowWidth(i));
			}else if(i==2){
				assertEquals(1,mainBoard.getRowWidth(i));
			}else{
				assertEquals(0,mainBoard.getRowWidth(i));
			}
		}
		assertEquals(3,mainBoard.getMaxHeight());
		assertEquals(3,mainBoard.dropHeight(stick,2));
		assertEquals(3,mainBoard.getColumnHeight(2));
	}
	@Test
	public void stringTest(){
		mainBoard.place(pyr1,0,0);
		mainBoard.commit();
		mainBoard.place(stickR,4,0);
		mainBoard.commit();
		mainBoard.place(square,8,0);
		//System.out.println(mainBoard.toString());
		mainBoard.commit();
		String empty ="|          |\n";
		String end = "------------";
		String second = "| +      ++|\n";
		String last = "|+++ ++++++|\n";
		//System.out.println(empty+empty+empty+empty+empty+empty+empty+empty+empty+empty+second+last+end);
		assertEquals(empty + empty + empty + empty + empty + empty
						+ empty + empty + empty + empty + second + last + end,
				mainBoard.toString());

		mainBoard.place(stickR,2,1);
		String changedSecond= "| +++++  ++|\n";
		assertEquals(empty + empty + empty + empty + empty + empty +
				empty + empty + empty + empty + changedSecond + last + end,mainBoard.toString());
	}

	@Test
	public void debugChangeTest(){
		mainBoard.changeDebugMode();
		mainBoard.sanityCheck();

		mainBoard.changeDebugMode();
		mainBoard.sanityCheck();
	}
}