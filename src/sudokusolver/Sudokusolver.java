/*
 //guilty algorithm
 */
package sudokusolver;

/**
 * @author Sheyas Krishna Prasad, July 8th 2017
 */
public class Sudokusolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Sudokusolver();
    }
    private int[] startingArray = {0,0,5,7,6,0,2,0,4,0,0,2,0,9,5,8,0,1,1,6,9,0,0,8,0,3,0,9,1,0,0,5,2,0,7,0,0,0,0,1,0,4,9,2,6,2,4,7,0,0,9,0,5,0,6,2,8,9,0,0,0,0,5,0,5,0,2,8,0,3,0,9,3,0,0,5,1,0,6,8,0};//FORMATTED SUDOKU GRID GOES HERE
    private int[] currentIterationArray = new int[81];
    private int[] possibleNums = new int[9];
    private int[] trackEmptySquares = new int[8];
    private boolean[] posSquares = new boolean[9];
    int positionOfSquare = 0;
    //boxAdjacentTracker used for adjacent method parameters, first 2: rows, last 2: columns
    private int[][] checkBoxAdjacent = new int[4][9];
    private int[][][] checkLinearAdjacent = new int[2][8][9];

    boolean unsolved = false;
    int iterationNum = 0, boxCounter, adjacentVertCounter, adjacentHorCounter;

    public Sudokusolver() {
        resetPossibleNums();
        while (!unsolved) {
            for (int i = 0; i < startingArray.length; i++) {
                if (startingArray[i] == 0) {
                    checkPossibility(i);
                }

            }
            iterationNum++;
            System.out.println("Iteration #" + iterationNum + " complete.");
            unsolved = checkSolved();

        }

        System.out.println(iterationNum + " iterations of the guilty algorithm were required to solve the sudoku.");
        System.out.println("The solved sudoku is: ");
        printSolution();

    }

    private void checkPossibility(int index) {
        //check all possible methods of possible number for given index     
        int orderOfMethods = 0;
        while (!updateGrid(index)) {//evaluate if number can be DEFINITELY determined and if so, update grid 
            if (orderOfMethods == 1) {
                checkBox(index);
            } else if (orderOfMethods == 2) {
                checkHorizontal(index, false, false);
            } else if (orderOfMethods == 3) {
                checkVertical(index, false, false);
            } else {
                checkAdjacentLines(index);
            }

            orderOfMethods++;
            if (orderOfMethods == 5) {
                resetPossibleNums();
                break;
            }
        }
    }
    //directions: 1=up,2=down,3=left,4=right,5=diagUpLeft, 6-diagUpRight, 7=diagBottomLeft, 8=diagBottomRight,9=Up2Left, 
    //10=Up2Right, 11=Down2Left, 12=Down2Right, 13=UpRight2,14=DownRight2,15=UpLeft2,16=DownLeft2

    private void checkBox(int index) {
        int row = (index / 9) + 1;
        int column = (index % 9) + 1;

        int[] directions = new int[8];
        boolean[] checks = new boolean[8];
        if (column % 3 == 2) {
            if (row % 3 == 2) {
                directions[0] = 1;
                directions[1] = 2;
                directions[2] = 3;
                directions[3] = 4;
                directions[4] = 5;
                directions[5] = 6;
                directions[6] = 7;
                directions[7] = 8;
                for (int i = 0; i < checks.length; i++) {
                    checks[i] = false;
                }
                positionOfSquare = 4;

            } else if (row % 3 == 1) {
                directions[0] = 3;
                directions[1] = 4;
                directions[2] = 2;
                directions[3] = 7;
                directions[4] = 8;
                directions[5] = 2;
                directions[6] = 11;
                directions[7] = 12;
                checks[0] = false;
                checks[1] = false;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = false;
                positionOfSquare = 1;

            } else {
                directions[0] = 3;
                directions[1] = 4;
                directions[2] = 1;
                directions[3] = 5;
                directions[4] = 6;
                directions[5] = 1;
                directions[6] = 9;
                directions[7] = 10;
                checks[0] = false;
                checks[1] = false;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = false;
                positionOfSquare = 7;
            }
        } else if (column % 3 == 1) {
            if (row % 3 == 2) {
                directions[0] = 1;
                directions[1] = 2;
                directions[2] = 4;
                directions[3] = 6;
                directions[4] = 8;
                directions[5] = 4;
                directions[6] = 13;
                directions[7] = 14;
                checks[0] = false;
                checks[1] = false;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = false;
                positionOfSquare = 3;

            } else if (row % 3 == 1) {
                directions[0] = 2;
                directions[1] = 2;
                directions[2] = 4;
                directions[3] = 8;
                directions[4] = 12;
                directions[5] = 4;
                directions[6] = 14;
                directions[7] = 8;
                checks[0] = false;
                checks[1] = true;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = true;
                positionOfSquare = 0;

            } else {
                directions[0] = 1;
                directions[1] = 1;
                directions[2] = 4;
                directions[3] = 6;
                directions[4] = 10;
                directions[5] = 4;
                directions[6] = 13;
                directions[7] = 6;
                checks[0] = false;
                checks[1] = true;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = true;
                positionOfSquare = 6;
            }

        } else {
            if (row % 3 == 2) {
                directions[0] = 1;
                directions[1] = 2;
                directions[2] = 3;
                directions[3] = 5;
                directions[4] = 7;
                directions[5] = 3;
                directions[6] = 15;
                directions[7] = 16;
                checks[0] = false;
                checks[1] = false;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = false;
                positionOfSquare = 5;

            } else if (row % 3 == 1) {
                directions[0] = 2;
                directions[1] = 2;
                directions[2] = 3;
                directions[3] = 7;
                directions[4] = 11;
                directions[5] = 3;
                directions[6] = 16;
                directions[7] = 7;
                checks[0] = false;
                checks[1] = true;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = true;
                positionOfSquare = 2;

            } else {
                directions[0] = 1;
                directions[1] = 1;
                directions[2] = 3;
                directions[3] = 5;
                directions[4] = 9;
                directions[5] = 3;
                directions[6] = 15;
                directions[7] = 5;
                checks[0] = false;
                checks[1] = true;
                checks[2] = false;
                checks[3] = false;
                checks[4] = false;
                checks[5] = true;
                checks[6] = false;
                checks[7] = true;
                positionOfSquare = 8;
            }
        }
        //initiate call
            callDetect(index, directions, checks);
    }

    void callDetect(int index, int[] directions, boolean[] checks) {
        int localSquare = positionOfSquare;
        for (int i = 0; i < 8; i++) {
            int tempVal = detectNum(index, directions[i], checks[i]);
            if (tempVal != 0) {
               // System.out.println(tempVal+" "+index+" "+i+" "+positionOfSquare);
                possibleNums[tempVal - 1] = 0;
                posSquares[positionOfSquare] = true;
            }
            positionOfSquare = localSquare;
        }
    }

    private int detectNum(int index, int direction, boolean checkTwo) {
        int multiplier = 1;
        if (checkTwo) {
            multiplier++;
        }

        switch (direction) {
            case 1:
                checkHorizontal(index - (9 * multiplier), true, false);
                positionOfSquare -= (3 * multiplier);
                boxCounter++;
                return (startingArray[index - (9 * multiplier)]);
            case 2:
                checkHorizontal(index + (9 * multiplier), true, false);
                positionOfSquare += (3 * multiplier);
                boxCounter++;
                return (startingArray[index + (9 * multiplier)]);
            case 3:
                checkVertical(index - multiplier, true, false);
                positionOfSquare -= multiplier;
                boxCounter++;
                return (startingArray[index - multiplier]);
            case 4:
                checkVertical(index + multiplier, true, false);
                positionOfSquare += multiplier;
                boxCounter++;
                return (startingArray[index + multiplier]);
            case 5:
                positionOfSquare -= ((3 * multiplier) + multiplier);
                return (startingArray[(index - (9 * multiplier)) - multiplier]);
            case 6:
                positionOfSquare -= ((3 * multiplier) - multiplier);
                return (startingArray[(index - (9 * multiplier)) + multiplier]);
            case 7:
                positionOfSquare += ((3 * multiplier) - multiplier);
                return (startingArray[(index + (9 * multiplier)) - multiplier]);
            case 8:
                positionOfSquare += ((3 * multiplier) + multiplier);
                return (startingArray[(index + (9 * multiplier)) + multiplier]);
            case 9:
                positionOfSquare -= ((3 * 2) + 1);
                return (startingArray[(index - (9 * 2)) - 1]);
            case 10:
                positionOfSquare -= ((3 * 2) - 1);
                return (startingArray[(index - (9 * 2)) + 1]);
            case 11:
                positionOfSquare += ((3 * 2) - 1);
                return (startingArray[(index + (9 * 2)) - 1]);
            case 12:
                positionOfSquare += ((3 * 2) + 1);
                return (startingArray[(index + (9 * 2)) + 1]);
            case 13:
                positionOfSquare -= ((3 * 1) - 2);
                return (startingArray[(index - (9 * 1)) + 2]);
            case 14:
                positionOfSquare += (3 * 1) + 2;
                return (startingArray[(index + (9 * 1)) + 2]);
            case 15:
                positionOfSquare -= (3 * 1) + 2;
                return (startingArray[(index - (9 * 1)) - 2]);
            default:
                positionOfSquare += (3 * 1) - 2;
                return (startingArray[(index + (9 * 1)) - 2]);
        }
    }

    private void checkVertical(int index, boolean isCheckingBox, boolean isLinear) {
        boolean notAtHigh = true, notAtLow = true;
        int indexH = index;
        int indexL = index;

        while (notAtHigh || notAtLow) {

            if (indexH > 80) {
                notAtHigh = false;
            } else {
                if (startingArray[indexH] != 0 && !isLinear) {
                    if (!isCheckingBox) {
                        possibleNums[startingArray[indexH] - 1] = 0;

                    } else {
                        checkBoxAdjacent[boxCounter][startingArray[indexH] - 1] = 0;
                    }

                } else if (isLinear) {
                    if (startingArray[indexH] != 0) {
                        checkLinearAdjacent[0][adjacentVertCounter][startingArray[indexH] - 1] = 0;
                    }
                    if (isInSameBox(true, indexH, index)) {
                        addToAdjacentLines(true, index, startingArray[indexH]);
                    }
                } else if (!isCheckingBox) {
                    checkHorizontal(indexH, false, true);
                    if (indexH != index) {
                        adjacentHorCounter++;
                    }
                }
                indexH += 9;

            }
            if (indexL < 0) {
                notAtLow = false;
            } else {
                if (startingArray[indexL] != 0 && !isLinear) {
                    if (!isCheckingBox) {
                        possibleNums[startingArray[indexL] - 1] = 0;
                    } else {
                        checkBoxAdjacent[boxCounter][startingArray[indexL] - 1] = 0;
                    }

                } else if (isLinear) {
                    if (startingArray[indexL] != 0) {
                        checkLinearAdjacent[0][adjacentVertCounter][startingArray[indexL] - 1] = 0;
                    }
                    if (isInSameBox(true, indexL, index)) {
                        addToAdjacentLines(true, index, startingArray[indexL]);
                    }
                } else if (!isCheckingBox) {
                    checkHorizontal(indexL, false, true);
                    if (indexL != index) {
                        adjacentHorCounter++;
                    }
                }
                indexL -= 9;

            }
        }
    }

    private void checkHorizontal(int index, boolean isCheckingBox, boolean isLinear) {
        boolean notAtHigh = true, notAtLow = true;
        int indexH = index;
        int indexL = index;

        while (notAtHigh || notAtLow) {

            if (((9 - (index + 1) % 9) < (indexH - index)) || ((9 - (index + 1) % 9) == 9)) {
                notAtHigh = false;
            } else {
                if (startingArray[indexH] != 0 && !isLinear) {
                    if (!isCheckingBox) {
                        possibleNums[startingArray[indexH] - 1] = 0;
                    } else {
                        checkBoxAdjacent[boxCounter][startingArray[indexH] - 1] = 0;
                    }

                } else if (isLinear) {
                    if (startingArray[indexH] != 0) {
                        checkLinearAdjacent[1][adjacentHorCounter][startingArray[indexH] - 1] = 0;
                    }
                    if (isInSameBox(false, indexH, index)) {
                        addToAdjacentLines(false, index, startingArray[indexH]);
                    }
                } else if (!isCheckingBox) {
                    checkVertical(indexH, false, true);
                    if (indexH != index) {
                        adjacentVertCounter++;
                    }
                }
                indexH++;

            }
            if (((index + 1) % 9) == (index - indexL)) {
                notAtLow = false;
            } else {
                if (startingArray[indexL] != 0 && !isLinear) {
                    if (!isCheckingBox) {
                        possibleNums[startingArray[indexL] - 1] = 0;
                    } else {
                        checkBoxAdjacent[boxCounter][startingArray[indexL] - 1] = 0;
                    }

                } else if (isLinear) {
                    if (startingArray[indexL] != 0) {
                        checkLinearAdjacent[1][adjacentHorCounter][startingArray[indexL] - 1] = 0;
                    }
                    if (isInSameBox(false, indexL, index)) {
                        addToAdjacentLines(false, index, startingArray[indexL]);
                    }
                } else if (!isCheckingBox) {
                    checkVertical(indexL, false, true);
                    if (indexL != index) {
                        adjacentVertCounter++;
                    }
                }
                indexL--;

            }
        }
    }

    private boolean isInSameBox(boolean isVertical, int initial, int ending) {
        if (isVertical) {
            return Math.abs(ending - initial) < 19 && (ending / 27 == initial / 27);
        } else {
            return (Math.abs(ending - initial) < 3 && (((ending % 9) / 3) == ((initial % 9) / 3)));
        }
    }

    private void addToAdjacentLines(boolean isVertical, int index, int num) {
        boolean currentSideRight = true, currentSideLeft = true;
        int counterRight = 0, counterLeft = 0;
        int indexH = index, indexL = index;
        if (isVertical) {
            while (currentSideRight && index < 81) {
                currentSideRight = false;
                if (isInSameBox(true, index, indexH) && index != indexH) {
                    currentSideRight = true;
                    counterRight++;
                    if (startingArray[indexH] == 0) {
                        checkLinearAdjacent[1][adjacentVertCounter + counterRight][num - 1] = 0;
                    }
                }
                indexH += 9;
            }
            while (currentSideLeft && index >= 0) {
                currentSideLeft = false;
                if (isInSameBox(true, index, indexL) && index != indexL) {
                    currentSideLeft = true;
                    counterLeft--;
                    if (startingArray[indexL] == 0) {
                        checkLinearAdjacent[1][adjacentVertCounter + counterLeft][num - 1] = 0;
                    }
                }
                indexL -= 9;
            }
        } else {
            while (currentSideRight && index < 81) {
                currentSideRight = false;
                if (isInSameBox(true, index, indexH) && index != indexH) {
                    currentSideRight = true;
                    counterRight++;
                    if (startingArray[indexH] == 0) {
                        checkLinearAdjacent[0][adjacentHorCounter + counterRight][num - 1] = 0;
                    }
                }
                indexH++;
            }
            while (currentSideLeft && index >= 0) {

                currentSideLeft = false;
                if (isInSameBox(true, index, indexL) && index != indexL) {
                    currentSideLeft = true;
                    counterLeft--;
                    if (startingArray[indexL] == 0) {
                        checkLinearAdjacent[0][adjacentHorCounter + counterLeft][num - 1] = 0;
                    }
                }
                indexL--;
            }
        }

    }

    private void checkAdjacentLines(int index) {
        //for checkBox
        int verifyNum = 0;
        for (int i = 0; i < 9; i++) {
            for (int t = 0; t < 4; t++) {
                if (checkBoxAdjacent[t][i] == 0) {
                    verifyNum++;
                }
            }
            if (verifyNum == checkFullSquares()) {
                for (int m = 0; m < 9; m++) {
                    if (m != i) {
                        possibleNums[m] = 0;
                    }
                }
            } else {
                verifyNum = 0;
            }
        }
        //for vertical and horizontal checkAdjacent
        for (int n = 0; n < 2; n++) {
            for (int t = 0; t < 9; t++) {
                for (int g = 0; g < 8; g++) {
                    if (checkLinearAdjacent[n][g][t] == 0) {
                        verifyNum++;
                    }
                }
                if (verifyNum == 8) {
                    for (int m = 0; m < 9; m++) {
                        if (m != t) {
                            possibleNums[m] = 0;
                        }
                    }
                } else {
                    verifyNum = 0;
                }
            }
        }

    }

    private int checkFullSquares() {
        int validAdjacentLines = 4;
        if (posSquares[0] == posSquares[1] && posSquares[0] == posSquares[2] && posSquares[0] != false) {
            validAdjacentLines--;
        }
        if (posSquares[3] == posSquares[4] && posSquares[3] == posSquares[5] && posSquares[3] != false) {
            validAdjacentLines--;
        }
        if (posSquares[6] == posSquares[7] && posSquares[6] == posSquares[8] && posSquares[6] != false) {
            validAdjacentLines--;
        }
        if (posSquares[0] == posSquares[3] && posSquares[0] == posSquares[6] && posSquares[0] != false) {
            validAdjacentLines--;
        }
        if (posSquares[1] == posSquares[4] && posSquares[1] == posSquares[7] && posSquares[1] != false) {
            validAdjacentLines--;
        }
        if (posSquares[2] == posSquares[5] && posSquares[2] == posSquares[8] && posSquares[2] != false) {
            validAdjacentLines--;
        }
        return validAdjacentLines;
    }

    final boolean updateGrid(final int index) {
        int tempNum = 0;
        int numZeros = 0;
        for (int i = 0; i < possibleNums.length; i++) {
            if (possibleNums[i] != 0) {
                tempNum = possibleNums[i];
            } else {
                numZeros++;
            }
        }
        if (tempNum != 0 && numZeros == 8) {
            startingArray[index] = tempNum;
            resetPossibleNums();
            return true;
        } else {
            return false;
        }
    }

    private boolean checkSolved() {
        int numFilled = 0;
        for (int i = 0; i < startingArray.length; i++) {
            if (startingArray[i] != 0) {
                numFilled++;
            }
        }
        return (numFilled == 81 || deadEnd());
    }

    private boolean deadEnd() {
        if (iterationNum == 1) {
            currentIterationArray = startingArray;
            return false;
        } else if (startingArray == currentIterationArray) {
            return true;
        } else {
            currentIterationArray = startingArray;
            return false;
        }
    }

    private void resetPossibleNums() {
        //reinitialize arrays
        for (int i = 0; i < possibleNums.length; i++) {
            possibleNums[i] = (i + 1);
            posSquares[i] = false;
        }
        for (int t = 0; t < 4; t++) {
            for (int i = 0; i < possibleNums.length; i++) {
                checkBoxAdjacent[t][i] = (i + 1);
            }
        }
        for (int n = 0; n < 2; n++) {
            for (int t = 0; t < 8; t++) {
                for (int i = 0; i < possibleNums.length; i++) {
                    checkLinearAdjacent[n][t][i] = (i + 1);
                }
            }
        }
        boxCounter = 0;
        adjacentHorCounter = 0;
        adjacentVertCounter = 0;
        positionOfSquare = 0;
    }

    private void printSolution() {
        for (int i = 0; i < startingArray.length; i++) {
            System.out.print(startingArray[i]);
            if ((i + 1) % 9 == 0) {
                System.out.println();
            }
        }

    }

}
