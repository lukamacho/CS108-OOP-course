// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private final char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}

	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int leftX=-1;
		int leftY=-1;
		int rightX=-1;
		int rightY=-1;
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				if(grid[i][j]==ch){
					if(leftX==-1||leftX>i){
						leftX=i;
					}
					if(leftY==-1||leftY>j){
						leftY=j;
					}
					if(rightX==-1||rightX<i){
						rightX=i;
					}
					if(rightY==-1||rightY<j){
						rightY=j;
					}
				}
			}
		}
		if(leftX==-1){
			return 0;
		}
		return (rightX-leftX+1)*(rightY-leftY+1);
	}

	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int plusNums=0;
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				if(correctCross(i,j)){
					plusNums++;
				}
			}
		}
		return plusNums;
	}
	private boolean correctCross(int i, int j){
		int left=charsNumber(i,j,0,-1);
		int right=charsNumber(i,j,0,1);
		int up=charsNumber(i,j,-1,0);
		int down=charsNumber(i,j,1,0);
		if(left==0){
			return false;
		}

		return left == right && left == up && left == down;
	}
	private int charsNumber(int i,int j, int dx, int dy){
		int nextX=i+dx;
		int nextY=j+dy;
		if(inBorders(nextX,nextY)&&grid[i][j]==grid[nextX][nextY]){
			return 1+charsNumber(nextX,nextY,dx,dy);
		}
		return 0;
	}
	private boolean inBorders(int i,int j){
		return i>-1&&j>-1&&i<grid.length&&j<grid[i].length;
	}

}