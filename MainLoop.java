/**
 * by Joshua Toumu'a & Leo Riginelli
 * 09/06/23
 * Implementing object-based code
 */
//some necessary imports
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

public class MainLoop extends JFrame implements ActionListener, MouseListener {
    static int gridSize = 10;//creates variable for overall gridsize. allowed to change
    JButton gridButton; //defines the JButton

    int buttonSize = 50; //several other constants
    int mineCount = 12;

    public Board mainBoard = new Board(); //creates a board using board class

    public MainLoop() {
        Integer buttonLabel = 0;
        int buttonYPosition = 100;
        int buttonXPosition = 100;

        // Creating grid of buttons
        for (int buttonYCount = 0; buttonYCount < gridSize; buttonYCount++) {
            for (int buttonXCount = 0; buttonXCount < gridSize; buttonXCount++) {
                //code to generate new JButton
                gridButton = new JButton();
                gridButton.setText(buttonLabel.toString());
                gridButton.setBounds(buttonXPosition, buttonYPosition, buttonSize, buttonSize);
                gridButton.setFocusable(false);
                gridButton.addActionListener(this);

                buttonLabel++; //increases by 1 when moving to the left

                this.add(gridButton); //draws button
                buttonXPosition += buttonSize; //moves X Coord to the right
            }
            buttonLabel -= gridSize; //returns the labels position to the beginning
            buttonYPosition += buttonSize; //moves Y Coord downwards
            buttonXPosition = 100; //returns X Coord to origin point

            buttonLabel += 100; //increases by 100 when moving downwards
        }

        // Setting up JFrame
        this.getContentPane().setPreferredSize(new Dimension(1050, 1050));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.toFront(); // Brings the panel to front of desktop
        this.setVisible(true);

        // Adding MouseListener to the buttons
        for (Component component : this.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addMouseListener(this);
            }
        }
    }

    //a static variable for the board to use to reference the grid size at any time
    public static int getGridSize() {
        return gridSize;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button click
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            // Right-click detected
            JButton button = (JButton) e.getSource();
            String name = button.getActionCommand();
            int buttonCoordY = Integer.parseInt(name) / 100; //finds the X coord within the ones and tens place value
            int buttonCoordX = Integer.parseInt(name) % 100; //finds the Y coord within the hundreds place value

            // Perform actions for right-click
            if(!mainBoard.cellGrid[buttonCoordX][buttonCoordY].getShown()){
                if(mainBoard.cellGrid[buttonCoordX][buttonCoordY].getFlagged()){
                    mainBoard.cellGrid[buttonCoordX][buttonCoordY].setFlagged(false); //if right clicked, button is flagged
                }else{
                    mainBoard.cellGrid[buttonCoordX][buttonCoordY].setFlagged(true);
                }
            }
            mainBoard.testPrint();
        }else{
            // Right-click detected
            JButton button = (JButton) e.getSource();
            String name = button.getActionCommand();
            int buttonCoordY = Integer.parseInt(name) / 100;
            int buttonCoordX = Integer.parseInt(name) % 100;

            // Perform actions for right-click
            if(!mainBoard.cellGrid[buttonCoordX][buttonCoordY].getFlagged()){
                mainBoard.cellGrid[buttonCoordX][buttonCoordY].setShown(true);
            }
            mainBoard.testPrint();  
        }
    }

    // Other methods from MouseListener interface
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
