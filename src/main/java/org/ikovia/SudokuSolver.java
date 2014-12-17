package org.ikovia;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SudokuSolver {

    private List<Sudoku> solutions;

    public SudokuSolver(String filename) throws Exception{

        solutions = new LinkedList<Sudoku>();

        BufferedReader fileReader = new BufferedReader((new InputStreamReader(getClass().getResourceAsStream(filename))));

        String line;
        while ((line = fileReader.readLine()) != null) {

            if (line.charAt(0) == '#')
                continue;

            Sudoku s = new Sudoku(line);
            solutions.add(s);
        }

    }

    public void solve() throws InvalidSudokuException{

        ListIterator<Sudoku> it = solutions.listIterator();

        System.out.println("Size:" + solutions.size() + "\n");

        int numberofInvalid = 0;

        while(it.hasNext()){

            Sudoku s = it.next();

            if(s.isValid()){
               System.out.println("Valid");
               System.out.println(s.toString());
            }
            else{
                ++numberofInvalid;
                System.out.println("Invalid");
                System.out.println(s.toString());
            }

            System.out.println("");
        }
    }
}
