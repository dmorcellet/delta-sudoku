package delta.games.sudoku;

/**
 * Storage for a single SUDOKU cell.
 * @author DAM
 */
public class SudokuCell
{
  // A null value indicates an undecided cell value
  // otherwise _value.intValue() gives the value of the cell.
  private Integer _value;
  // _possibleValues[i-1] indicates the number of cells blocking the value.
  private int[] _possibleValues;

  private int _x;
  private int _y;

  /**
   * Constructor.
   * @param x Horizontal index (starting at 0).
   * @param y Vertical index (starting at 0).
   */
  public SudokuCell(int x,int y)
  {
    _x=x;
    _y=y;
    _possibleValues=new int[SudokuConstants.GRID_CELLS];
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _possibleValues[i]=0;
    }
  }

  /**
   * Get horizontal index.
   * @return An index (starting at 0);
   */
  public int getX()
  {
    return _x;
  }

  /**
   * Get vertical index.
   * @return An index (starting at 0);
   */
  public int getY()
  {
    return _y;
  }

  /**
   * Reset the value of this cell.
   * @param value Value to set.
   */
  public void clearValue(int value)
  {
    _value=null;
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _possibleValues[i]--;
    }
  }

  /**
   * Set the value of this cell.
   * @param value Value to set.
   */
  public void setValue(int value)
  {
    _value=Integer.valueOf(value);
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _possibleValues[i]++;
    }
  }

  /**
   * Enable a value for this cell.
   * @param value Value to enable (starting at 1).
   */
  public void enableValue(int value)
  {
    _possibleValues[value-1]--;
  }

  /**
   * Disable a value for this cell.
   * @param value Value to disable (starting at 1).
   */
  public void disableValue(int value)
  {
    _possibleValues[value-1]++;
  }

  /**
   * Get the value of this cell.
   * @return a value or <code>null</code> if undecided.
   */
  public Integer getValue()
  {
    return _value;
  }

  /**
   * Indicates if the given value is possible or not.
   * @param value Value to test.
   * @return <code>true</code> if it is possible, <code>false</code> otherwise.
   */
  public boolean isValuePossible(int value)
  {
    return _possibleValues[value-1]==0;
  }

  /**
   * Get the number of possible different values in this cell.
   * @return A count.
   */
  public int getPossibleValueCount()
  {
    int ret=0;
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      if (_possibleValues[i]==0)
      {
        ret++;
      }
    }
    return ret;
  }

  /**
   * Tests whether a value can be found for this cell.
   * @return <code>null</code> is no value can be found, or the value (1..GRID_CELLS) that can
   * be used for this cell.
   */
  public Integer getDecidableValue()
  {
    int value=0;
    int nbPossibleValues=0;
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      if (_possibleValues[i]==0)
      {
        value=i+1;
        nbPossibleValues++;
      }
    }
    if ((value>0) && (nbPossibleValues==1))
    {
      return Integer.valueOf(value);
    }
    return null;
  }
}

