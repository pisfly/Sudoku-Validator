package org.ikovia;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SudokuSolver {

    private List<Sudoku> solutions;

    /**
     * Create sudoku list from provided resource
     * @param filename
     * @throws Exception
     */
    public SudokuSolver(String filename) throws Exception{

        solutions = new LinkedList<Sudoku>();

        BufferedReader fileReader = new BufferedReader((new InputStreamReader(getClass().getResourceAsStream(filename))));

        String line;
        while ((line = fileReader.readLine()) != null) {

            if (line.isEmpty() || line.charAt(0) == '#')
                continue;

            Sudoku s = new Sudoku(line);
            solutions.add(s);
        }

    }

    /**
     * Solve all sudoku in a sequential order.
     * Prints invalid solutions and counter of invalid solutions
     * @throws InvalidSudokuException
     */
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

        System.out.println("Invalid Sudoku:" + numberofInvalid);
    }

    /**
     * Solve all sudoku parallelly. Fixed number of thread pool used with Futures.
     * Prints invalid solutions and counter of invalid solutions
     * @throws InvalidSudokuException
     */
    public void solveWithThread() throws InvalidSudokuException{

        ListIterator<Sudoku> it = solutions.listIterator();

        System.out.println("Size:" + solutions.size() + "\n");

        final int NTHREAD = 500;
        ExecutorService executor = Executors.newFixedThreadPool(NTHREAD);

        while(it.hasNext()){

            Sudoku s = it.next();

            executor.submit(s);
        }

        try {

            List<Future<Sudoku>> futures = executor.invokeAll(solutions);

            int numberofInvalid = 0;
            int numberOfValid = 0;

            for (Future<Sudoku> future : futures) {

                Sudoku s = future.get();

                if(s.status == Sudoku.Solution.VALID){

                   // System.out.println("Valid:");
                   // System.out.println(s);
                    ++numberOfValid;
                }
                else if(s.status == Sudoku.Solution.INVALID){

                    System.out.println("Invalid:");
                    System.out.println(s);
                    ++numberofInvalid;
                }
                else{
                    System.out.println("Not solved");
                }
            }

            System.out.println("Valid Sudoku:" + numberOfValid);
            System.out.println("Invalid Sudoku:" + numberofInvalid);
            System.out.println("Feature Size:" + futures.size());
        }
        catch(Exception e){
            System.out.println(e.toString());
        }

        executor.shutdown();
    }
}
