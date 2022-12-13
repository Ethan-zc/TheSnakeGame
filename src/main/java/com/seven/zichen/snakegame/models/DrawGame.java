package com.seven.zichen.snakegame.models;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static com.seven.zichen.snakegame.models.GamePanel.GAME_HEIGHT;
import static com.seven.zichen.snakegame.models.GamePanel.GAME_WIDTH;
import static com.seven.zichen.snakegame.models.GamePanel.SCREEN_HEIGHT;
import static com.seven.zichen.snakegame.models.GamePanel.SCREEN_WIDTH;
import static com.seven.zichen.snakegame.models.GamePanel.UNIT_SIZE;

public class DrawGame extends JPanel implements ActionListener {
    static final Color[] snakeColors = {
            Color.decode("#16E053"),
            Color.decode("#F5E12F"),
            Color.decode("#53EEFC"),
            Color.decode("#6B6FF2")};

    private GamePanel game;
    private JFrame frame;

    private JButton btn;

    public DrawGame(GamePanel game, JFrame frame) {
        this.game = game;
        this.frame = frame;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        btn = new JButton("Show Leaderboard");
        btn.setBounds(220, 540, 150, 30);
        btn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn) {
            frame.dispose();
            String response = null;
            try {
                response = LeaderBoard.getLeaderBoardData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new LeaderBoard(response, ""); // TODO: Pass current username
        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
        repaint();
    }

    private void draw(Graphics g){
        boolean allDead = true;
        for (Snake s : game.getSnakes()) {
            if (s.isAlive()) {
                allDead = false;
            }
        }
        if (!allDead && game.isRunning()) {
            g.drawLine(0, GAME_HEIGHT, GAME_WIDTH, GAME_HEIGHT);
            g.setColor(Color.black);
            g.fillOval(game.getAppleX(),game.getAppleY(),UNIT_SIZE,UNIT_SIZE);

            for (int i = 0; i < game.getSnakes().size(); i++) {
                Snake snake = game.getSnakes().get(i);
                if (snake.isAlive()) {
                    int bodyParts = snake.getBodyParts();
                    int[] X = snake.getX();
                    int[] Y = snake.getY();
                    for(int j = 0; j < bodyParts; j++){
                        g.setColor(snakeColors[i]);
                        if(j == 0){
                            g.fillRoundRect(X[j], Y[j], UNIT_SIZE,UNIT_SIZE, 9, 9);
                        } else {
                            g.fillRect(X[j], Y[j], UNIT_SIZE,UNIT_SIZE);
                        }
                    }
                }
                //score
                g.setColor(snakeColors[i]);
                g.setFont(new Font("Bayon",Font.BOLD,40));
                g.drawString(snake.isAlive() ? String.valueOf(game.getSnakes().get(i).getScores()) : "OUT", (20 + i * 120), SCREEN_HEIGHT - 10);
            }
            g.setColor(Color.black);
            g.setFont(new Font("Bayon",Font.BOLD,35));
            g.drawString("0" + game.getGameTime() / 60 + ":" + (game.getGameTime() % 60 >= 10 ? "" : "0") + game.getGameTime() % 60, SCREEN_WIDTH - 105, SCREEN_HEIGHT - 10);
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g){
        //score
        for (int i = 0; i < game.getSnakes().size(); i++) {
            Snake snake = game.getSnakes().get(i);
            g.setColor(snakeColors[i]);
            g.setFont( new Font("Bayon",Font.BOLD, 40));
            g.drawString(snake.getUserName() + ": " + snake.getScores(), 180, 190 + i * 100);
        }
        //Game over text
        g.setColor(Color.black);
        g.setFont(new Font("Bayon",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(GAME_WIDTH - metrics.stringWidth("Game Over"))/2,100);
        add(btn);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.handleKeyPressed(e.getKeyCode());
        }
    }
}
