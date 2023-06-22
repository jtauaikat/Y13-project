/**
 * by Joshua Toumu'a & Leo Riginelli
 * 09/06/23
 * Implementing Cells class
 */

public class Cells {
    private int xCoordinate;
    private int yCoordinate;
    private int neighbourCount; // Stores the number of neighboring cells with mines
    private boolean isFlagged; // Indicates if the cell is flagged
    private boolean isMine; // Indicates if the cell contains a mine
    private boolean isRevealed; // Indicates if the cell has been revealed
    private String name;

    public Cells() {
        isFlagged = false;
        isMine = false;
        isRevealed = false;
    }

    public void getX(int forLoopX) {
        this.xCoordinate = forLoopX;
    }

    public void getY(int forLoopY) {
        this.yCoordinate = forLoopY;
    }

    public void setMine() {
        this.isMine = true;
    }
    public boolean getMine() {
        return this.isMine;
    }

    public void setNeighbours(int xMod, int yMod, Cells[][] board) {
        // Calculates the number of neighboring cells with mines
        for (int ySurroundCell = -1; ySurroundCell <= 1; ySurroundCell++) {
            for (int xSurroundCell = -1; xSurroundCell <= 1; xSurroundCell++) {
                if (yMod + ySurroundCell >= 0 && yMod + ySurroundCell < MainLoop.getGridSize()
                        && xMod + xSurroundCell >= 0 && xMod + xSurroundCell < MainLoop.getGridSize() && !isFlagged) {
                    if (board[xMod + xSurroundCell][yMod + ySurroundCell].getMine()) {
                        neighbourCount++;
                    }
                }
            }
        }
    }
    public int getNeighbours(){
        return this.neighbourCount;
    }
    
        public boolean getFlagged (){
        return isFlagged;
    }
    public void setFlagged(boolean flagState){
        this.isFlagged = flagState;
    }

    public boolean getShown (){
        return isRevealed;
    }
    public void setShown(boolean revealState){
        this.isRevealed = revealState;
    }
}
