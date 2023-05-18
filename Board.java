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
import java.util.Random;
import java.util.concurrent.TimeUnit;
    
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board
{
        int board[][];
 
    
    board = new int[rows][columns];
    
    Random rand = new Random();

 public Board(){
     
    }
    
    public void printRandBoard (int board[][], int rows, int columns, int mineCount, int gridSize){
        int cellGenX;
        int cellGenY;
        int mineGen = 0;
        while(mineGen<mineCount){
            cellGenX = rand.nextInt(gridSize);
            cellGenY = rand.nextInt(gridSize);
            if(board[cellGenY][cellGenX] != 1){
                board[cellGenY][cellGenX] = 9;
                mineGen++;
            }
        }

        for(int yModifier= 0; yModifier<columns; yModifier++){
            for(int xModifier = 0; xModifier<rows; xModifier++){
                //board[yModifier][xModifier] = (rand.nextInt(2));
                wrapPrint(board [yModifier] [xModifier] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
     public static void wrapPrint(String output) {    
        for (int i = 0; i<output.length(); i++) {
            char c = output.charAt(i);
            System.out.print(c);   
        }
    }
}
