/**
 * by Joshua Toumu'a & Leo Riginelli
 * 20/07/23
 * Adding menu systems
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener
{
    private MainLoop mainLoop;

    public Menu(MainLoop mainLoop) {
        this.mainLoop = mainLoop;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Success!");
        String menuName = e.getActionCommand();
        System.out.println(menuName);
        switch(menuName){
            case "easy":

                break;
            case "med":

                break;
            case "hard":

                break;
            case "small":

                break;
            case "large":

                break;
            case "theme1":

                break;
            case "theme2":

                break;
            case "help":

                break;
            case "restart":
                mainLoop.restartGame();
                break;
            case "quit":
                System.exit(0);
                break;
        }
    }

}
