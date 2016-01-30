package delta.games.sudoku;

/**
 * Sudoku sub-grid.
 * @author DAM
 */
public class SudokuSubGrid
{
  private SudokuCell[][] _cells;

  /**
   * Constructor.
   */
  public SudokuSubGrid()
  {
    _cells=new SudokuCell[SudokuConstants.GRID_SIZE][];
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      _cells[i]=new SudokuCell[SudokuConstants.GRID_SIZE];
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        _cells[i][j]=new SudokuCell(i,j);
      }
    }
  }

  /**
   * Get the value for a cell.
   * @param x Horizontal index for cell (starting at 0).
   * @param y Vertical index for cell (starting at 0).
   * @return A value or <code>null</code> if not set.
   */
  public Integer getValueForCell(int x, int y)
  {
    Integer value=_cells[x][y].getValue();
    return value;
  }

  /**
   * Get the cell for a given value.
   * @param value Value to search (1..GRID_CELLS)
   * @return A cell or <code>null</code> if not found.
   */
  public SudokuCell getPositionForValue(int value)
  {
    SudokuCell ret=null;
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        SudokuCell cell=_cells[i][j];
        Integer cellValue=cell.getValue();
        if ((cellValue!=null) && (cellValue.intValue()==value))
        {
          ret=cell;
          break;
        }
      }
      if (ret!=null)
      {
        break;
      }
    }
    return ret;
  }

  /**
   * Indicates if the given value is placed in this sub-grid.
   * @param value Value to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isValuePlaced(int value)
  {
    return (getPositionForValue(value)!=null);
  }

  /**
   * Indicates if a horizontal line contains the given value.
   * @param y Vertical index inside this sub-grid (starting at 0).
   * @param value Value to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hLineContains(int y, int value)
  {
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      Integer cellValue=_cells[i][y].getValue();
      if ((cellValue!=null) && (cellValue.intValue()==value))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Indicates if a vertical line contains the given value.
   * @param x Horizontal index inside this sub-grid (starting at 0).
   * @param value Value to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean vLineContains(int x, int value)
  {
    for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
    {
      Integer cellValue=_cells[x][j].getValue();
      if ((cellValue!=null) && (cellValue.intValue()==value))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Set the value for a cell.
   * @param x Horizontal index inside this sub-grid (starting at 0).
   * @param y Vertical index inside this sub-grid (starting at 0).
   * @param value Value to set.
   * @return <code>true</code> if it succeeded, <code>false</code> otherwise.
   */
  public boolean setValueForCell(int x, int y, int value)
  {
    SudokuCell pos=getPositionForValue(value);
    if (pos!=null) return false;

    _cells[x][y].setValue(Integer.valueOf(value));
    // Disable value in all other cells
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        if ((i!=x) || (j!=y))
        {
          _cells[i][j].disableValue(value);
        }
      }
    }
    return true;
  }

  /**
   * Disable a value in a child cell.
   * @param x Horizontal index inside this sub-grid (starting at 0).
   * @param y Vertical index inside this sub-grid (starting at 0).
   * @param value Value to disable.
   */
  public void disableChoice(int x, int y, int value)
  {
    _cells[x][y].disableValue(value);
  }

  /**
   * Indicates if a value is possible in a child cell.
   * @param x Horizontal index inside this sub-grid (starting at 0).
   * @param y Vertical index inside this sub-grid (starting at 0).
   * @param value Value to test.
   * @return <code>true</code> it this value is possible, <code>false</code> otherwise.
   */
  public boolean isPossible(int x, int y, int value)
  {
    return _cells[x][y].isValuePossible(value);
  }

  /**
   * Tests whether a value can be found for cell x,y.
   * @param x Horizontal cell coordinate.
   * @param y Vertical cell coordinate.
   * @return <code>null</code> is no value can be found, or the value (1..GRID_CELLS) that can
   * be used at x,y.
   */
  public Integer getDecidableValue(int x, int y)
  {
    return _cells[x][y].getDecidableValue();
  }

  /**
   * Indicates if a single cell is candidate for the given value.
   * @param value Value to test.
   * @return <code>null</code> if there is not such candidate, otherwise
   * the selected cell.
   */
  public SudokuCell getDecidableCell(int value)
  {
    SudokuCell cell=null;
    int nbCellsForValue=0;
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
      {
        if (_cells[i][j].isValuePossible(value))
        {
          cell=_cells[i][j];
          nbCellsForValue++;
        }
      }
    }
    if (nbCellsForValue==1)
    {
      return cell;
    }
    return null;
  }
}
