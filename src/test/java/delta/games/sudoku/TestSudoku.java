package delta.games.sudoku;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.url.URLTools;

public class TestSudoku extends TestCase
{
  /**
   * Constructor.
   */
	public TestSudoku()
	{
		super("Sudoku");
	}

	public void testFillSudoku()
	{
		SudokuGrid grid=new SudokuGrid();
		int bigX=1,bigY=0,x=2,y=0,value=5;
		grid.setValueForCell(bigX,bigY,x,y,value);
		Assert.assertEquals(value,grid.getGrid(bigX,bigY).getValueForCell(x,y));
		try
		{
			grid.setValueForCell(bigX,bigY,x,y,value);
			Assert.assertTrue(false);
		}
		catch(IllegalStateException e)
		{
			//Traces.logException(e);
		}
		grid.dump(System.out);
	}

  public void testReadSudoku()
  {
    SudokuGrid grid=loadGrid("testSudokuFacile");
    Assert.assertNotNull(grid);
    grid.dump(System.out);
  }

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
    URL url=URLTools.getFromClassPath(gridName+".txt", TestSudoku.class.getPackage());
    SudokuGrid grid=SudokuReader.from(url);
    Assert.assertNotNull(grid);
    return grid;
  }

  public void testDump()
	{
		SudokuGrid grid=new SudokuGrid();
		grid.dump(System.out);
	}
}
