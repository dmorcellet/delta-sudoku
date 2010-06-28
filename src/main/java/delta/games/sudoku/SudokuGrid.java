package delta.games.sudoku;

import java.io.PrintStream;

public class SudokuGrid
{
	private SudokuSubGrid[] _grids;
	public SudokuGrid()
	{
		_grids=new SudokuSubGrid[SudokuConstants.GRID_CELLS];
		for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
		{
			_grids[i]=new SudokuSubGrid();
		}
	}

	public SudokuSubGrid getGrid(int x, int y)
	{
		int index=SudokuConstants.GRID_SIZE*y+x;
		return _grids[index];
	}

	private boolean checkValuePlacing(int bigX, int bigY, int x, int y, int value)
	{
		if (getGrid(bigX,bigY).isValuePlaced(value)) return false;
		if (hLineContains(bigY,y,value)) return false;
		if (vLineContains(bigX,x,value)) return false;
		return true;
	}

	public boolean hLineContains(int bigY, int y, int value)
	{
		for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
			if (getGrid(i,bigY).hLineContains(y,value)) return true;
		return false;
	}

	public boolean vLineContains(int bigX, int x, int value)
	{
		for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
			if (getGrid(bigX,i).vLineContains(x,value)) return true;
		return false;
	}

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
		SudokuSubGrid tmp;
    // Handle horizontal line
		for(int i=0;i<SudokuConstants.GRID_SIZE;i++)
		{
			if (i!=bigX)
			{
				tmp=getGrid(i,bigY);
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
				tmp=getGrid(bigX,i);
				for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
				{
					tmp.disableChoice(x,j,value);
				}
			}
		}
		return true;
	}

	public void dump(PrintStream ps)
	{
		SudokuSubGrid tmp;
		int value;
		{
			ps.print('|');
			for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
			{
				for(int i=0;i<SudokuConstants.GRID_SIZE;i++) ps.print('-');
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
						if (value==0) ps.print(' '); else ps.print(value);
					}
					ps.print('|');
				}
				ps.println("");
			}
			{
				ps.print('|');
				for(int j=0;j<SudokuConstants.GRID_SIZE;j++)
				{
					for(int i=0;i<SudokuConstants.GRID_SIZE;i++) ps.print('-');
					ps.print('|');
				}
				ps.println("");
			}
		}
	}

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
