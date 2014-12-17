package org.ikovia;

import java.util.concurrent.Callable;

public class Sudoku implements Callable<Sudoku> {

    public enum Solution{
        NOT_SOLVED, VALID, INVALID
    };

    private int [][] data;
    private final int SUB_SIZE = 3;
    public final int SIZE = 9;

    public Solution status;

    public Sudoku(){

        this.status = Solution.NOT_SOLVED;
        this.data = new int[SIZE][SIZE];
    }

    public Sudoku(String stream) throws InvalidSudokuException{

        this();

        if(stream.length() != SIZE*SIZE){

            throw new InvalidSudokuException("Invalid sudoku solution is provided");
        }
        else{

            for(int k = 0; k < stream.length(); k++){

                int i = k / SIZE;
                int j = k % SIZE;

               // System.out.println("Creating for " + i + "," + j);

                Character c = stream.charAt(k);

                if(!Character.isDigit(c)){
                    throw new InvalidSudokuException("Invalid sudoku solution is provided");
                }
                else{

                    setCell(Character.digit(c,10),i,j);
                }
            }
        }
    }

    @Override
    public Sudoku call() throws Exception{

        isValid();

        return this;
    }

    private void checkBoundaries(int i, int j) throws InvalidSudokuException{

        if(i >= SIZE || i < 0 || j >= SIZE || j < 0){
            throw new InvalidSudokuException("Indices are not correct");
        }
    }

    public void setCell(int cell, int i, int j) throws InvalidSudokuException{

        checkBoundaries(i, j);

        if(cell < 1 || cell > 9)
            throw new InvalidSudokuException("Invalid sudoku data:" + cell);

        data[i][j] = cell;
    }

    public int getCell(int i, int j) throws InvalidSudokuException{

        checkBoundaries(i,j);

        return data[i][j];
    }

    /**
     * Check the specified cell exists whether only once or more in the current horizontal line
     * @param i row index
     * @param j column index
     * @return
     * @throws InvalidSudokuException
     */
    public boolean checkHorizontal(int i, int j) throws InvalidSudokuException {

        int cell = getCell(i,j);

        for(int k = 0; k < SIZE; k++ ){

            if(cell == data[i][k] && k != j ){
                return false;
            }
        }

        return true;
    }

    /**
     * Check the specified cell exists whether only once or more in the current vertical line
     * @param i row index
     * @param j column index
     * @return
     * @throws InvalidSudokuException
     */
    public boolean checkVertical(int i, int j) throws InvalidSudokuException {

        int cell = getCell(i,j);

        for(int k = 0; k < SIZE; k++ ){

            if(cell == data[k][j] && k != i ){
                return false;
            }
        }

        return true;
    }

    /**
     * Check the sub grid which holds the specified cell exists whether once or more
     * @param i row index
     * @param j column index
     * @return true for valid sub grid
     * @throws InvalidSudokuException
     */
    public boolean checkSubMatrix(int i, int j) throws InvalidSudokuException{

        int [][] subMatrix = getSubMatrix(i, j);

        int cell = getCell(i,j);

        for(int i0 = subMatrix[0][0]; i0 <= subMatrix[1][0]; i0++){

            for(int j0 = subMatrix[0][1]; j0 <= subMatrix[1][1]; j0++){

                if(i == i0 && j == j0)
                    continue;

                if(getCell(i0,j0) == cell){
                    return false;
                }
            }
        }


        return true;
    }

    public boolean isValid() throws InvalidSudokuException{

        for(int i = 0; i < SIZE; i++){

            for(int j = 0; j < SIZE; j++){

                boolean res1 = checkHorizontal(i,j);
                boolean res2 = checkVertical(i,j);
                boolean res3 = checkSubMatrix(i,j);

                if(!res1 || !res2 || !res3 ) {
                    status = Solution.INVALID;
                    return false;
                }
            }
        }

        status = Solution.VALID;
        return true;
    }

    /**
     * Find 3x3 sub grid boundaries for a specified cell indices
     * @param i row index
     * @param j column index
     * @return Multi dimensional array which holds pair of i0 -> i1, j0 -> j1
     * @throws InvalidSudokuException
     */
    public int[][] getSubMatrix(int i, int j) throws InvalidSudokuException{

        checkBoundaries(i,j);

        int subI = i / SUB_SIZE;
        int subJ = j / SUB_SIZE;

        int lowI = subI * SUB_SIZE;
        int lowJ = subJ * SUB_SIZE;

        int maxI = lowI + SUB_SIZE - 1;
        int maxJ = lowJ + SUB_SIZE - 1;

        int [][] subMatrix = new int [2][2];

        subMatrix[0][0] = lowI;
        subMatrix[0][1] = lowJ;
        subMatrix[1][0] = maxI;
        subMatrix[1][1] = maxJ;

        return subMatrix;
    }

    @Override
    public String toString(){

        String all = "";

        for(int i = 0; i < SIZE; i++){

            for(int j = 0; j < SIZE; j++){
                all += data[i][j];
                all += " ";
            }
            all += "\n";
        }

        return all;
    }
}
