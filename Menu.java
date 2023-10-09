/**
 * by Joshua Toumu'a & Leo Riginelli
 * 20/07/23
 * Adding menu systems
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Menu implements ActionListener
{
    private MainLoop mainLoop;
    
    int storeMineCount = 9;
    int storeGridSize = 10;
    int storeButtonSize = 30;

    public Menu(MainLoop mainLoop, int setGrid, int setButton, int setMine) {
        this.mainLoop = mainLoop;
        storeMineCount = setMine;
        storeGridSize = setGrid;
        storeButtonSize = setButton;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //System.out.println("Success!");
        String menuName = e.getActionCommand();
        //System.out.println(menuName);
        switch(menuName){
            case "easy":
                storeGridSize = 10;
                storeMineCount = 9;
                mainLoop.restartGameInMenu(storeButtonSize,storeGridSize, storeMineCount);
                break;
            case "med":
                storeGridSize = 15;
                storeMineCount = 30;
                mainLoop.restartGameInMenu(storeButtonSize,storeGridSize, storeMineCount);
                break;
            case "hard":
                storeGridSize = 20;
                storeMineCount = 40;
                mainLoop.restartGameInMenu(storeButtonSize,storeGridSize, storeMineCount);
                break;
            case "small":
                storeButtonSize = 30;
                mainLoop.restartGameInMenu(storeButtonSize,storeGridSize, storeMineCount);
                break;
            case "large":
                storeButtonSize = 50;
                mainLoop.restartGameInMenu(storeButtonSize,storeGridSize, storeMineCount);
                break;
            case "help":
                dialogBox();
                break;
            case "restart":
                mainLoop.restartGameInMenu(storeButtonSize,storeGridSize, storeButtonSize);
                break;
            case "quit":
                System.exit(0);
                break;
        }
        
    }
    void dialogBox(){
        JDialog tutorial = new JDialog();
        tutorial.setBounds(100,100,1250,620);
        
        TextArea area = new TextArea("JoLe puzzle game where the objective is to uncover all the hidden mines on a grid without triggering any of them. Here's a basic rundown on the rules:\n\n1. Game Setup:\n   - You are presented with a grid of square cells.\n   - Some of these cells contain hidden mines, while others are safe.\n\n2. Objective:\n   - Your goal is to reveal all the safe cells without detonating any mines.\n\n3. Gameplay:\n   - To uncover a cell, simply left-click on it.\n   - When you uncover a cell:\n     - If it contains a mine, the game ends, and you lose.\n     - If it's a safe cell, a number is displayed in the cell.\n       - This number represents the count of mines in the adjacent cells (horizontally, vertically, and diagonally).\n\n4. Using Clues:\n   - Use the numbers as clues to determine which cells are safe.\n   - If a cell has a \"1,\" it means there is exactly 1 mine adjacent to it.\n   - If a cell has a \"2,\" there are 2 mines nearby, and so on.\n\n5. Flagging Mines:\n   - If you suspect a cell contains a mine, right-click on it to place a flag.\n   - This helps you keep track of potential mine locations.\n\n6. Winning:\n   - Continue uncovering cells and flagging mines until you've revealed all safe cells.\n   - Once all safe cells are uncovered, you win the game!\n\n7. Losing:\n   - If you accidentally uncover a mine, the game ends, and you lose.\n   - Pay close attention to the numbers and use logical deduction to avoid mines.\n\n8. Difficulty Levels:\n   - JoLe comes with different grid sizes and mine densities to increase the challenge.\n\nRemember that Minesweeper is a game of strategy and deduction. Start with the cells that are certain to be safe based on the numbers you uncover, and work your way through the grid methodically to win the game.");
        tutorial.add(area);
        area.setEditable(false);
        tutorial.setResizable(false);
        
        tutorial.toFront();
        tutorial.setVisible(true);
        tutorial.setTitle("Tutorial");
    }

}
