package org.ikovia;

import static org.junit.Assert.*;
import org.junit.Test;

public class SudokuTest {

    private String invalidInput1 = "A2131221312321414141";
    private String invalidInput2 = "123456789578139624496872153952381467641297835387564291719623548864915372235748916323";
    private String invalidInput3 = "12345678957813962449687215395238146764129783538756429171962354886491537223574891";

    private String validInput = "123456789578139624496872153952381467641297835387564291719623548864915372235748916";


    @Test(expected = InvalidSudokuException.class)
    public void alphanumericInput() throws InvalidSudokuException{

        Sudoku s = new Sudoku(invalidInput1);
    }

    @Test(expected = InvalidSudokuException.class)
    public void missingDigit() throws InvalidSudokuException{

        Sudoku s = new Sudoku(invalidInput2);
    }

    @Test(expected = InvalidSudokuException.class)
    public void moreDigit() throws InvalidSudokuException{

        Sudoku s = new Sudoku(invalidInput3);
    }

    @Test(expected = InvalidSudokuException.class)
    public void invalidRowBoundary() throws InvalidSudokuException{

        Sudoku s = new Sudoku();
        s.setCell(3,9,1);
        s.setCell(3,-2,1);
    }

    @Test(expected = InvalidSudokuException.class)
    public void invalidColumnBoundary() throws InvalidSudokuException{

        Sudoku s = new Sudoku();
        s.setCell(3,2,-1);
        s.setCell(3,2,12);
    }

    @Test(expected = InvalidSudokuException.class)
    public void invalidData() throws InvalidSudokuException{

        Sudoku s = new Sudoku();
        s.setCell(-3,0,3);
    }

    @Test
    public void sudokuCreate(){

        try {

            Sudoku s = new Sudoku(validInput);
            assertNotNull(s);
        }
        catch (InvalidSudokuException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void getElement(){

        try {

            Sudoku validObj = new Sudoku("123456789578139624496872153952381467641297835387564291719623548864915372235748916");

            assertEquals(validObj.getCell(0,0),1);
            assertEquals(validObj.getCell(2,0),4);
            assertEquals(validObj.getCell(5,4),6);
            assertEquals(validObj.getCell(8,7),1);

        }
        catch (InvalidSudokuException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void subMatrixCalculation(){

        try{

            Sudoku validObj = new Sudoku("123456789578139624496872153952381467641297835387564291719623548864915372235748916");

            int [][] arr1 = validObj.getSubMatrix(0,0);
            int [][] arr1Result = new int[][] {
                    {0,0},
                    {2,2}
            };


            int [][] arr2 = validObj.getSubMatrix(5,3);
            int [][] arr2Result = new int[][] {
                    {3,3},
                    {5,5}
            };

            int [][] arr3 = validObj.getSubMatrix(7,6);
            int [][] arr3Result = new int[][] {
                    {6,6},
                    {8,8}
            };

            assertArrayEquals(arr1,arr1Result);
            assertArrayEquals(arr2,arr2Result);
            assertArrayEquals(arr3,arr3Result);
        }
        catch(InvalidSudokuException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void subMatrixCheck(){

        try{

            Sudoku validObj = new Sudoku("123456789578139624496872153952381467641297835387564291719623548864915372235738916");

            assertTrue(validObj.checkSubMatrix(3,3));
            assertFalse(validObj.checkSubMatrix(6,5));

        }
        catch(InvalidSudokuException e){
            fail(e.getMessage());
        }
    }

}
