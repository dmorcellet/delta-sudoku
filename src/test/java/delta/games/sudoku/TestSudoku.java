package delta.games.sudoku;

import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.url.URLTools;

/**
 * Test for sudoko solver.
 * @author DAM
 */
public class TestSudoku extends TestCase
{
  /**
   * Constructor.
   */
  public TestSudoku()
  {
    super("Sudoku");
  }

  /**
   * Test cell fill.
   */
  public void testFillSudoku()
  {
    SudokuGrid grid=new SudokuGrid();
    int bigX=1, bigY=0, x=2, y=0, value=5;
    grid.setValueForCell(bigX,bigY,x,y,value);
    Assert.assertEquals(value,grid.getGrid(bigX,bigY).getValueForCell(x,y).intValue());
    try
    {
      grid.setValueForCell(bigX,bigY,x,y,value);
      Assert.assertTrue(false);
    }
    catch (IllegalStateException e)
    {
      // Traces.logException(e);
    }
    grid.dump(System.out);
  }

  /**
   * Test grid loading.
   */
  public void testReadSudoku()
  {
    SudokuGrid grid=loadGrid("testSudokuFacile");
    Assert.assertNotNull(grid);
    grid.dump(System.out);
  }

  /**
   * Test grid solving.
   */
  public void testSolveSudoku()
  {
    SudokuGrid grid=loadGrid("testSudokuFacile");
    Assert.assertNotNull(grid);
    grid.dump(System.out);
    SudokuSolver solver=new SudokuSolver();
    solver.solve(grid);
  }

  private static SudokuGrid loadGrid(String gridName)
  {
    URL url=URLTools.getFromClassPath(gridName+".txt",TestSudoku.class.getPackage());
    SudokuGrid grid=SudokuReader.from(url);
    Assert.assertNotNull(grid);
    return grid;
  }

  /**
   * Test grid dump.
   */
  public void testDump()
  {
    SudokuGrid grid=new SudokuGrid();
    grid.dump(System.out);
  }
}
