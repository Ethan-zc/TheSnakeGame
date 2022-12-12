package com.seven.zichen.snakegame.models;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.seven.zichen.snakegame.models.GamePanel.GAME_HEIGHT;
import static com.seven.zichen.snakegame.models.GamePanel.GAME_WIDTH;
import static com.seven.zichen.snakegame.models.GamePanel.SCREEN_HEIGHT;
import static com.seven.zichen.snakegame.models.GamePanel.SCREEN_WIDTH;
import static com.seven.zichen.snakegame.models.GamePanel.UNIT_SIZE;

public class DrawGame extends JPanel {
    static final Color[] snakeColors = {
            Color.decode("#16E053"),
            Color.decode("#F5E12F"),
            Color.decode("#53EEFC"),
            Color.decode("#6B6FF2")};

    private GamePanel game;

    public DrawGame(GamePanel game) {
        this.game = game;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

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
        if(!allDead){
            g.drawLine(0, GAME_HEIGHT, GAME_WIDTH, GAME_HEIGHT);
            g.setColor(Color.red);
            g.fillOval(game.getAppleX(),game.getAppleY(),UNIT_SIZE,UNIT_SIZE);
            //draw the head and the body of the snake
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
                g.drawString(snake.isAlive() ? String.valueOf(game.getSnakes().get(i).getScores()) : "Over", (20 + i * 150), SCREEN_HEIGHT - 10);
            }
        }else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g){
        //score
        for (int i = 0; i < game.getSnakes().size(); i++) {
            Snake snake = game.getSnakes().get(i);
            g.setColor(snakeColors[i]);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
//            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString(String.valueOf(snake.getScores()), 100 + i * 100, g.getFont().getSize());
        }
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(GAME_WIDTH - metrics.stringWidth("Game Over"))/2,GAME_HEIGHT/2);
    }

    public class MyKeyAdapter extends KeyAdapter {
        private final int[] keysValc = {
                KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
                KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S,
                KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_I, KeyEvent.VK_K,
                KeyEvent.VK_F, KeyEvent.VK_H, KeyEvent.VK_T, KeyEvent.VK_G
        };

        @Override
        public void keyPressed(KeyEvent e) {
            for (int i = 0; i < game.getSnakes().size(); i++) {
                Snake snake = game.getSnakes().get(i);
                char dir = snake.getDirection();
                if (e.getKeyCode() == keysValc[i * 4]) {
                    if (dir != 'R') {
                        snake.changeDirection('L');
                    }
                } else if (e.getKeyCode() == keysValc[i * 4 + 1]) {
                    if (dir != 'L') {
                        snake.changeDirection('R');
                    }
                } else if (e.getKeyCode() == keysValc[i * 4 + 2]) {
                    if (dir != 'D') {
                        snake.changeDirection('U');
                    }
                } else if (e.getKeyCode() == keysValc[i * 4 + 3]) {
                    if (dir != 'U') {
                        snake.changeDirection('D');
                    }
                }
            }
        }
    }
}
