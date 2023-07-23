/**
 * by Joshua Toumu'a & Leo Riginelli
 * 20/07/23
 * Adding menu systems
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Success!");
        String menuName = e.getActionCommand();
        System.out.println(menuName);
    }
}
