package delta.games.sudoku;

import java.io.PrintStream;

/**
 * Sudoku grid.
 * @author DAM
 */
public class SudokuGrid
{
  private SudokuSubGrid[][] _grids;

  /**
   * Constructor.
   */
  public SudokuGrid()
  {
    _grids=new SudokuSubGrid[SudokuConstants.GRID_SIZE][];
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      _grids[i]=new SudokuSubGrid[SudokuConstants.GRID_SIZE];
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        _grids[i][j]=new SudokuSubGrid();
      }
    }
  }

  /**
   * Get a sub-grid.
   * @param x Horizontal index for cell (starting at 0).
   * @param y Vertical index for cell (starting at 0).
   * @return A sub-grid.
   */
  public SudokuSubGrid getGrid(int x, int y)
  {
    return _grids[x][y];
  }

  private boolean checkValuePlacing(int bigX, int bigY, int x, int y, int value)
  {
    if (getGrid(bigX,bigY).isValuePlaced(value)) return false;
    if (hLineContains(bigY,y,value)) return false;
    if (vLineContains(bigX,x,value)) return false;
    return true;
  }

  /**
   * Indicates if a horizontal line contains the given value.
   * @param bigY Vertical index for sub-grid (starting at 0).
   * @param y Vertical index inside this sub-grid (starting at 0).
   * @param value Value to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hLineContains(int bigY, int y, int value)
  {
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
      if (getGrid(i,bigY).hLineContains(y,value)) return true;
    return false;
  }

  /**
   * Indicates if a vertical line contains the given value.
   * @param bigX Horizontal index for sub-grid (starting at 0).
   * @param x Horizontal index inside this sub-grid (starting at 0).
   * @param value Value to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean vLineContains(int bigX, int x, int value)
  {
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
      if (getGrid(bigX,i).vLineContains(x,value)) return true;
    return false;
  }

  /**
   * Set the value for a cell.
   * @param bigX Horizontal index for sub-grid (starting at 0).
   * @param bigY Vertical index for sub-grid (starting at 0).
   * @param x Horizontal index inside this sub-grid (starting at 0).
   * @param y Vertical index inside this sub-grid (starting at 0).
   * @param value Value to set.
   * @return <code>true</code> if it succeeded, <code>false</code> otherwise.
   */
  public boolean setValueForCell(int bigX, int bigY, int x, int y, int value)
  {
    if (!checkValuePlacing(bigX,bigY,x,y,value))
    {
      throw new IllegalStateException("Cannot place value "+value+" at ("+bigX+","+bigY+") ("+x+","+y+") !");
    }
    SudokuSubGrid grid=getGrid(bigX,bigY);
    boolean ok=grid.setValueForCell(x,y,value);
    if (!ok) return false;

    // Update choices
    // Handle horizontal line
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      if (i!=bigX)
      {
        SudokuSubGrid tmp=getGrid(i,bigY);
        for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
        {
          tmp.disableChoice(j,y,value);
        }
      }
    }
    // Handle vertical line
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      if (i!=bigY)
      {
        SudokuSubGrid tmp=getGrid(bigX,i);
        for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
        {
          tmp.disableChoice(x,j,value);
        }
      }
    }
    return true;
  }

  /**
   * Dump grid contents to the given output stream.
   * @param ps Output stream.
   */
  public void dump(PrintStream ps)
  {
    SudokuSubGrid tmp;
    Integer value;
    {
      ps.print('|');
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
          ps.print('-');
        ps.print('|');
      }
      ps.println("");
    }
    for(int bigJ=0;bigJ<SudokuConstants.GRID_SIZE;bigJ++)
    {
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        ps.print('|');
        for(int bigI=0;bigI<SudokuConstants.GRID_SIZE;bigI++)
        {
          tmp=getGrid(bigI,bigJ);
          for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
          {
            value=tmp.getValueForCell(i,j);
            if (value==null)
              ps.print(' ');
            else
              ps.print(value);
          }
          ps.print('|');
        }
        ps.println("");
      }
      {
        ps.print('|');
        for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
        {
          for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
            ps.print('-');
          ps.print('|');
        }
        ps.println("");
      }
    }
  }

  /**
   * Dump choices to the given output stream.
   * @param ps Output stream.
   */
  public void dumpChoices(PrintStream ps)
  {
    int nbLines=SudokuConstants.GRID_SIZE*SudokuConstants.GRID_SIZE+4;
    StringBuilder[] lines=new StringBuilder[nbLines];
    for(int i=0;i<nbLines;i++)
    {
      lines[i]=new StringBuilder();
    }
    for(int value=1;value<=SudokuConstants.GRID_CELLS;value++)
    {
      int lineNumber=0;
      SudokuSubGrid tmp;
      // Draw separation line
      {
        lines[lineNumber].append('|');
        for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
        {
          for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
          {
            lines[lineNumber].append('-');
          }
          lines[lineNumber].append('|');
        }
      }
      lineNumber++;

      for(int bigJ=0;bigJ<SudokuConstants.GRID_SIZE;bigJ++)
      {
        for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
        {
          lineNumber=1+(bigJ*4)+j;
          lines[lineNumber].append('|');
          for(int bigI=0;bigI<SudokuConstants.GRID_SIZE;bigI++)
          {
            tmp=getGrid(bigI,bigJ);
            for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
            {
              if (tmp.isPossible(i,j,value))
              {
                lines[lineNumber].append(value);
              }
              else
              {
                lines[lineNumber].append(' ');
              }
            }
            lines[lineNumber].append('|');
          }
        }
        // Draw separation line
        {
          lineNumber=1+(bigJ*4)+3;
          lines[lineNumber].append('|');
          for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
          {
            for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
            {
              lines[lineNumber].append('-');
            }
            lines[lineNumber].append('|');
          }
        }
      }

      // Add separation space
      for(int i=0;i<nbLines;i++)
      {
        lines[i].append(' ');
      }
    }

    for(int i=0;i<nbLines;i++)
    {
      ps.println(lines[i]);
    }
  }
}
