/*
//guilty
 */
package sudokusolver;

/**
 *
 * @author bala_
 */
public class Sudokusolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     new Sudokusolver();
    }
      private int []startingArray=new int[81];
      private int possibleNums[]={1,2,3,4,5,6,7,8,9};
      boolean unsolved=true;
      int iterationNum=0;
      public Sudokusolver(){
          while (unsolved){
          for (int i=0; i<startingArray.length;i++){
              if (startingArray[i]==0)
                     checkPossibility(i);
          }
          iterationNum++;
          System.out.println("Iteration #"+iterationNum+" complete.");
          }
          
          System.out.println(iterationNum+" iterations of the guilty algorithm were required to solve the sudoku.");
          System.out.println("The solved sudoku is: ");
          printSolution();
         
      }
      private void checkPossibility(int index){
       //check all possible methods of possible number for given index      
                  checkBox(index);
                  if (!updateGrid(index))
                     checkHorizontal(index);
                  if (!updateGrid(index))
                     checkVertical(index);
                  if (!updateGrid(index))
                     checkAdjacentLines(index);
                  if (!updateGrid(index))
                     checkHiddenTwin(index);
                  
        //evaluate if number can be DEFINITELY determined and if so, update grid                                 
      }
       //directions: 1=up,2=down,3=left,4=right,5=diagUpLeft, 6-diagUpRight, 7=diagBottomLeft, 8=diagBottomRight,9=Up2Left, 
       //10=Up2Right, 11=Down2Left, 12=Down2Right, 13=UpRight2,14=DownRight2,15=UpLeft2,16=DownLeft2
      private void checkBox(int index){
          int row=(index/9)+1;
          int column=(index%9)+1;
          
          int [] directions=new int[8];
          boolean [] checks=new boolean[8];
          if (column%3==2){
              if (row%3==2){
                directions[0]=1; directions[1]=2; directions[2]=3; directions[3]=4; directions[4]=5; directions[5]=6; directions[6]=7; directions[7]=8;
                for (int i=0; i<checks.length;i++){
                    checks[i]=false;
                }
              }
              else if (row%3==1){
                  directions[0]=3; directions[1]=4; directions[2]=2; directions[3]=7; directions[4]=8; directions[5]=2; directions[6]=11; directions[7]=12;
                  checks[0]=false; checks[1]=false; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=false;
              }
              else{
                  directions[0]=3; directions[1]=4; directions[2]=1; directions[3]=5; directions[4]=6; directions[5]=1; directions[6]=9; directions[7]=10;
                   checks[0]=false; checks[1]=false; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=false;
              }
          }
          else if (column%3==1){
              if (row%3==2){
                  directions[0]=1; directions[1]=2; directions[2]=4; directions[3]=6; directions[4]=8; directions[5]=4; directions[6]=13; directions[7]=14;
                  checks[0]=false; checks[1]=false; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=false;
              }
              else if (row%3==1){
                  directions[0]=2; directions[1]=2; directions[2]=4; directions[3]=8; directions[4]=12; directions[5]=4; directions[6]=14; directions[7]=8;
                  checks[0]=false; checks[1]=true; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=true;
              }
              else{
                  directions[0]=1; directions[1]=1; directions[2]=4; directions[3]=6; directions[4]=10; directions[5]=4; directions[6]=13; directions[7]=6;
                  checks[0]=false; checks[1]=true; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=true;
              }
                  
          }
          else{
              if (row%3==2){
                  directions[0]=1; directions[1]=2; directions[2]=3; directions[3]=5; directions[4]=7; directions[5]=3; directions[6]=15; directions[7]=16;
                  checks[0]=false; checks[1]=false; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=false;
              }
              else if (row%3==1){
                  directions[0]=2; directions[1]=2; directions[2]=3; directions[3]=7; directions[4]=11; directions[5]=3; directions[6]=16; directions[7]=7;
                  checks[0]=false; checks[1]=true; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=true;
              }
              else{     
                  directions[0]=1; directions[1]=1; directions[2]=3; directions[3]=5; directions[4]=9; directions[5]=3; directions[6]=15; directions[7]=5;
                  checks[0]=false; checks[1]=true; checks[2]=false; checks[3]=false; checks[4]=false; checks[5]=true; checks[6]=false;checks[7]=true;
              }               
          }        
          //initiate call
          callDetect(index,directions,checks);
      }
     
      void callDetect(int index, int [] directions, boolean [] checks){
          for (int i=0; i<8;i++){
              int tempVal=detectNum(index, directions[i], checks[i]);
              if (tempVal!=0)
                  possibleNums[tempVal-1]=0;
          }
      }
      private int detectNum(int index, int direction, boolean checkTwo){
          int multiplier=1;
          if (checkTwo)
              multiplier++;
          
          switch (direction){
                      case 1:
                           return(startingArray[index-(9*multiplier)]);                 
                      case 2:
                           return(startingArray[index+(9*multiplier)]);
                      case 3:
                           return(startingArray[index-multiplier]);
                      case 4:
                           return(startingArray[index+multiplier]);                 
                      case 5:
                           return(startingArray[(index-(9*multiplier))-multiplier]);
                      case 6:
                           return(startingArray[(index-(9*multiplier))+multiplier]);
                      case 7:
                           return(startingArray[(index+(9*multiplier))-multiplier]);
                      case 8:
                          return(startingArray[(index+(9*multiplier))+multiplier]);      
                      case 9:
                          return(startingArray[(index-(9*2))-1]);      
                      case 10:
                          return(startingArray[(index-(9*2))+1]);      
                      case 11:
                          return(startingArray[(index+(9*2))-1]);      
                      case 12: 
                          return(startingArray[(index+(9*2))+1]);      
                      case 13:
                          return(startingArray[(index-(9*1))+2]);      
                      case 14:
                          return(startingArray[(index+(9*1))+2]);      
                      case 15:
                          return(startingArray[(index-(9*1))-2]);      
                      default:
                          return(startingArray[(index+(9*1))-2]);      
          }  
      }
      private void checkVertical(int index){
          boolean notAtHigh=true, notAtLow=true;
          int indexH=index; int indexL=index;
          
          while (notAtHigh || notAtLow){
             
              if (indexH>80)
                  notAtHigh=false;
              else{
                  if (possibleNums[startingArray[indexH]-1]!=0)
                      possibleNums[startingArray[indexH]-1]=0;
                  indexH+=9;
              }
              if (indexL<0)
                  notAtLow=false;
              else{
                  if (possibleNums[startingArray[indexL]-1]!=0)
                      possibleNums[startingArray[indexL]-1]=0;
                  indexL-=9;
              }                 
          }
      }
      private void checkHorizontal(int index){
         boolean notAtHigh=true, notAtLow=true;
          int indexH=index; int indexL=index;
          
          while (notAtHigh || notAtLow){
             
              if (((9-(index+1)%9)<(indexH-index)) || ((9-(index+1)%9)==9))
                  notAtHigh=false;
              else{
                  
                  if (possibleNums[startingArray[indexH]-1]!=0)
                      possibleNums[startingArray[indexH]-1]=0;
                  indexH++;
              }
              if (((index+1)%9)==(index-indexL))
                  notAtLow=false;
              else{ 
                  
                  if (possibleNums[startingArray[indexL]-1]!=0)
                      possibleNums[startingArray[indexL]-1]=0;  
                  indexL--;
              }                 
          }
      }
      
      private void checkAdjacentLines(int index){
          
      }
      private void checkHiddenTwin(int index){
          
      }
      final boolean updateGrid(final int index)
      {
          int tempNum=0;
          int numZeros=0;
          for (int i=0; i<possibleNums.length;i++){
              if (possibleNums[i]!=0)
                 tempNum=possibleNums[i];
              else
                  numZeros++;
          }
          if (tempNum!=0&&numZeros==8){
            startingArray[index]=tempNum;
            //reinitialize array
            for (int i=0; i<possibleNums.length;i++){
                possibleNums[i]=(i+1);
            }
            return true;
          }
          else return false;
      }
      
      private void printSolution(){
          for (int i=0; i<startingArray.length; i++){
              System.out.print(startingArray[i]);
              if ((i+1)%9==0){
                  System.out.println ();
              }             
          }
      }
      
      
}
