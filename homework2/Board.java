
import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	protected boolean DEBUG = true;
	boolean committed;
    private int maxColumnHeight;
	private int [] widths;
	private int [] heights;

	private boolean [][] backupGrid;
	private  int [] backupWidths;
	private int  [] backupHeights;
	private int backupMaxColumnHeight;
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		widths = new int[height];
		heights = new int[width];
		maxColumnHeight = 0;

		backupGrid = new boolean[width][height];
		backupHeights = new int[width];
		backupWidths = new int [height];
		backupMaxColumnHeight = 0;
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		return maxColumnHeight;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			int curMaxColumnHeight = 0;
			int [] curWidths = new int [height];
			int [] curHeights = new int [width];

			for(int i = 0; i < width; i++){
				for(int j =0; j < height; j++){
					if(grid[i][j]){
						curWidths[j]++;
						if(curHeights[i]<=j){
							curHeights[i] = j + 1;
							if(curHeights[i] > curMaxColumnHeight){
								curMaxColumnHeight = curHeights[i];
							}
						}
					}
				}
			}
			if(!Arrays.equals(curWidths,widths)){
				throw new RuntimeException("Incorrect width array");
			}
			if(!Arrays.equals(curHeights,heights)){
				throw new RuntimeException("Incorrect height array");
			}
			if(curMaxColumnHeight != maxColumnHeight){
				System.out.println(curMaxColumnHeight+ " "+maxColumnHeight);
				throw new RuntimeException("Incorrect maxColumnHeight");
			}
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int dropPoint = 0;
		for(int i = 0; i < piece.getWidth(); i++){
			int currentDropPoint = heights[x + i] - piece.getSkirt()[i];
			if(dropPoint < currentDropPoint){
				dropPoint = currentDropPoint;
			}
		}
		return dropPoint;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		return (!isValid(x,y) || grid[x][y]);
	}

	/**
	 * This function gets two integers of the point and return true
	 * if it is in the grid or false otherwise.
	 */
	 private boolean isValid(int x,int y){
		return x > -1 && y > -1 && x < width && y<height;
	 }
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		previousGrid();
		int result = PLACE_OK;
		committed = false;
		for(int i=0; i< piece.getBody().length;i++){
			TPoint cur = piece.getBody()[i];

			int curX = cur.x + x;
			int curY = cur.y + y;
			if(!isValid(curX,curY)){
				return PLACE_OUT_BOUNDS;
			}
			if(grid[curX][curY]){
				return PLACE_BAD;
			}
			grid[curX][curY] = true;
			widths[curY]++;
			heights[curX] = Math.max(curY + 1,heights[curX]);
			if(maxColumnHeight < heights[curX]){
				maxColumnHeight = heights[curX];
			}
			if(widths[curY] == width){
				result = PLACE_ROW_FILLED;
			}
		}
		sanityCheck();
		return result;
	}

	private void previousGrid() {
		for(int i=0; i<width; i++){
			for(int j=0;j<height;j++){
				backupGrid[i][j] = grid[i][j];
			}
		}
		backupMaxColumnHeight = maxColumnHeight;
		for(int i=0;i<height;i++){
			backupWidths[i] = widths[i];
		}
		for(int i=0;i<width;i++){
			backupHeights[i] = heights[i];
		}
	}


	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		committed = false;
		int toRow =0;
		for(int fromRow = 0;fromRow < height;fromRow++){
			if(widths[fromRow] == width){
				rowsCleared++;
				continue;
			}else if(rowsCleared > 0){
				widths[toRow] = widths[fromRow];
				for(int i = 0; i < width; i++){
					grid[i][toRow] = grid[i][fromRow];
				}
				toRow++;
			}else{
				toRow++;
			}
		}
		while(toRow<height){
			widths[toRow]=0;
			for(int i=0;i<width;i++){
				grid[i][toRow]=false;
				heights[i]--;
			}
			toRow++;
			maxColumnHeight--;
		}
		for(int i=0;i<width;i++){
			boolean exist = false;
			for(int j=0;j<height;j++){
				if(grid[i][j]){
					heights[i]=j+1;
					exist = true;
				}
			}
			if(!exist) {
				heights[i] = 0;
			}
		}
		sanityCheck();
		return rowsCleared;
	}


	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(committed){
			return;
		}
		boolean [][] tempGrid = grid;
		grid = backupGrid;
		backupGrid = tempGrid;

		int [] tempWidths = widths;
		widths = backupWidths;
		backupWidths = tempWidths;

		int [] tempHeights = heights;
		heights = backupHeights;
		backupHeights = tempHeights;

		int tempMaxColumnHeight = maxColumnHeight;
		maxColumnHeight = backupMaxColumnHeight;
		backupMaxColumnHeight = tempMaxColumnHeight;

		commit();
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	public void changeDebugMode(){
		DEBUG = !DEBUG;
	}
	/**
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


