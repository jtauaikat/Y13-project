/**
 * by Joshua Toumu'a & Leo Riginelli
 *06/06/23
 *Implementing board class
 */

import java.util.Random;

public class Board
{
    Random rand = new Random();
    int maxMines = 9;
    int gridSize;
    
    public Cells[][] cellGrid = new Cells[gridSize][gridSize];

    public Board(){
        
    }
    
    public Board(int gridSize){
        Cells[][] cellGrid;
        cellGrid = new Cells[gridSize][gridSize];
        this.gridSize = gridSize;
    }

    void boardFirstGen(){
        for(int yMod= 0; yMod<gridSize; yMod++){
            for(int xMod = 0; xMod<gridSize; xMod++){
                cellGrid[xMod][yMod] = new Cells();
                
            }
            System.out.println();
        }
    }
    
    void boardSecondGen(){
        for(int yMod= 0; yMod<gridSize; yMod++){
            for(int xMod = 0; xMod<gridSize; xMod++){
                cellGrid[xMod][yMod].setNeighbors(xMod,yMod,cellGrid);
            }
            System.out.println();
        }
    }

    void mineAsign(){
        int mineGenX;
        int mineGenY;
        int mineGenCount = 0;
        while(mineGenCount<maxMines){
            mineGenX = rand.nextInt(gridSize);
            mineGenY = rand.nextInt(gridSize);
            if(!cellGrid[mineGenX][mineGenY].checkMine()){
                cellGrid[mineGenX][mineGenY].setMine();
                mineGenCount++;
            }
        }
    }

}
