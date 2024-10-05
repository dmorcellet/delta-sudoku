package delta.games.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import delta.common.utils.url.URLTools;

/**
 * Test for sudoko solver.
 * @author DAM
 */
@DisplayName("Sudoku")
class TestSudoku
{
  /**
   * Test cell fill.
   */
  @Test
  void testFillSudoku()
  {
    SudokuGrid grid=new SudokuGrid();
    int bigX=1, bigY=0, x=2, y=0, value=5;
    grid.setValueForCell(bigX,bigY,x,y,value);
    assertEquals(value,grid.getGrid(bigX,bigY).getValueForCell(x,y).intValue());
    try
    {
      grid.setValueForCell(bigX,bigY,x,y,value);
      assertTrue(false);
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
  @Test
  void testReadSudoku()
  {
    SudokuGrid grid=loadGrid("testSudokuFacile");
    assertNotNull(grid);
    grid.dump(System.out);
  }

  /**
   * Test grid solving.
   */
  @Test
  void testSolveSudoku()
  {
    SudokuGrid grid=loadGrid("testSudokuFacile");
    assertNotNull(grid);
    grid.dump(System.out);
    SudokuSolver solver=new SudokuSolver();
    solver.solve(grid);
  }

  private static SudokuGrid loadGrid(String gridName)
  {
    URL url=URLTools.getFromClassPath(gridName+".txt",TestSudoku.class);
    SudokuGrid grid=SudokuReader.from(url);
    assertNotNull(grid);
    return grid;
  }

  /**
   * Test grid dump.
   */
  @Test
  void testDump()
  {
    SudokuGrid grid=new SudokuGrid();
    grid.dump(System.out);
  }
}
