
/**
 * by Joshua Toumu'a & Leo Riginelli
 * 09/10/23
 * commenting overveiw
 */

// Import necessary classes and packages
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import javax.swing.border.Border;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

// Main class definition, extends JFrame and implements ActionListener and MouseListener interfaces
public class MainLoop extends JFrame implements ActionListener, MouseListener {
    JButton gridButton; 
    int buttonSize = 30; // Constant for the visual size of the button

    int gridSize = 10; // Create a variable for the overall grid size (can be changed)
    int mineCount = 9; // Define the number of mines that spawn
    public Board mainBoard; // Create a board using the Board class and populate it upon opening the program

    JMenuBar menuBar; // Menu & timer variable imports
    JMenu menu;
    JMenuItem menuItem;
    private Timer timer;
    private Timer lossTimer;
    ImageIcon flagIcon = new ImageIcon("flag.png"); // ImageIcon for flag cells
    ImageIcon mineIcon = new ImageIcon("mine.png"); // ImageIcon for mine cells
    ImageIcon icon;
    ImageIcon rolloverIcon;

    Image setIcon;
    Image scaleIcon;
    int particleAdjust = 4; // Particle adjustment for X-coordinate
    int particleAdjustY = 4; // Particle adjustment for Y-coordinate
    Scanner keyboard = new Scanner(System.in);

    // ArrayList to store particles
    private ArrayList<Particle> particles = new ArrayList<>();
    private final int maxParticles = 50; // Maximum number of particles

