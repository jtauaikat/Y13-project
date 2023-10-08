/**
 * by Joshua Toumu'a & Leo Riginelli
 * 20/07/23
 * Adding menu systems
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
     
    public Cells(int importGridSize) {
        gridSize = importGridSize;
        isFlagged = false;
        isMine = false;
        isRevealed = false;
        icon = null;
        rollover = null;
    }

    public void getX(int forLoopX) {
        this.xCoordinate = forLoopX;
    }

    public void getY(int forLoopY) {
        this.yCoordinate = forLoopY;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine() {
        this.isMine = true;
    }

    public int getNeighbours() {
        return this.neighbourCount;
    }

    public void setNeighbours(int xMod, int yMod, Cells[][] board) {
        // Calculates the number of neighboring cells with mines
        for (int ySurroundCell = -1; ySurroundCell <= 1; ySurroundCell++) {
            for (int xSurroundCell = -1; xSurroundCell <= 1; xSurroundCell++) {
                if (yMod + ySurroundCell >= 0 && yMod + ySurroundCell < gridSize
                        && xMod + xSurroundCell >= 0 && xMod + xSurroundCell < gridSize && !isFlagged) {
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
