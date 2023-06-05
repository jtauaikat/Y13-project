
/**
 * Write a description of class Cells here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cells
{
    private int xCoordinate;
    private int yCoordinate;
    private boolean isFlagged;
    private boolean isMine;
    private boolean isRevealed;

    public Cells()
    {
        isFlagged = false;
        isMine = false;
        isRevealed = false;
    }

    public void getX(int forLoopX){
        this.xCoordinate=forLoopX;
    }

    public void getY(int forLoopY){
        this.yCoordinate=forLoopY;
    }

    public void setMine(){
            this.isMine = true;
    }

    public boolean checkMine(){
        return this.isMine;
    }

    public void arrayNeighbours(int board[][], int gridSize){
        //  future = new int[rows][columns];

        for (int ySurroundCell= -1; ySurroundCell<= 1; ySurroundCell++)
        {
            for (int xSurroundCell= -1; xSurroundCell<= 1; xSurroundCell++)
            {
                if (yMod+ySurroundCell>=0 && yMod+ySurroundCell<gridSize && xMod+xSurroundCell>=0 && xMod+xSurroundCell<gridSize){
                    if(board[xMod+xSurroundCell][yMod+ySurroundCell]==9){
                        board[xMod][yMod]++;
                    }
                }
            }
        }
    }


    public static boolean stateFlagged (){
        // if ( == ) {
        // return true;
        // }
        // else {
        // return false;
        // }
        return false;
    }

    public boolean stateShown (){
        // if ( == ) {
        // return true;
        // }
        // else {
        // return false;
        // }
        return false;
    }

    // int getX(){
    // }
    // int getY(){
    // }
}