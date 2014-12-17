##Simple Sudoku Validator

This code snippet aims to validate provived sudoku whether valid or not.
It uses **resources/samples.txt** file for sample input.

File format is:

>  Code:  123456789578139624496872153952381467641297835387564291719623548864915372235748916

   Sudoku: 123|456|789
           578|139|624
           496|872|153
           952|381|467
           641|297|835
           387|564|291
           719|623|548
           864|915|372
           235|748|916


Two methods implemented for solver class.
Use **SudokuSolver.solve** method for single thread solution.
Use **SudokuSolver.solveWithThread** for multiple threading solution. It uses a thread pool of size 500.

To build and run use maven.
