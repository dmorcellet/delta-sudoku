package delta.games.sudoku;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.environment.FileSystem;

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
    File rootDir=getSudokuTestDataDir();
    File f=new File(rootDir,"testSudoku.txt");
    SudokuGrid grid=SudokuFileReader.readFile(f);
    Assert.assertNotNull(grid);
    grid.dump(System.out);
  }

  public void testSolveSudoku()
  {
    File rootDir=getSudokuTestDataDir();
    File f=new File(rootDir,"testSudoku.txt");
    SudokuGrid grid=SudokuFileReader.readFile(f);
    Assert.assertNotNull(grid);
    grid.dump(System.out);
    SudokuSolver solver=new SudokuSolver();
    solver.solve(grid);
  }

  private File getSudokuTestDataDir()
  {
    File testDataDir=FileSystem.getTestDataDir();
    File gamesDir=new File(testDataDir,"games");
    File sudokuDir=new File(gamesDir,"sudoku");
    return sudokuDir;
  }

  public void testDump()
	{
		SudokuGrid grid=new SudokuGrid();
		grid.dump(System.out);
	}
}
