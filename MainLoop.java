/**
 * by Joshua Toumu'a & Leo Riginelli
 * 29/06/23
 * Implementing Sprites and repaint() function
 */
//some necessary imports
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.awt.Image;
import java.util.concurrent.TimeUnit;
import javax.swing.border.Border;

public class MainLoop extends JFrame implements ActionListener, MouseListener {
    static int gridSize = 10;//creates variable for overall gridsize. allowed to change
    JButton gridButton; //defines the JButton

    int buttonSize = 30; //constant for the visual size of the button
    int mineCount = 12; // amount of mines that spawn

    public Board mainBoard = new Board(); //creates a board using board class and populates board upon opening program

    ImageIcon flagIcon = new ImageIcon("flag.png");
    ImageIcon mineIcon = new ImageIcon("mine.png"); // Variable to store the ImageIcon for mine cells
    ImageIcon icon;
    ImageIcon rolloverIcon;
    public MainLoop() {
        Integer buttonLabel = 0; //Integer type for number-based button naming system

        //places top left button 100px down and 100px right
        int buttonYPosition = 100;
        int buttonXPosition = 100;
        // Creates grid of buttons
        for (int buttonYCount = 0; buttonYCount < gridSize; buttonYCount++) {
            for (int buttonXCount = 0; buttonXCount < gridSize; buttonXCount++) {

                //code to generate new JButton
                gridButton = new JButton();
                gridButton.setText(buttonLabel.toString()); //takes Integer and sets gridbutton name to Integer
                gridButton.setBounds(buttonXPosition, buttonYPosition, buttonSize, buttonSize);//sets button size and position
                gridButton.setFocusable(false);
                gridButton.addActionListener(this);

                buttonLabel++; //increases by 1 when moving to the left

                this.add(gridButton); //draws button
                buttonXPosition += buttonSize; //moves X Coord to the right

                gridButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                int mineCount = mainBoard.cellGrid[buttonXCount][buttonYCount].getNeighbours(); //returns the amount of neighbours for an integer
                boolean isFlagged = mainBoard.cellGrid[buttonXCount][buttonYCount].getFlagged();

                if (mainBoard.cellGrid[buttonXCount][buttonYCount].isMine()) {
                    icon = mineIcon;
                } else {
                    switch (mineCount) {
                            //each case allows for a different image to be used for each button
                        case 0:
                            icon = new ImageIcon("number_0.png");
                            break;
                        case 1:
                            icon = new ImageIcon("number_1.png");
                            rolloverIcon = new ImageIcon("number_1H.png");

                            break;
                        case 2:
                            icon = new ImageIcon("number_2.png");
                            rolloverIcon = new ImageIcon("number_2H.png");
                            break;
                        case 3:
                            icon = new ImageIcon("number_3.png");
                            rolloverIcon = new ImageIcon("number_3H.png");
                            break;

                        case 4:
                            icon = new ImageIcon("number_4.png");
                            rolloverIcon = new ImageIcon("number_4H.png");
                            break;
                        case 5:
                            icon = new ImageIcon("number_5.png");
                            rolloverIcon = new ImageIcon("number_5H.png");
                            break;
                        case 6:
                            icon = new ImageIcon("number_6.png");
                            rolloverIcon = new ImageIcon("number_6H.png");
                            break;
                        case 7:
                            icon = new ImageIcon("number_7.png");
                            rolloverIcon = new ImageIcon("number_7H.png");
                            break;
                        case 8:
                            icon = new ImageIcon("number_8.png");
                            rolloverIcon = new ImageIcon("number_8H.png");
                            break;

                        default:
                            icon = new ImageIcon("mine.png");
                            break;
                    }   
                }
                //stashes the reveal icon in cell variable
                mainBoard.cellGrid[buttonXCount][buttonYCount].setIcon(icon);
                mainBoard.cellGrid[buttonXCount][buttonYCount].setRollover(rolloverIcon);

                //resets icon to hidden cell Icon
                icon = new ImageIcon("blueRect.png");
                rolloverIcon = new ImageIcon("lightBlueRect.png");

                gridButton.setIcon(icon); //sets Icon according to switch statement
                gridButton.setRolloverIcon(rolloverIcon);
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
    public void mouseClicked(MouseEvent e) {
        // detects click
        JButton button = (JButton) e.getSource(); //finds the button that is clicked upon
        String name = button.getActionCommand(); //finds the name of the clicked button
        int buttonCoordY = Integer.parseInt(name) / 100; //finds the X coord within the ones and tens place value of the button's name
        int buttonCoordX = Integer.parseInt(name) % 100; //finds the Y coord within the hundreds place value of the button's name
        ImageIcon icon;
        if (e.getButton() == MouseEvent.BUTTON3) {
            //if the button click is a right click:
            if(!mainBoard.cellGrid[buttonCoordX][buttonCoordY].getShown()){
                //if the button is not shown already
                if(mainBoard.cellGrid[buttonCoordX][buttonCoordY].getFlagged()){
                    mainBoard.cellGrid[buttonCoordX][buttonCoordY].setFlagged(false); //button is flagged if unflagged
                    icon = new ImageIcon("blueRect.png");
                    rolloverIcon = new ImageIcon("lightBlueRect.png");
                    button.setIcon(icon);
                    button.setRolloverIcon(rolloverIcon);
                    button.setRolloverEnabled(true);
                }else{
                    mainBoard.cellGrid[buttonCoordX][buttonCoordY].setFlagged(true); //button is unflagged if flagged
                    button.setIcon(flagIcon);
                    button.setRolloverEnabled(false);
                }
            }

        }else if(e.getButton()== MouseEvent.BUTTON1){
            // if the button click is a left click:
            if(!mainBoard.cellGrid[buttonCoordX][buttonCoordY].getFlagged()){
                //reveals button if not flagged
                mainBoard.cellGrid[buttonCoordX][buttonCoordY].setShown(true);
                button.setIcon(mainBoard.cellGrid[buttonCoordX][buttonCoordY].getIcon());
                button.setRolloverIcon(mainBoard.cellGrid[buttonCoordX][buttonCoordY].getRollover());
            }
        }
        mainBoard.testPrint(); //prints out board in console for testing
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String name = button.getActionCommand();
        int buttonCoordY = Integer.parseInt(name) / 100;
        int buttonCoordX = Integer.parseInt(name) % 100;

        if (mainBoard.cellGrid[buttonCoordX][buttonCoordY].getNeighbours() == 0 && !mainBoard.cellGrid[buttonCoordX][buttonCoordY].getShown()) {
            // Zero cell clicked, reveal neighboring cells recursively
            revealZeroNeighbors(buttonCoordX, buttonCoordY);
            // for (int buttonYCount = 0; buttonYCount < gridSize; buttonYCount++) {
                // for (int buttonXCount = 0; buttonXCount < gridSize; buttonXCount++) {
                    // if(mainBoard.cellGrid[buttonCoordX][buttonCoordY].getShown()){
                        // button.setIcon(mainBoard.cellGrid[buttonCoordX][buttonCoordY].getIcon());
                        // button.setRolloverIcon(mainBoard.cellGrid[buttonCoordX][buttonCoordY].getRollover());
                        // repaint();
                    // }
                // }
            // }
        }
        mainBoard.testPrint();
        repaint();
    }

    private void revealZeroNeighbors(int x, int y) {
        if (x < 0 || x >= gridSize || y < 0 || y >= gridSize) {
            // if cell is out of bounds, returns
            return;
        }

        Cells cell = mainBoard.cellGrid[x][y];

        if (cell.getShown()) {
            // Cell already revealed
            return;
        }

        cell.setShown(true);

        if (cell.getNeighbours() == 0) {
            // Recursively reveal neighboring cells
            revealZeroNeighbors(x - 1, y - 1);
            revealZeroNeighbors(x - 1, y);
            revealZeroNeighbors(x - 1, y + 1);
            revealZeroNeighbors(x, y - 1);
            revealZeroNeighbors(x, y + 1);
            revealZeroNeighbors(x + 1, y - 1);
            revealZeroNeighbors(x + 1, y);
            revealZeroNeighbors(x + 1, y + 1);
        }
    }

    // Other methods from MouseListener interface
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

