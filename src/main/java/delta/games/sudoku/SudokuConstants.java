package delta.games.sudoku;

public class SudokuConstants
{
	public static final int GRID_SIZE=3;
	public static final int GRID_CELLS=GRID_SIZE*GRID_SIZE;
  public static final int VALUES_MASK=(1<<SudokuConstants.GRID_CELLS)-1;
  public static final int CELLS_MASK=(1<<SudokuConstants.GRID_CELLS)-1;
}
