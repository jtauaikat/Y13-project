/**
 * by Joshua Toumu'a & Leo Riginelli
 * 12/10/23
 * Commenting
 */
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cells {
    private int xCoordinate;
    private int yCoordinate;
    private int neighbourCount; // Stores the number of neighboring cells with mines
    private int gridSize;
    private boolean isFlagged; // Indicates if the cell is flagged
    private boolean isMine; // Indicates if the cell contains a mine
    private boolean isRevealed; // Indicates if the cell has been revealed
    private String name;
    private ImageIcon icon;
    private ImageIcon rollover;
    
    //creates basic cell
    public Cells(int importGridSize) {
        gridSize = importGridSize;
        isFlagged = false;
        isMine = false;
        isRevealed = false;
        icon = null;
        rollover = null;
    }
    //returns X coordinate
    public void getX(int forLoopX) {
        this.xCoordinate = forLoopX;
    }
    //returns Y coordinate
    public void getY(int forLoopY) {
        this.yCoordinate = forLoopY;
    }
    //returns if is mine
    public boolean isMine() {
        return isMine;
    }
    //sets cell to be mine
    public void setMine() {
        this.isMine = true;
    }
    //returns number of neighbouring mines
    public int getNeighbours() {
        return this.neighbourCount;
    }

    public void setNeighbours(int xMod, int yMod, Cells[][] board) {
        // looks at 9x9 grid around cell
        for (int ySurroundCell = -1; ySurroundCell <= 1; ySurroundCell++) {
            for (int xSurroundCell = -1; xSurroundCell <= 1; xSurroundCell++) {
                //if within boundaries and not flagged
                if (yMod + ySurroundCell >= 0 && yMod + ySurroundCell < gridSize
                        && xMod + xSurroundCell >= 0 && xMod + xSurroundCell < gridSize && !isFlagged) {
                    //if the neighbour is a mine, adds 1 to neighbourCount
                    if (board[xMod + xSurroundCell][yMod + ySurroundCell].isMine()) {
                        neighbourCount++;
                    }
                }
            }
        }
    }

    public boolean getFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagState) {
        this.isFlagged = flagState;
    }

    public boolean getShown() {
        return isRevealed;
    }

    public void setShown(boolean revealState) {
        this.isRevealed = revealState;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    
    public void setIcon(ImageIcon iconSet) {
        this.icon = iconSet;
    }
    
    public ImageIcon getRollover(){
        return rollover;
    }
    public void setRollover(ImageIcon rolloverSet){
        this.rollover = rolloverSet;
    }
  
}
