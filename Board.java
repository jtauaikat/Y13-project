/**
 * by Joshua Toumu'a & Leo Riginelli
 * 29/06/23
 * Implementing board class
 */

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.Image;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.awt.Image;
import javax.swing.JButton;
import java.util.concurrent.TimeUnit;

public class Board {
    Random rand = new Random();
    int maxMines = 9; // Represents the maximum number of mines
    int gridSize; // Represents the size of the grid & retrieves the grid size from the MainLoop class
    public Cells[][] cellGrid; // Represents the grid of cells
    
    public Board(int mineCount, int importGridSize) {
        gridSize = importGridSize;
        maxMines = mineCount;
        cellGrid = new Cells[gridSize][gridSize]; // Initializes the cell grid
        boardFirstGen(); // Generates the initial cell grid
        mineAsign(); // Assigns mines to the cells
        boardSecondGen(); // Generates the neighbors for each cell
        testPrint(); // Prints the grid for testing purposes
        maxMines = mineCount;
    }
    
    void boardFirstGen() {
        for (int yMod = 0; yMod < gridSize; yMod++) {
            for (int xMod = 0; xMod < gridSize; xMod++) {
                cellGrid[xMod][yMod] = new Cells(gridSize); // Creates a new cell and adds it to the grid
            }
        }
    }
    
    void boardSecondGen() {
        for (int yMod = 0; yMod < gridSize; yMod++) {
            for (int xMod = 0; xMod < gridSize; xMod++) {
                cellGrid[xMod][yMod].setNeighbours(xMod, yMod, cellGrid); // Sets the neighbors for each cell in the grid
            }
        }
    }
    
    void testPrint() {
        //System.out.println('\u000c');
        for (int yModifier = 0; yModifier < gridSize; yModifier++) {
            for (int xModifier = 0; xModifier < gridSize; xModifier++) {
                if(cellGrid[xModifier][yModifier].getShown()){
                    System.out.print("S ");
                }else if (cellGrid[xModifier][yModifier].getFlagged()){
                    System.out.print("F ");
                }else if (cellGrid[xModifier][yModifier].isMine()){
                    System.out.print("M ");
                }else{
                    System.out.print(cellGrid[xModifier][yModifier].getNeighbours()+" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    void mineAsign() {
        int mineGenX;
        int mineGenY;
        int mineGenCount = 0;
        while (mineGenCount < maxMines) {
            mineGenX = rand.nextInt(gridSize); // Generates a random X coordinate for a mine
            mineGenY = rand.nextInt(gridSize); // Generates a random Y coordinate for a mine
            if (!cellGrid[mineGenX][mineGenY].isMine()) {
                cellGrid[mineGenX][mineGenY].setMine(); // Sets the cell as containing a mine
                mineGenCount++;
            }
        }
    }
    
}
