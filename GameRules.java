
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
    
    void arrayNeighbours(int board[][], int rows, int columns){
        //  future = new int[rows][columns];

        for (int y= 0; y< rows; y++)
        {
            for (int x= 0; x< columns; x++)
            {

                int aliveNeighbours = 0;
                // alive neigbhours is cells which are alive near a selected cell 
                //adds all living cells to total
                for (int yModifier= -1; yModifier<= 1; yModifier++)
                    for (int xModifier = -1; xModifier <= 1; xModifier++)
                        if ((y+yModifier>=0 && y+yModifier<rows) && (x+xModifier>=0 && x+xModifier<columns)){

                            aliveNeighbours += board[y+ yModifier][x+ xModifier];
                        }
                //sub self form total

            }
        }
    }

}
