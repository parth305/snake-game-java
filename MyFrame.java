package SnakeGame2;

import javax.swing.*;

public class MyFrame extends JFrame {
    GamePanel panel;
    MyFrame(){
        panel=new GamePanel();

        this.add(panel);
        this.setResizable(false);
      //  this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Snake Game");
        this.pack();
    }
}
