package Presentation;

import javax.swing.*;

/**
 * Created by GEORGE on 17.03.2018.
 */
public class UIinit
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Electric Castle");
        frame.setContentPane(new UserInterface().getLoginPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
