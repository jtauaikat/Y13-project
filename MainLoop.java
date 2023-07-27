/**
 * by Joshua Toumu'a & Leo Riginelli
 * 20/07/23
 * Adding menu systems
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
    JButton gridButton; //defines the JButton
    int buttonSize = 30; //constant for the visual size of the button

    static int gridSize = 10;//creates variable for overall gridsize. allowed to change
    int mineCount = 10; // amount of mines that spawn
    public Board mainBoard = new Board(mineCount); //creates a board using board class and populates board upon opening program

    boolean newGame = true;

    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;

    ImageIcon flagIcon = new ImageIcon("flag.png");
    ImageIcon mineIcon = new ImageIcon("mine.png"); // Variable to store the ImageIcon for mine cells
    ImageIcon icon;
    ImageIcon rolloverIcon;

    public MainLoop() {
        setupLoop();
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

    private enum ButtonType {
        LEFT_CLICK,//shorthand name for left click
        RIGHT_CLICK, //shorthand name for right click
        INVALID_INPUT //accounts for invalid clicks i.e. middle mouse
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        String name = button.getActionCommand();
        int buttonCoordY = Integer.parseInt(name) / 100;
        int buttonCoordX = Integer.parseInt(name) % 100;
        Cells cell = mainBoard.cellGrid[buttonCoordX][buttonCoordY];

        ButtonType buttonType = getButtonType(e);

        if (buttonType == ButtonType.RIGHT_CLICK) {
            handleRightClick(button, cell);
        } else if (buttonType == ButtonType.LEFT_CLICK) {
            handleLeftClick(button, cell);
        }
        //mainBoard.testPrint();
    }

    private ButtonType getButtonType(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3){
            return ButtonType.RIGHT_CLICK;
        }else if(e.getButton() == MouseEvent.BUTTON1){
            return ButtonType.LEFT_CLICK;
        }else{
            return ButtonType.INVALID_INPUT;
        }
    }

    private void handleRightClick(JButton button, Cells cell) {
        if (!cell.getShown()) {
            if (cell.getFlagged()) {
                ImageIcon hiddenIcon = new ImageIcon("blueRect.png");
                ImageIcon hiddenRolloverIcon = new ImageIcon("lightBlueRect.png");
                cell.setFlagged(false);
                setButtonIcon(button, icon, rolloverIcon);
            } else {
                cell.setFlagged(true);
                button.setIcon(flagIcon);
                button.setRolloverEnabled(false);
            }
        }
    }

    private void handleLeftClick(JButton button, Cells cell) {
        if (!cell.getFlagged()) {
            cell.setShown(true);
            setButtonIcon(button, cell.getIcon(), cell.getRollover());
        }
    }

    private void setButtonIcon(JButton button, ImageIcon icon, ImageIcon rolloverIcon) {
        button.setIcon(icon);
        button.setRolloverIcon(rolloverIcon);
        button.setRolloverEnabled(true);
        repaint();
    }

    public void setupLoop(){
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

                setImage(buttonXCount, buttonYCount);//runs image file

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
        gridButton.setHorizontalAlignment(SwingConstants.CENTER);
        gridButton.setVerticalAlignment(SwingConstants.CENTER);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        Menu menuHandler = new Menu();

        // Difficulty menu
        JMenu menu = new JMenu("Difficulty");
        menuBar.add(menu);

        menuItem = new JMenuItem("Easy");
        menuItem.setActionCommand("easy"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Medium");
        menuItem.setActionCommand("med"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Hard");
        menuItem.setActionCommand("hard"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);
        
        menu = new JMenu("Size");
        menuBar.add(menu);

        menuItem = new JMenuItem("Small");
        menuItem.setActionCommand("small"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Large");
        menuItem.setActionCommand("large"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        // Theme menu
        menu = new JMenu("Theme");
        menuBar.add(menu);

        menuItem = new JMenuItem("Theme 1");
        menuItem.setActionCommand("theme1"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Theme 2");
        menuItem.setActionCommand("theme2"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        menu = new JMenu("Other");
        menuBar.add(menu);

        menuItem = new JMenuItem("Help");
        menuItem.setActionCommand("help"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Restart");
        menuItem.setActionCommand("restart"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Quit");
        menuItem.setActionCommand("quit"); // Set the action command
        menuItem.addActionListener(menuHandler);
        menu.add(menuItem);

        // Setting up JFrame
        setTitle("JoLe");
        this.getContentPane().setPreferredSize(new Dimension(1050, 1050));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.toFront(); // Brings the panel to front of desktop
        this.setVisible(true);
    }

    public void setImage(int buttonXCount, int buttonYCount){
        int mineAmount = mainBoard.cellGrid[buttonXCount][buttonYCount].getNeighbours(); //returns the amount of neighbours for an integer
        if (mainBoard.cellGrid[buttonXCount][buttonYCount].isMine()) {
            icon = mineIcon;
        } else {
            switch (mineAmount) {
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String name = button.getActionCommand();
        int buttonCoordY = Integer.parseInt(name) / 100;
        int buttonCoordX = Integer.parseInt(name) % 100;

        if (mainBoard.cellGrid[buttonCoordX][buttonCoordY].getNeighbours() == 0 && !mainBoard.cellGrid[buttonCoordX][buttonCoordY].getShown()) {
            // Zero cell clicked, reveal neighboring cells recursively
            revealZeroNeighbours(buttonCoordX, buttonCoordY);
        }
        repaint();
    }

    private void revealZeroNeighbours(int x, int y) {
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

        JButton button = getButtonAtCoordinates(x, y);
        button.setIcon(cell.getIcon());
        button.setRolloverIcon(cell.getRollover());

        if (cell.getNeighbours() == 0) {
            // Recursively reveal neighboring cells
            for(int revealY = y-1; revealY<y+2; revealY++){
                for(int revealX = x-1; revealX<x+2; revealX++){
                    revealZeroNeighbours(revealX, revealY);
                }
            }
        }
    }

    private JButton getButtonAtCoordinates(int x, int y) {
        Component[] components = this.getContentPane().getComponents();
        int buttonIndex = y * gridSize + x;
        if (buttonIndex >= 0 && buttonIndex < components.length && components[buttonIndex] instanceof JButton) {
            return (JButton) components[buttonIndex];
        }
        return null;
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

