package gui;

import javax.swing.*;
import java.awt.*;
import java.applet.Applet;

public class GUI {
    public GUI() {
        JFrame frame = new JFrame();

        JButton button = new JButton("Click Me");
        JLabel label = new JLabel("Number of Clicks:0");

        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Oop RPG - Dexter Young");
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        new GUI();
    }
}
