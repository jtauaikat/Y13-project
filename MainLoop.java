/**
 * by Joshua Toumu'a & Leo Riginelli
 *22/05/23
 *Implementing board class
 */
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

public class MainLoop extends JFrame implements ActionListener{
    Random rand = new Random();
    int columns = 10;
    int rows = 10;

    public int[][] board = new int[rows][columns];

    JButton gridButton; 
    int gridSize = 10;
    int buttonSize = 30;
    int mineCount = 12;
    public MainLoop(){
        Integer buttonLabel = 0;
        int buttonYPosition = 100;
        int buttonXPosition = 100;
        
        randBoardGen();

        for(int buttonYCount = 0; buttonYCount<gridSize; buttonYCount++){
            buttonLabel += 100;

            for(int buttonXCount=0; buttonXCount<gridSize; buttonXCount++){
                buttonLabel++;
                gridButton = new JButton();
                gridButton.setText(buttonLabel.toString());
                gridButton.setBounds(buttonXPosition,buttonYPosition,buttonSize, buttonSize);
                gridButton.setFocusable(false);
                gridButton.addActionListener(this);
                if(board[buttonYCount][buttonXCount] == 9){
                    gridButton.setIcon(new ImageIcon("blueRect.png"));
                }

                this.add(gridButton);
                buttonXPosition += buttonSize;
            }
            buttonLabel -= gridSize;
            buttonYPosition += buttonSize;
            buttonXPosition = 100;
        }
        this.getContentPane().setPreferredSize(new Dimension(1050,1050));  
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);    

        this.pack();
        this.toFront();  // Not too sure what this does, commenting out makes no apparent difference
        this.setVisible(true);
    }

    void randBoardGen (){
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
    }

    void wrapPrint() {    
        for(int yModifier= 0; yModifier<columns; yModifier++){
            for(int xModifier = 0; xModifier<rows; xModifier++){
                System.out.print(board [yModifier] [xModifier] + "  ");
            }
            System.out.println();
        }
    }

    public void actionPerformed(ActionEvent e){
        String name = e.getActionCommand();
        Board getInt = new Board(name);
        System.out.println(name);
        board[getInt.getY()][getInt.getX()] = 10;
        wrapPrint();
    }


}
