package delta.games.sudoku;

public class SudokuSolver
{
  public SudokuSolver()
  {
    // Nothing to do
  }

  public void solve(SudokuGrid grid)
  {
    grid.dumpChoices(System.out);
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
        int value=subGrid.getDecidableValue(i,j);
        if (value>0)
        {
          grid.setValueForCell(bigX,bigY,i,j,value);
          System.out.println("bigX="+bigX+",bigY="+bigY+",x="+i+",y="+j+" -> "+value);
          grid.dumpChoices(System.out);
          grid.dump(System.out);
          return value;
        }
      }
    }

    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      System.out.println("Examining value "+(i+1)+" in subgrid "+bigX+","+bigY);
      int cell=subGrid.getDecidableCell(i+1);
      if (cell>=0)
      {
        grid.setValueForCell(bigX,bigY,cell%SudokuConstants.GRID_SIZE,cell/SudokuConstants.GRID_SIZE,i+1);
        grid.dumpChoices(System.out);
        grid.dump(System.out);
        return i+1;
      }
    }

    return 0;
  }
}
