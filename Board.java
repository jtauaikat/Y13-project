/**
 * by Joshua Toumu'a & Leo Riginelli
 *09/06/23
 *Implementing board class
 */

import java.util.Random;

public class Board
{
    Random rand = new Random();
    int maxMines = 9;
    int gridSize;
    public static Cells[][] cellGrid;
    public Board(){
        gridSize = MainLoop.getGridSize();
        cellGrid = new Cells[gridSize][gridSize];
        boardFirstGen();
        mineAsign();
        boardSecondGen();
        testPrint();
    }

    void boardFirstGen(){
        Integer cellName = 0;
        for(int yMod= 0; yMod<gridSize; yMod++){
            for(int xMod = 0; xMod<gridSize; xMod++){
                cellName++;
                cellGrid[xMod][yMod] = new Cells();
            }
        }
    }

    void boardSecondGen(){
        for(int yMod= 0; yMod<gridSize; yMod++){
            for(int xMod = 0; xMod<gridSize; xMod++){
                cellGrid[xMod][yMod].setNeighbours(xMod, yMod, cellGrid);
            }
            System.out.println();
        }
    }

    void testPrint(){    
        for(int yModifier= 0; yModifier<gridSize; yModifier++){
            for(int xModifier = 0; xModifier<gridSize; xModifier++){
                if(cellGrid[xModifier][yModifier].getMine()){
                    System.out.print("9 ");
                }else if (cellGrid[xModifier][yModifier].getFlagged()){
                    System.out.print("F ");
                }else if (cellGrid[xModifier][yModifier].getShown()){
                    System.out.print("S ");
                }else{
                    System.out.print(cellGrid[xModifier][yModifier].getNeighbours()+" ");
                }
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
            if(!cellGrid[mineGenX][mineGenY].getMine()){
                cellGrid[mineGenX][mineGenY].setMine();
                mineGenCount++;
            }
        }
    }

}
