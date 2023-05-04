/**
 * by Joshua Toumu'a & Leo Riginelli
 *05/05/23
 *Generating button grid
 */


import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JButton;   

public class MainLoop extends JFrame implements ActionListener{
   
    JButton gridButton; 
    int gridSize = 10;
    int buttonSize = 10;
    public MainLoop(){
        Integer buttonLabel = 1;
        int buttonYPosition;
        int buttonXPosition;
        for(int buttonYCount = 0; buttonYCount<gridSize; buttonYCount++){
            buttonLabel += 100;
            
            for(int buttonXCount=0; buttonXCount<gridSize; buttonXCount++){
                gridButton = new JButton();
                gridButton.setText(buttonLabel.toString());
                buttonLabel++;
                gridButton.setBounds(buttonXPosition,buttonYPosition,buttonSize, buttonSize);
            }
        }
        this.getContentPane().setPreferredSize(new Dimension(1050,1050));  
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);    

        this.pack();
        this.toFront();  // Not too sure what this does, commenting out makes no apparent difference
        this.setVisible(true);
    }
    
    
    
   
    
}
