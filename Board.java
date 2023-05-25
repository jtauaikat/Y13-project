/**
 * by Joshua Toumu'a & Leo Riginelli
 *22/05/23
 *Implementing board class
 */

public class Board
{
    private int nameIntX;
    private int nameIntY;
    public Board(String name){
        nameIntY = Integer.parseInt(name);
        nameIntX = Integer.parseInt(name);
    }
    int getX(){
        while(nameIntX>=100){
            nameIntX-=100;
        }
        return nameIntX-1;
    }
    int getY(){
        System.out.println("before division: "+nameIntY);
        nameIntY/=100;
        System.out.println("after division: "+nameIntY);
        return nameIntY-1;
    }
}
