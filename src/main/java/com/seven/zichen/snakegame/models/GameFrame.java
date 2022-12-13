package com.seven.zichen.snakegame.models;

import javax.swing.*;
import java.util.List;

public class GameFrame extends JFrame {

    private GamePanel gamePanel;
    public GameFrame(List<String> userNameList){
        gamePanel = new GamePanel(userNameList);
        this.add(new DrawGame(gamePanel, this));
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // take our J frame and fit it around all of the component that we add
        this.setVisible(true);
        this.setLocationRelativeTo(null); // appear in the middle of the computer screen
    }

    public GameFrame(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.add(new DrawGame(gamePanel, this));
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // take our J frame and fit it around all of the component that we add
        this.setVisible(true);
        this.setLocationRelativeTo(null); // appear in the middle of the computer screen
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

}
