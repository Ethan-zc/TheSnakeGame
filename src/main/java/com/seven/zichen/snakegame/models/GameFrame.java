package com.seven.zichen.snakegame.models;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        GamePanel game = new GamePanel();
        this.add(new DrawGame(game, this));
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // take our J frame and fit it around all of the component that we add
        this.setVisible(true);
        this.setLocationRelativeTo(null); // appear in the middle of the computer screen
    }
}
