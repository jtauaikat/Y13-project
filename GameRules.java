
/**
 * Write a description of class GameRules here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameRules
{
    public GameRules(){
     
    }
    
    void arrayNeighbours(int board[][], int gridSize){
        //  future = new int[rows][columns];

        for(int yMod= 0; yMod<gridSize; yMod++){
            for(int xMod = 0; xMod<gridSize; xMod++){
                if(board[xMod][yMod]!=9){
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
            }
            System.out.println();
        }
        
        
    }
    
    

}
