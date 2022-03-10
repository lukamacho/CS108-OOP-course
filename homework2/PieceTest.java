import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

	private Piece[] pieces;
	/*
	* Pieces which are  squares;
	* */
	private Piece quad1, quad2;
	/*
	* LType formated cubes;
	* */
	private Piece lType1, lType2, lType3, lType4, lType5;
	/*
	* Long stick like shapes;
	* */
	private Piece stick1,stick2;
	/*
	* Pyramid shaped cubes;
	* */
    private Piece pyr1, pyr2, pyr3, pyr4;
	@BeforeEach
	protected void setUp() {
		pieces = Piece.getPieces();

		quad1 = new Piece(Piece.SQUARE_STR);
		quad2 = quad1.computeNextRotation();

		stick1 = new Piece(Piece.STICK_STR);
		stick2 = stick1.computeNextRotation();

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		lType1 = new Piece(Piece.L1_STR);
		lType2 = lType1.computeNextRotation();
		lType3 = lType2.computeNextRotation();
		lType4 = lType3.computeNextRotation();
		lType5 = lType4.computeNextRotation();
	}

	/*
	* This test checks piece getWidth function;
	* */
	@Test
	public void widthTest() {
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr2.getWidth());
		assertEquals(3,pyr3.getWidth());
		assertEquals(2, pyr4.getWidth());

		assertEquals(2, quad1.getWidth());
		assertEquals(2, quad2.getWidth());

		assertEquals(1,stick1.getWidth());
		assertEquals(4,stick2.getWidth());

		assertEquals(2, lType1.getWidth());
		assertEquals(3, lType2.getWidth());
		assertEquals(2, lType3.getWidth());
		assertEquals(3, lType4.getWidth());
		assertEquals(2, lType5.getWidth());
	}

	/*
	 * This test checks piece getHeight function;
	 * */
	@Test
	public void heightTest() {
		assertEquals(2, pyr1.getHeight());
		assertEquals(3, pyr2.getHeight());
		assertEquals(2,pyr3.getHeight());
		assertEquals(3,pyr4.getHeight());

		assertEquals(2, quad1.getHeight());
		assertEquals(2, quad2.getHeight());

		assertEquals(4,stick1.getHeight());
		assertEquals(1,stick2.getHeight());

		assertEquals(3, lType1.getHeight());
		assertEquals(2, lType2.getHeight());
		assertEquals(3, lType3.getHeight());
		assertEquals(2, lType4.getHeight());
		assertEquals(3, lType5.getHeight());
	}

	/*
	 * This test checks piece getSkirt function;
	 * */
	@Test
	public void skirtTest() {
		assertArrayEquals(new int[]{0,0,0},pyr1.getSkirt());
		assertArrayEquals(new int []{1,0},pyr2.getSkirt());
		assertArrayEquals(new int []{1,0,1},pyr3.getSkirt());
		assertArrayEquals(new int []{0,1},pyr4.getSkirt());

		assertArrayEquals(new int[]{0, 0}, quad1.getSkirt());
		assertArrayEquals(new int[]{0, 0}, quad2.getSkirt());

		assertArrayEquals(new int[]{0},stick1.getSkirt());
		assertArrayEquals(new int[]{0,0,0,0},stick2.getSkirt());

		assertArrayEquals(new int[]{0, 0},    lType1.getSkirt());
		assertArrayEquals(new int[]{0, 0, 0}, lType2.getSkirt());
		assertArrayEquals(new int[]{2, 0},    lType3.getSkirt());
		assertArrayEquals(new int[]{0, 1, 1}, lType4.getSkirt());
		assertArrayEquals(new int[]{0, 0},    lType5.getSkirt());
	}

	/*
	 * This test checks piece fastRotation function;
	 * */
	@Test
	public void fastRotationTest() {
		Piece quadrilaterals = pieces[Piece.SQUARE];
		assertTrue(quad1.equals(quadrilaterals));
		assertTrue(quad2.equals(quadrilaterals.fastRotation()));

		Piece pyramids = pieces[Piece.PYRAMID];
		assertTrue(pyr1.equals(pyramids));
		assertTrue(pyr2.equals(pyramids.fastRotation()));
		assertTrue(pyr3.equals(pyramids.fastRotation().fastRotation()));


		Piece sticks = pieces[Piece.STICK];
		assertTrue(stick1.equals(sticks));
		assertTrue(stick2.equals(sticks.fastRotation()));

		Piece lTypes = pieces[Piece.L1];
		assertTrue(lType1.equals(lTypes));
		assertTrue(lType2.equals(lTypes.fastRotation()));
		assertTrue(lType3.equals(lTypes.fastRotation().fastRotation()));
		assertTrue(lType4.equals(lTypes.fastRotation().fastRotation().fastRotation()));
		assertTrue(lType5.equals(lTypes.fastRotation().fastRotation().fastRotation().fastRotation()));
	}
	/*
	 * This test checks piece equal function;
	 * */
	@Test
	public void equalityTest() {
		assertFalse(pyr1.equals(pyr2));
		assertFalse(pyr2.equals(pyr3));
		assertFalse(pyr2.equals(new Piece("0 0 1 1 2 2 0 0")));

		assertTrue(pyr1.equals(pyr4.computeNextRotation()));
		assertTrue(pyr1.equals(new Piece ("0 0 1 0 2 0 1 1")));

		assertFalse(quad1.equals(null));
		assertFalse(quad1.equals(new Piece("0 0  1 0  1 1 0 2")));

		assertTrue(quad1.equals(quad1));
		assertTrue(quad1.equals(quad2));


		assertFalse(stick1.equals("0 0 1 1"));
		assertFalse(stick2.equals(null));
		assertFalse(stick1.equals(stick2));

		assertTrue(stick1.equals(new Piece("0 0 0 1 0 2 0 3")));

		assertFalse(lType1.equals(null));
		assertFalse(lType1.equals(new Piece("1 2  0 0")));

		assertTrue(lType1.equals(lType1));
		assertFalse(lType1.equals(lType2));
		assertFalse(lType1.equals(lType3));
		assertFalse(lType1.equals(lType4));
		assertTrue(lType1.equals(lType5));
	}


}