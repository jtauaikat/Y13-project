/**
 * by Joshua Toumu'a & Leo Riginelli
 *09/06/23
 *Implementing board class
 */

public class Cells
{
    private int xCoordinate;
    private int yCoordinate;
    private int neighbourCount;
    private boolean isFlagged;
    private boolean isMine;
    private boolean isRevealed;
    private String name;
    
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

    public boolean getMine(){
        return this.isMine;
    }

    public void setNeighbours(int xMod, int yMod, Cells[][] board){
        //  future = new int[rows][columns];

        for (int ySurroundCell= -1; ySurroundCell<= 1; ySurroundCell++)
        {
            for (int xSurroundCell= -1; xSurroundCell<= 1; xSurroundCell++)
            {
                if (yMod+ySurroundCell>=0 && yMod+ySurroundCell<MainLoop.getGridSize() && xMod+xSurroundCell>=0 && xMod+xSurroundCell<MainLoop.getGridSize() && !isFlagged){
                    if(board[xMod+xSurroundCell][yMod+ySurroundCell].getMine()){
                        neighbourCount++;
                    }
                }
            }
        }
    }
    
    public int getNeighbours(){
        return this.neighbourCount;
    }

    public static boolean stateFlagged (){
        return false;
    }

    public boolean stateShown (){
        return false;
    }
}