package org.ikovia;


public class Application {

    public static void main(String [] args){

        System.out.println("Sudoku solver started...");

        try{

            SudokuSolver solver = new SudokuSolver("/samples.txt");
            solver.solveWithThread();
        }
        catch(Exception e){

            System.out.println(e.toString());
        }

    }
}
