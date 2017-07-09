# Jsudokusolver
A sudoku solver coded from scratch in Java:).

JSudokusolver takes advantage of Java's object oriented features to solve inputted sudokus in a logical and humanlike manner. The algorithm which
I named "Guilty" operates on the basis of the well known principle "guilty until proven innocent." This is an analogy for assuming that a single
square on the sudoku board can correspond to any of the 9 numbers until it is proven, with absolute certainty (using the rules and methods of
sudoku), that only a single number can occupy the square. Therefore, the algorithm requires that all 81 squares on the grid are checked 
individually for numbers that be certainly filled in. Each sweep of 81 checks is known as an iteration. More than 1 iteration to solve the sudoku
is required when the addition of numbers in one sweep leads to the discovery of other numbers in the next sweep and so forth. 

To use the algorithm:
1. clone the project and use any Java IDE (i.e NetBeans/Eclipse) to open the file Sudokusolver.java in the src folder of the repository. 
2. write your sudoku in unsolved form in the following format: 0 replaces unknown numbers, and the numbers are ordered from left to right, separated by commas. 
3. copy this entire list of numbers and paste it between the two curly brackets denoted by int [] startingArray.
4. Hit run!

*A GUI using the swing library will be made available in the future, greatly simplifying the use of the algorithm.*

Screenshots are included below:

![alt text](https://raw.githubusercontent.com/ShreyasPrasad/Jsudokusolver/master/screenshots/ss1.png)
![alt text](https://raw.githubusercontent.com/ShreyasPrasad/Jsudokusolver/master/screenshots/ss2.png)
