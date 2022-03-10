import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {

	private int [][] matrix;
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	public static final  int[][] myGrid = Sudoku.stringsToGrid(
			"3 7 0 0 0 0 0 8 0",
			"0 0 1 0 9 3 0 0 0",
			"0 4 0 7 8 0 0 0 3",
			"0 9 3 8 0 0 0 1 2",
			"0 0 0 0 4 0 0 0 0",
			"5 2 0 0 0 6 7 9 0",
			"6 0 0 0 0 1 0 4 0",
			"0 0 0 0 0 0 9 0 0",
			"0 3 0 0 0 0 0 5 1");
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;

	/**
	 * Spot class which contains every king of information about the grid spot.
	 * */
	public class Spot implements Comparable{
		private final int x;
		private final int y;

		public Spot (int row,int col){
			x = row;
			y = col;
		}
		public void set(int value){
			matrix[x][y] = value;
		}
		/**
		 * This function returns the hashset of integers which may be valid in this spot.
		 * */
		public HashSet<Integer> validCombinations(){
			HashSet<Integer> answer = new HashSet<>();
			for(int i = 1;i < 10; i++){
				answer.add(i);
			}
			for(int i = 0; i < SIZE; i++){
				answer.remove(matrix[x][i]);
			}
			for(int i = 0; i < SIZE; i++){
				answer.remove(matrix[i][y]);
			}
			int upperLeftX = (x / PART) * PART;
			int upperLeftY = (y / PART) * PART;

			for(int i = upperLeftX; i < upperLeftX + 3;i++){
				for(int j = upperLeftY; j< upperLeftY + 3;j++){
					answer.remove(matrix[i][j]);
				}
			}
			return answer;
		}
		@Override
		public int compareTo(Object sp){
			Spot cur = (Spot) sp;
			return this.validCombinations().size() - cur.validCombinations().size();
		}
	}

	// Provided various static utility methods to
	// convert data formats to int[][] grid.


	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);

		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	private ArrayList<Spot> spots;
	private int[][] sudokuSolution;
	private int numSolutions;
	private long codeTime;
	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		matrix = ints;
		sudokuSolution = new int[SIZE][SIZE];
		makeSpots();
	}
	private void makeSpots(){
		spots = new ArrayList<>();
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(matrix[i][j] == 0){
					Spot cur = new Spot(i,j);
					spots.add(cur);
				}
			}
		}
		Collections.sort(spots);
	}
	
	
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		long startTime = System.currentTimeMillis();
		backtracking(0);
		codeTime = System.currentTimeMillis() - startTime;
		return numSolutions;
	}

	private void backtracking(int index){
		if(numSolutions >= MAX_SOLUTIONS){
			return;
		}
		if(index == spots.size()){
			numSolutions++;
			rememberSolution();
			return;
		}
		Spot current = spots.get(index);

		HashSet<Integer> validSpots = current.validCombinations();

		for(int value : validSpots){
			current.set(value);

			backtracking(index + 1);
			current.set(0);
		}
	}
	private void rememberSolution(){
		if(numSolutions == 1){
			for(int i = 0; i < SIZE; i++){
				for(int j = 0; j < SIZE;j++){
					sudokuSolution[i][j] = matrix[i][j];
				}
			}
		}
	}
	public String getSolutionText() {
		return gridToString();
	}

	public String gridToString() {
		StringBuilder answer = new StringBuilder();
		for(int i = 0; i < SIZE;i++ ){
			for(int j = 0; j < SIZE ;j++){
				answer.append(sudokuSolution[i][j]);
				answer.append(" ");
			}
			if(i != SIZE - 1)
			answer.append("\n");
		}
		return answer.toString();
	}

	public long getElapsed() {
		return codeTime;
	}

}
