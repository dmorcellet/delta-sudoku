package delta.games.sudoku;

/**
 * Solver for sudoku grids.
 * @author DAM
 */
public class SudokuSolver
{
  /**
   * Constructor.
   */
  public SudokuSolver()
  {
    // Nothing to do
  }

  /**
   * Solve a grid.
   * @param grid Grid to solve.
   */
  public void solve(SudokuGrid grid)
  {
    //grid.dumpChoices(System.out);
    int nbFound;
    do
    {
      nbFound=0;
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
        {
          if (getDecidableValue(grid,i,j)>0)
          {
            nbFound++;
          }
        }
      }
    }
    while (nbFound>0);
  }

  private int getDecidableValue(SudokuGrid grid, int bigX, int bigY)
  {
    //System.out.println("Examining cell "+bigX+","+bigY);
    SudokuSubGrid subGrid=grid.getGrid(bigX,bigY);
    for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
    {
      for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
      {
        Integer value=subGrid.getDecidableValue(i,j);
        if (value!=null)
        {
          grid.setValueForCell(bigX,bigY,i,j,value.intValue());
          System.out.println("bigX="+bigX+",bigY="+bigY+",x="+i+",y="+j+" -> "+value);
          grid.dumpChoices(System.out);
          grid.dump(System.out);
          return value.intValue();
        }
      }
    }

    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      //System.out.println("Examining value "+(i+1)+" in subgrid "+bigX+","+bigY);
      SudokuCell cell=subGrid.getDecidableCell(i+1);
      if (cell!=null)
      {
        int x=cell.getX();
        int y=cell.getY();
        grid.setValueForCell(bigX,bigY,x,y,i+1);
        System.out.println("bigX="+bigX+",bigY="+bigY+",x="+x+",y="+y+" -> "+(i+1));
        grid.dumpChoices(System.out);
        grid.dump(System.out);
        return i+1;
      }
    }

    return 0;
  }
}
