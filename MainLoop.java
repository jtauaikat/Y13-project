/**
 * by Joshua Toumu'a & Leo Riginelli
 *19/06/23
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
    static int gridSize = 10;
    int buttonSize = 30;
    int mineCount = 12;
    
    public Board mainBoard = new Board();
    public MainLoop(){
        Integer buttonLabel = 0;
        int buttonYPosition = 100;
        int buttonXPosition = 100;
        
        for(int buttonYCount = 0; buttonYCount<gridSize; buttonYCount++){
            for(int buttonXCount=0; buttonXCount<gridSize; buttonXCount++){
                
                gridButton = new JButton();
                gridButton.setText(buttonLabel.toString());
                gridButton.setBounds(buttonXPosition,buttonYPosition,buttonSize, buttonSize);
                gridButton.setFocusable(false);
                gridButton.addActionListener(this);
                
                buttonLabel++;
                
                this.add(gridButton);
                buttonXPosition += buttonSize;
            }
            buttonLabel -= gridSize;
            buttonYPosition += buttonSize;
            buttonXPosition = 100;
            
            buttonLabel+=100;
        }
        this.getContentPane().setPreferredSize(new Dimension(1050,1050));  
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);    

        this.pack();
        this.toFront();  // Not too sure what this does, commenting out makes no apparent difference
        this.setVisible(true);
    }
    
    public static int getGridSize(){
        return gridSize;
    }

    public void actionPerformed(ActionEvent e){
        System.out.print('\u000c');
        String name = e.getActionCommand();
        
        int buttonCoordX = Integer.parseInt(name)/100;
        int buttonCoordY = Integer.parseInt(name)%100;
        
        mainBoard.cellGrid[buttonCoordX][buttonCoordY].stateFlagged();
    }

}
