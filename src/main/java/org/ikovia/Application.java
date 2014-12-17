package org.ikovia;

import java.io.FileNotFoundException;

public class Application {

    public static void main(String [] args){

        System.out.println("Sudoku solver started...");

        try{

            SudokuSolver solver = new SudokuSolver("/samples.txt");
            solver.solve();
        }
        catch(Exception e){

            System.out.println(e.toString());
        }

    }
}
