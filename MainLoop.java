/**
 * by Joshua Toumu'a & Leo Riginelli
 *18/05/23
 *Generating button grid
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
    int board[][];
    Random rand = new Random();
    int columns = 10;
    int rows = 10;

    JButton gridButton; 
    int gridSize = 10;
    int buttonSize = 30;
    int mineCount = 12;
    public MainLoop(){
        Integer buttonLabel = 0;
        int buttonYPosition = 100;
        int buttonXPosition = 100;
        board = new int[rows][columns];
        printRandBoard( board, gridSize, gridSize);
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

    public void actionPerformed(ActionEvent e){
        String name = e.getActionCommand();
        System.out.println(name);
    }

    void printRandBoard (int board[][], int rows, int columns){
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

    public static void wrapPrint(String output) {    
        for (int i = 0; i<output.length(); i++) {
            char c = output.charAt(i);
            System.out.print(c);   
        }
    }
}