    // Constructor for the MainLoop class
    public MainLoop() {
        // Create a new Board with mineCount and gridSize
        mainBoard = new Board(mineCount, gridSize);
        // Initialize the game loop
        setupLoop();
        // Adding MouseListener to the buttons
        for (Component component : this.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addMouseListener(this);
            }
        }
        // Automatically click the first tile with zero neighbors
        clickFirstZeroTile();
    }

    // Overloaded constructor with custom buttonSize, gridSize, and mineCount
    public MainLoop(int newButtonSize, int newGridSize, int newMineCount) {
        buttonSize = newButtonSize;
        gridSize = newGridSize;
        mineCount = newMineCount;

        // Create a new Board with the specified mineCount and gridSize
        mainBoard = new Board(mineCount, gridSize);
        // Initialize the game loop
        setupLoop();
        // Adding MouseListener to the buttons
        for (Component component : this.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addMouseListener(this);
            }
        }
        // Automatically click the first tile with zero neighbors
        clickFirstZeroTile();
    }

    // Enum to represent different button types (LEFT_CLICK, RIGHT_CLICK, INVALID_INPUT)
    private enum ButtonType {
        LEFT_CLICK,      // Shorthand name for left click
        RIGHT_CLICK,     // Shorthand name for right click
        INVALID_INPUT    // Accounts for invalid clicks (e.g., middle mouse)
    }

    // Determine the button type (LEFT_CLICK, RIGHT_CLICK, INVALID_INPUT) based on MouseEvent
    private ButtonType getButtonType(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            return ButtonType.RIGHT_CLICK;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            return ButtonType.LEFT_CLICK;
        } else {
            return ButtonType.INVALID_INPUT;
        }
    }

    // Override the paint method to draw particles
    public void paint(Graphics g) {
        super.paint(g);
        drawParticles(g);
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                new MainLoop();
            });
    }

    // Set the icon and rollover icon for a JButton
    private void setButtonIcon(JButton button, ImageIcon icon, ImageIcon rolloverIcon) {
        button.setIcon(icon);
        button.setRolloverIcon(rolloverIcon);
        repaint();
    }

    // Get the JButton at specified coordinates (x, y) in the grid
    private JButton getButtonAtCoordinates(int x, int y) {
        Component[] components = this.getContentPane().getComponents();
        int buttonIndex = y * gridSize + x;
        if (buttonIndex >= 0 && buttonIndex < components.length && components[buttonIndex] instanceof JButton) {
            return (JButton) components[buttonIndex];
        }
        return null;
    }

    /** mouse click methods */
    // Handle mouse click events
    @Override
    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        String name = button.getActionCommand();
        int buttonCoordY = Integer.parseInt(name) / 100;
        int buttonCoordX = Integer.parseInt(name) % 100;
        Cells cell = mainBoard.cellGrid[buttonCoordX][buttonCoordY];

        ButtonType buttonType = getButtonType(e);

        // Handle right-click
        if (buttonType == ButtonType.RIGHT_CLICK) {
            handleRightClick(button, cell);
        }
        // Handle left-click
        else if (buttonType == ButtonType.LEFT_CLICK) {
            handleLeftClick(button, cell);
        }
    }

    // Handle right-click event
    private void handleRightClick(JButton button, Cells cell) {
        ImageIcon hiddenIcon = new ImageIcon("blueRect.png");
        ImageIcon hiddenRolloverIcon = new ImageIcon("lightBlueRect.png");
        if (!cell.getShown()) {
            if (cell.getFlagged()) {
                cell.setFlagged(false);
                setButtonIcon(button, hiddenIcon, hiddenRolloverIcon);
            } else {
                cell.setFlagged(true);
                setButtonIcon(button, flagIcon, flagIcon);
            }
        }
    }

    // Handle left-click event
    private void handleLeftClick(JButton button, Cells cell) {
        if (!cell.getFlagged()) {
            cell.setShown(true);
            setButtonIcon(button, cell.getIcon(), cell.getRollover());
            handleLossCondition(); // Check for the loss condition after revealing the cell
            checkWinCondition(); // Check for the win condition after revealing the cell
        }
    }

    // ActionListener for button clicks
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String name = button.getActionCommand();
        int buttonCoordY = Integer.parseInt(name) / 100;
        int buttonCoordX = Integer.parseInt(name) % 100;

        // Check if the clicked cell has no neighboring mines (value is 0)
        if (mainBoard.cellGrid[buttonCoordX][buttonCoordY].getNeighbours() == 0 && 
        !mainBoard.cellGrid[buttonCoordX][buttonCoordY].getShown()) {
            // Zero cell clicked, reveal neighboring cells recursively
            revealZeroNeighbours(buttonCoordX, buttonCoordY);
        }
        repaint();
    }

    // Other methods from the MouseListener interface
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /** win/loss handling */
    // Restart the game by disposing of the current instance and creating a new one
    public void restartGame() {
        this.dispose();
        MainLoop gameReset = new MainLoop();
    }

    // Restart the game with custom settings from the menu
    public void restartGameInMenu(int passSize, int passGrid, int passMineCount) {
        this.dispose();
        MainLoop gameReset = new MainLoop(passSize, passGrid, passMineCount);
    }

    // Check if the win condition is met
    private void checkWinCondition() {
        int mineTally = 0;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cells cell = mainBoard.cellGrid[x][y];
                if (!cell.getShown()) {
                    mineTally++;
                }
            }
        }
        if (mineTally == mineCount) {
            // If all non-mine cells are revealed, show a pop-up message indicating the win
            JOptionPane.showMessageDialog(this, "Congratulations! You've won!");
            restartGame(); // Restart the game after winning
        }
    }

    // Handle the loss condition when a mine is hit
    private void handleLossCondition() {
        // Iterate through the grid to check if any mine cell is revealed
        boolean lossState = false;
        int mineY = 0;
        int mineX = 0;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cells cell = mainBoard.cellGrid[x][y];
                if (cell.isMine() && cell.getShown()) {
                    // Store x and y as final variables
                    mineX = x;
                    mineY = y;
                    lossState = true;
                }
            }
        }
        if (lossState) {
            int xPos = getButtonAtCoordinates(mineX, mineY).getX() + buttonSize / 2;
            int yPos = getButtonAtCoordinates(mineX, mineY).getY() + buttonSize * 2;

            // Create particles at the mine position
            createParticles(xPos, yPos, 50);

            // If a mine cell is revealed, show a pop-up message indicating the loss after a delay
            if (lossTimer == null) {
                lossTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(MainLoop.this, "You've hit a mine! Game Over.");
                            lossTimer.stop();
                            restartGame(); // Restart the game after the loss
                        }
                    });
                lossTimer.setRepeats(false); // Execute the action only once
                lossTimer.start();
            } else if (!lossTimer.isRunning()) {
                // If the timer is not running (e.g., if a mine is hit again after restarting), start it again
                lossTimer.start();
            }
        }
    }

    /** particle methods */
    // Create and manage particle effects
    public void createParticles(int x, int y, int count) {
        for (int i = 0; i < count; i++) {
            if (particles.size() < maxParticles) {
                Particle particle = new Particle(x + particleAdjust, y + particleAdjustY);
                particles.add(particle);
            }
        }

        if (timer == null || !timer.isRunning()) { // Create and start the timer only if it doesn't exist or isn't running
            timer = new Timer(30, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateParticles();
                        repaint();

                        // If there are no more particles, stop the timer
                        if (particles.isEmpty()) {
                            timer.stop();
                        }
                    }
                });
            timer.start();
        }
    }

    // Draw particles on the screen
    private void drawParticles(Graphics g) {
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

    // Update the state of particles
    private void updateParticles() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();
            if (!particle.isAlive()) {
                iterator.remove();
            }
        }
    }

    // Programmatically trigger a left-click on the first "0" tile
    private void clickFirstZeroTile() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (mainBoard.cellGrid[x][y].getNeighbours() == 0) {
                    JButton button = getButtonAtCoordinates(x, y);
                    revealZeroNeighbours(x, y);
                    setImage(x, y); // Set the icon and rolloverIcon for the clicked cell
                    return; // Break the loop once we've clicked the first "0" tile
                }
            }
        }
    }

    // Set up the main game loop
    public void setupLoop() {
        Integer buttonLabel = 0; // Integer type for number-based button naming system

        // Places top-left button 100px down and 100px right
        int buttonYPosition = 0;
        int buttonXPosition = 0;

        // Creates a grid of buttons
        for (int buttonYCount = 0; buttonYCount < gridSize; buttonYCount++) {
            for (int buttonXCount = 0; buttonXCount < gridSize; buttonXCount++) {
                // Code to generate a new JButton
                gridButton = new JButton("" + buttonLabel);
                // gridButton.setText(buttonLabel.toString()); // Takes Integer and sets gridbutton name to Integer
                gridButton.setFont(new Font("Dialog", Font.PLAIN, 0));
                gridButton.setBounds(buttonXPosition, buttonYPosition, buttonSize, buttonSize); // Sets button size and position
                gridButton.setFocusable(false);
                gridButton.addActionListener(this);
                buttonLabel++; // Increases by 1 when moving to the left

                this.add(gridButton); // Draws button
                buttonXPosition += buttonSize; // Moves X Coord to the right

                gridButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                setImage(buttonXCount, buttonYCount); // Runs the image file

                mainBoard.cellGrid[buttonXCount][buttonYCount].setIcon(icon);
                mainBoard.cellGrid[buttonXCount][buttonYCount].setRollover(rolloverIcon);

                icon = new ImageIcon("blueRect.png"); // Resets icon to hidden cell Icon
                rolloverIcon = new ImageIcon("lightBlueRect.png");

                gridButton.setIcon(icon); // Sets Icon according to switch statement
                gridButton.setRolloverIcon(rolloverIcon);
            }
            buttonLabel -= gridSize; // Returns the labels position to the beginning
            buttonYPosition += buttonSize; // Moves Y Coord downwards
            buttonXPosition = 0; // Returns X Coord to the origin point

            buttonLabel += 100; // Increases by 100 when moving downwards
        }
        Menu menuClassHandler = new Menu(this, gridSize, buttonSize, mineCount);

        menuSetup(menuClassHandler);

        // Setting up JFrame
        setTitle("JoLe");
        this.getContentPane().setPreferredSize(new Dimension(gridSize * buttonSize, gridSize * buttonSize));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.toFront(); // Brings the panel to the front of the desktop
        this.setVisible(true);
    }

    /**
     * Sets up the game's menu with difficulty, size, theme, and other options.
     * 
     * @param menuClassHandler An instance of the Menu class to handle menu actions.
     */
    void menuSetup(Menu menuClassHandler) {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        // Difficulty menu
        JMenu menu = new JMenu("Difficulty");
        menuBar.add(menu);

        // Add menu items for different difficulty levels
        menuItem = new JMenuItem("Easy");
        menuItem.setActionCommand("easy"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Medium");
        menuItem.setActionCommand("med"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Hard");
        menuItem.setActionCommand("hard"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        // Size menu
        menu = new JMenu("Size");
        menuBar.add(menu);

        // Add menu items for different board sizes
        menuItem = new JMenuItem("Small");
        menuItem.setActionCommand("small"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Large");
        menuItem.setActionCommand("large"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        // Other menu
        menu = new JMenu("Other");
        menuBar.add(menu);

        // Add menu items for help, restart, and quit
        menuItem = new JMenuItem("Help");
        menuItem.setActionCommand("help"); // Set the action command
        menuItem.setAccelerator(KeyStroke.getKeyStroke(('h')));
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Restart");
        menuItem.setActionCommand("restart"); // Set the action command
        menuItem.setAccelerator(KeyStroke.getKeyStroke(('r')));
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Quit");
        menuItem.setActionCommand("quit"); // Set the action command
        menuItem.setAccelerator(KeyStroke.getKeyStroke(("ESCAPE")));
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);
    }

    public void setImage(int buttonXCount, int buttonYCount){
        int mineAmount = mainBoard.cellGrid[buttonXCount][buttonYCount].getNeighbours(); //returns the amount of neighbours for an integer
        if (mainBoard.cellGrid[buttonXCount][buttonYCount].isMine()) {
            icon = mineIcon;
        } else {

            String gridSizeSuffix = "";
            if (buttonSize == 50) {
                gridSizeSuffix = "L";
                flagIcon = new ImageIcon("flagL.png");
                mineIcon = new ImageIcon("mineL.png");
                particleAdjustY = -20;

            }
            switch (mineAmount) {
                    //each case allows for a different image to be used for each button
                case 0:
                    icon = new ImageIcon("number_0.png");

                    break;
                case 1:
                    icon = new ImageIcon("number_1" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_1H" + gridSizeSuffix + ".png");
                    break;
                case 2:
                    icon = new ImageIcon("number_2" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_2H" + gridSizeSuffix + ".png");
                    break;
                case 3:
                    icon = new ImageIcon("number_3" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_3H" + gridSizeSuffix + ".png");
                    break;

                case 4:
                    icon = new ImageIcon("number_4" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_4H" + gridSizeSuffix + ".png");
                    break;
                case 5:
                    icon = new ImageIcon("number_5" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_5H" + gridSizeSuffix + ".png");
                    break;
                case 6:
                    icon = new ImageIcon("number_6" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_6H" + gridSizeSuffix + ".png");
                    break;
                case 7:
                    icon = new ImageIcon("number_7" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_7H" + gridSizeSuffix + ".png");
                    break;
                case 8:
                    icon = new ImageIcon("number_8" + gridSizeSuffix + ".png");
                    rolloverIcon = new ImageIcon("number_8H" + gridSizeSuffix + ".png");
                    break;

                default:
                    icon = new ImageIcon("mine" + gridSizeSuffix + ".png");
                    break;
            }  
        }
    }

  private void revealZeroNeighbours(int x, int y) {
        if (x < 0 || x >= gridSize || y < 0 || y >= gridSize) {
            // If cell is out of bounds, return
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
            for (int revealY = y - 1; revealY <= y + 1; revealY++) {
                for (int revealX = x - 1; revealX <= x + 1; revealX++) {
                    if (revealX != x || revealY != y) {
                        revealZeroNeighbours(revealX, revealY);
                    }
                }
            }
        }
    }

}

