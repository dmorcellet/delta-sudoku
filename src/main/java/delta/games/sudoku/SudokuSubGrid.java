package delta.games.sudoku;

public class SudokuSubGrid
{
  // Positions (-1, 0..GRID_CELLS-1) <- Values (0..GRID_CELLS-1)
  private int[] _positions;
  // Values (0..GRID_CELLS) <- Positions (0..GRID_CELLS-1)
  private int[] _values;

  /**
   * For each position, bit set that indicates the possible values.
   * <code>_possibleValues[y*GRID_CELLS+x] & (2^value-1)</code> is zero if
   * <code>value</code> is not possible for cell <code>(x,y)</code>.
   */
  private int[] _possibleValues;

  /**
   * For each value, bit set that indicates the possible cells.
   * <code>__possibleCells[value-1] & (2^(y*GRID_CELLS+x))</code> is zero if
   * cell <code>(x,y)</code> is not possible for value <code>value</code>.
   */
  private int[] _possibleCells;

  public SudokuSubGrid()
  {
    _positions=new int[SudokuConstants.GRID_CELLS];
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _positions[i]=-1;
    }
    _values=new int[SudokuConstants.GRID_CELLS];
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _values[i]=0;
    }
    _possibleValues=new int[SudokuConstants.GRID_CELLS];
    int allPossibleValues=SudokuConstants.VALUES_MASK;
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _possibleValues[i]=allPossibleValues;
    }
    _possibleCells=new int[SudokuConstants.GRID_CELLS];
    int allPossibleCells=SudokuConstants.CELLS_MASK;
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _possibleCells[i]=allPossibleCells;
    }
  }

  public int getValueForCell(int x, int y)
  {
    return _values[y*SudokuConstants.GRID_SIZE+x];
  }

  /**
   * Get the position for a given value.
   * @param value Value to search (1..GRID_CELLS)
   * @return 0..GRID_CELLS-1 if found, -1 otherwise.
   */
  public int getPositionForValue(int value)
  {
    return _positions[value-1];
  }

  public boolean isValuePlaced(int value)
  {
    return (_positions[value-1]!=-1);
  }

  public boolean hLineContains(int y, int value)
  {
    int index=SudokuConstants.GRID_SIZE*y;
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      if (_values[index+i]==value) return true;
    }
    return false;
  }

  public boolean vLineContains(int x, int value)
  {
    for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
    {
      if (_values[i*SudokuConstants.GRID_SIZE+x]==value) return true;
    }
    return false;
  }

  public boolean setValueForCell(int x, int y, int value)
  {
    int pos=getPositionForValue(value);
    int index=SudokuConstants.GRID_SIZE*y+x;
    if (pos!=-1) return false;

    _positions[value-1]=index;
    _values[index]=value;
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _possibleValues[i]&=(~(1<<(value-1)));
    }
    // Disable all values for this cell
    for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
    {
      _possibleCells[i]&=(~(1<<index));
    }
    _possibleValues[index]=0;
    _possibleCells[value-1]=0;
    return true;
  }

  public void disableChoice(int x, int y, int value)
  {
    int index=SudokuConstants.GRID_SIZE*y+x;
    _possibleValues[index]&=(~(1<<(value-1)));
    _possibleCells[value-1]&=(~(1<<index));
  }

  public boolean isPossible(int x, int y, int value)
  {
    int index=SudokuConstants.GRID_SIZE*y+x;
    return (((_possibleValues[index])&(1<<(value-1)))!=0);
  }

  /**
   * Tests whether a value can be found for cell x,y.
   * @param x Horizontal cell coordinate.
   * @param y Vertical cell coordinate.
   * @return -1 is no value can be found, or the value (1..GRID_CELLS) that can
   * be used at x,y.
   */
  public int getDecidableValue(int x, int y)
  {
    int nb=0;
    int index=SudokuConstants.GRID_SIZE*y+x;
    if (_values[index]>0) return -1;
    int choices=_possibleValues[index];
    int value=-1;
    int mask=1;
    for(int k=0;k<SudokuConstants.GRID_CELLS;k++)
    {
      if ((choices&mask)!=0)
      {
        //System.out.println("Value "+(k+1)+" is possible at ("+x+","+y+")");
        if (nb==1)
        {
          //System.out.println("Already found a value there. Skip it.");
          return -1;
        }
        nb++;
        value=k+1;
      }
      mask<<=1;
    }
    return value;
  }

  public int getDecidableCell(int value)
  {
    int nb=0;
    if (_positions[value-1]>=0) return -1;
    int choices=_possibleCells[value-1];
    int cell=-1;
    int mask=1;
    for(int k=0;k<SudokuConstants.GRID_CELLS;k++)
    {
      if ((choices&mask)!=0)
      {
        System.out.println("Cell "+k+" is possible for value "+value);
        if (nb==1)
        {
          System.out.println("Already found a cell for this value. Skip it.");
          return -1;
        }
        nb++;
        cell=k;
      }
      mask<<=1;
    }
    System.out.println("Cell "+cell+" has value "+value);
    return cell;
  }
}
