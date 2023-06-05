/**
 * by Joshua Toumu'a & Leo Riginelli
 *06/06/23
 *Implementing object-based code
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
import java.util.concurrent.TimeUnit;

public class MainLoop extends JFrame implements ActionListener{
    
    JButton gridButton; 
    int gridSize = 10;
    int buttonSize = 30;
    int mineCount = 12;

    public int[][] board = new int[gridSize][gridSize];
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
                if(board[buttonXCount][buttonYCount] == 9){
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
            if(board[cellGenX][cellGenY] != 1){
                board[cellGenX][cellGenY] = 9;
                mineGen++;
            }
        }

        
       // Cells classObj = new Cells();
        //classObj.arrayNeighbours(board, gridSize);
        Cells.arrayNeighbours(board, gridSize);
        
        
        wrapPrint();

    }

    public void wrapPrint() {    
        for(int yModifier= 0; yModifier<gridSize; yModifier++){
            for(int xModifier = 0; xModifier<gridSize; xModifier++){
                System.out.print(board [xModifier] [yModifier] + "  ");
            }
            System.out.println();
        }
    }

    public void actionPerformed(ActionEvent e){
        System.out.print('\u000c');
        String name = e.getActionCommand();

        Board getInt = new Board(name);
        System.out.println(name);
        board[getInt.getX()][getInt.getY()] = 10;
        System.out.println("X: "+getInt.getX()+ " Y: "+getInt.getY());
        wrapPrint();
    }

}
