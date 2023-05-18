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
        
        Board.printRandBoard( board, gridSize, gridSize, mineCount, gridSize);
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

   

   
}
