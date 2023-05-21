/**
 * by Joshua Toumu'a & Leo Riginelli
 *22/05/23
 *Implementing board class
 */

public class Board
{
    private int nameInt;
    public Board(){
        this.nameInt  = 0;
    }
    public Board(String name){
        this.nameInt = Integer.parseInt(name);
    }

    int getX(){
        while(this.nameInt>=100){
            this.nameInt-=100;
        }
        return this.nameInt;
    }

    int getY(){
        this.nameInt/=100;
        return this.nameInt;
    }
}
