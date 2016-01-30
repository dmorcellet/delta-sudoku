package delta.games.sudoku;

import java.io.File;
import java.net.URL;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.url.URLTools;

/**
 * Reader for sudoku grids.
 * @author DAM
 */
public abstract class SudokuReader
{
  /**
   * Read grid from a file.
   * @param f File to use.
   * @return the read grid.
   */
  public static SudokuGrid readFile(File f)
  {
    URL url=URLTools.buildFileURL(f);
    return from(url);
  }

  /**
   * Read grid from an URL.
   * @param url URL to use.
   * @return the read grid.
   */
  public static SudokuGrid from(URL url)
  {
    SudokuGrid grid=null;
    TextFileReader reader=new TextFileReader(url, EncodingNames.UTF_8);
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
