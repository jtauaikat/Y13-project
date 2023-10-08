/**
 * by Joshua Toumu'a & Leo Riginelli
 * 03/10/23
 * Tidying Code + Implementation of final menu and particles
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
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class MainLoop extends JFrame implements ActionListener, MouseListener {
    JButton gridButton; //defines the JButton
    int buttonSize = 30; //constant for the visual size of the button

    int gridSize = 10;//creates variable for overall gridsize. allowed to change
    int mineCount = 9; // amount of mines that spawn
    public Board mainBoard; //creates a board using board class and populates board upon opening program

    boolean newGame = true;

    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    private Timer timer;
    private Timer lossTimer;
    ImageIcon flagIcon = new ImageIcon("flag.png");
    ImageIcon mineIcon = new ImageIcon("mine.png"); // Variable to store the ImageIcon for mine cells
    ImageIcon icon;
    ImageIcon rolloverIcon;

    Image setIcon;
    Image scaleIcon;
     int particleAdjust = 4;
    Scanner keyboard = new Scanner(System.in);

    private ArrayList<Particle> particles = new ArrayList<>();
    private final int maxParticles = 50;

    public MainLoop() {
        mainBoard = new Board(mineCount, gridSize);
        setupLoop();
        // Adding MouseListener to the buttons
        for (Component component : this.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addMouseListener(this);
            }
        }
        clickFirstZeroTile();

    }

    public MainLoop(int newButtonSize, int newGridSize, int newMineCount) {
        buttonSize = newButtonSize;
        gridSize = newGridSize;
        mineCount = newMineCount;

        mainBoard = new Board(mineCount, gridSize);
        setupLoop();
        // Adding MouseListener to the buttons
        for (Component component : this.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addMouseListener(this);
            }
        }
        clickFirstZeroTile();

    }

    /** set variables */

    private enum ButtonType {
        LEFT_CLICK,//shorthand name for left click
        RIGHT_CLICK, //shorthand name for right click
        INVALID_INPUT //accounts for invalid clicks i.e. middle mouse
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

    public void paint(Graphics g) {
        super.paint(g);
        drawParticles(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                new MainLoop();
            });
    }

    private void setButtonIcon(JButton button, ImageIcon icon, ImageIcon rolloverIcon) {
        button.setIcon(icon);
        button.setRolloverIcon(rolloverIcon);

        repaint();
    }

    private JButton getButtonAtCoordinates(int x, int y) {
        Component[] components = this.getContentPane().getComponents();
        int buttonIndex = y * gridSize + x;
        if (buttonIndex >= 0 && buttonIndex < components.length && components[buttonIndex] instanceof JButton) {
            return (JButton) components[buttonIndex];
        }
        return null;
    }

    /** mouse click methods */

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
            //System.out.println(buttonCoordX);
            //System.out.println(buttonCoordY);
            //createParticles(button.getX() + buttonSize / 2, originY + button.getY()+2*buttonSize, 10);
        }
    }

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

    private void handleLeftClick(JButton button, Cells cell) {
        if (!cell.getFlagged()) {
            cell.setShown(true);
            setButtonIcon(button, cell.getIcon(), cell.getRollover());
            handleLossCondition(); // Check for the loss condition after revealing the cell
            checkWinCondition(); // Check for the win condition after revealing the cell
            // if(cell.getNeighbours() == 0){
            //    revealZeroNeighbours(button.getX(), button.getY());
            // }
            //f (mainBoard.cellGrid[button.getX()][button.getY()].getNeighbours() == 0) {
            // Found a "0" tile, trigger a left-click on it

            //handleLeftClick(button, mainBoard.cellGrid[x][y]);
            //  revealZeroNeighbours(button.getX(), button.getY());

            // Break the loop once we've clicked the first "0" tile
            // }
        }
    }

    public void actionPerformed(ActionEvent e) {
        //.out.println("actionPerformed");
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

    // Other methods from MouseListener interface
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /** win/loss handling */

    public void restartGame() {
        this.dispose();
        MainLoop gameReset = new MainLoop();
    }

    public void restartGameInMenu(int passSize, int passGrid, int passMineCount) {
        this.dispose();
        MainLoop gameReset = new MainLoop(passSize, passGrid, passMineCount);
    }

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
            restartGame(); 
        }

    }

    private void handleLossCondition() {
        // Iterate through the grid to check if any mine cell is revealed
        Boolean lossState = false;
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
        if (lossState){
            System.out.println(getButtonAtCoordinates(mineX, mineY).getX() + buttonSize / 2 + " is the xPos.");
            System.out.println(getButtonAtCoordinates(mineX, mineY).getY() + buttonSize * 2 + " is the yPos.");
            createParticles(getButtonAtCoordinates(mineX, mineY).getX() + buttonSize / 2, getButtonAtCoordinates(mineX, mineY).getY() + buttonSize * 2, 50); // Call createParticles for the mine

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
            return;
        }
    }

    /** particle methods */

    public void createParticles(int x, int y, int count) {
        for (int i = 0; i < count; i++) {
            if (particles.size() < maxParticles) {
               
                Particle particle = new Particle(x+particleAdjust, y+particleAdjust);
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

    private void drawParticles(Graphics g) {
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

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

    /**start methods */

    private void clickFirstZeroTile() {
        // Find the first "0" tile and programmatically trigger a left-click on it
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (mainBoard.cellGrid[x][y].getNeighbours() == 0) {
                    // Found a "0" tile, trigger a left-click on it
                    JButton button = getButtonAtCoordinates(x, y);
                    //handleLeftClick(button, mainBoard.cellGrid[x][y]);
                    revealZeroNeighbours(x, y);
                    setImage(x, y); // Set the icon and rolloverIcon for the clicked cell

                    return; // Break the loop once we've clicked the first "0" tile
                }
            }
        }
    }

    public void setupLoop(){
        Integer buttonLabel = 0; //Integer type for number-based button naming system

        //places top left button 100px down and 100px right
        int buttonYPosition = 0;
        int buttonXPosition = 0;

        // Creates grid of butt ons
        for (int buttonYCount = 0; buttonYCount < gridSize; buttonYCount++) {
            for (int buttonXCount = 0; buttonXCount < gridSize; buttonXCount++) {
                //code to generate new JButton
                gridButton = new JButton(""+buttonLabel);
                //gridButton.setText(buttonLabel.toString()); //takes Integer and sets gridbutton name to Integer
                gridButton.setFont(new Font("Dialog", Font.PLAIN, 0));
                gridButton.setBounds(buttonXPosition, buttonYPosition, buttonSize, buttonSize);//sets button size and position
                gridButton.setFocusable(false);
                gridButton.addActionListener(this);
                buttonLabel++; //increases by 1 when moving to the left

                this.add(gridButton); //draws button
                buttonXPosition += buttonSize; //moves X Coord to the right

                gridButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                setImage(buttonXCount, buttonYCount);//runs image file

                // Image scaleIconImg = icon.getImage();
                // Image newIconScaled = scaleIconImg.getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH);
                // icon = new ImageIcon(newIconScaled);

                // Image scaleRolloverImg = rolloverIcon.getImage();
                // Image newRolloverScaled = scaleRolloverImg.getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH);
                // rolloverIcon = new ImageIcon(newRolloverScaled);

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
            buttonXPosition = 0; //returns X Coord to origin point

            buttonLabel += 100; //increases by 100 when moving downwards
        }
        Menu menuClassHandler = new Menu(this, gridSize, buttonSize, mineCount);

        menuSetup(menuClassHandler);

        // Setting up JFrame
        setTitle("JoLe");
        this.getContentPane().setPreferredSize(new Dimension(gridSize*buttonSize, gridSize*buttonSize));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.toFront(); // Brings the panel to front of desktop
        this.setVisible(true);
    }

    void menuSetup(Menu menuClassHandler){
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        // Difficulty menu
        JMenu menu = new JMenu("Difficulty");
        menuBar.add(menu);

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

        menu = new JMenu("Size");
        menuBar.add(menu);

        menuItem = new JMenuItem("Small");
        menuItem.setActionCommand("small"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Large");
        menuItem.setActionCommand("large"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        // Theme menu
        menu = new JMenu("Theme");
        menuBar.add(menu);

        menuItem = new JMenuItem("Theme 1");
        menuItem.setActionCommand("theme1"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menuItem = new JMenuItem("Theme 2");
        menuItem.setActionCommand("theme2"); // Set the action command
        menuItem.addActionListener(menuClassHandler);
        menu.add(menuItem);

        menu = new JMenu("Other");
        menuBar.add(menu);

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
                 ImageIcon flagIcon = new ImageIcon("flagL.png");
                // ImageIcon mineIcon = new ImageIcon("mineL.png");
                 particleAdjust = 0;
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

