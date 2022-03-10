//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	private boolean[][] tetrisGrid;

	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 *
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.tetrisGrid = grid;
	}


	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		for (int i = 0; i < tetrisGrid[0].length; i++) {
			if (goodRow(i)) {
				clearCurRow(i);
				i--;
			}
		}
	}

	/**
	 * Checks if the column contains only "True"-s.
	 */
	private boolean goodRow(int row) {
		for (int col = 0; col < tetrisGrid.length; col++) {
			if (!tetrisGrid[col][row]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Clears the current row and push others one row back;
	 */
	private void clearCurRow(int row) {
		for (int i = row; i < tetrisGrid[0].length - 1; i++) {
			for (int j = 0; j < tetrisGrid.length; j++) {
				tetrisGrid[j][i] = tetrisGrid[j][i + 1];
			}
		}
		for (int i = 0; i < tetrisGrid.length; i++) {
			tetrisGrid[i][tetrisGrid[0].length - 1] = false;
		}
	}

	/**
	 * Returns the internal 2d grid array.
	 *
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {

		return tetrisGrid;
	}
}