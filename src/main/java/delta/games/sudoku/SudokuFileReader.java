package delta.games.sudoku;

import java.io.File;

import delta.common.utils.files.TextFileReader;

public abstract class SudokuFileReader
{
  public static SudokuGrid readFile(File f)
  {
    SudokuGrid grid=null;
    TextFileReader reader=new TextFileReader(f);
    if (reader.start())
    {
      grid=new SudokuGrid();

      String line;
      int length;
      char c;
      for(int i=0;i<SudokuConstants.GRID_CELLS;i++)
      {
        line=reader.getNextLine();
        if (line==null)
        {
          break;
        }
        length=Math.min(line.length(),SudokuConstants.GRID_CELLS);
        for(int j=0;j<length;j++)
        {
          c=line.charAt(j);
          if (Character.isDigit(c))
          {
            grid.setValueForCell(j/SudokuConstants.GRID_SIZE,i/SudokuConstants.GRID_SIZE,j%SudokuConstants.GRID_SIZE,i%SudokuConstants.GRID_SIZE,c-'0');
          }
        }
      }
      reader.terminate();
    }
    return grid;
  }
}
