package com.seven.zichen.snakegame.models;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class GamePanel implements ActionListener {
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 650;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (GAME_WIDTH*GAME_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    private int gameTime;
//    private Snake snake;
    private ArrayList<Snake> snakes;
    private int appleX;
    private int appleY;
    private boolean running = false;
    private Timer timer;
    private Timer countDown;
    private Random random;

    public GamePanel() {
        random = new Random();
        gameTime = 120;

        snakes = new ArrayList<>();
        snakes.add(new Snake("Testing1", "LRUD",15, 15));
        snakes.add(new Snake("Testing2", "ADWS", 75, 75));
        snakes.add(new Snake("Testing3", "JLIK", 135, 135));
        snakes.add(new Snake("Testing4", "FHTG", 195, 195));

        startGame();
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    public int getAppleX() {
        return appleX;
    }

    public int getAppleY() {
        return appleY;
    }

    public boolean isRunning() {
        return running;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void startGame () {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        countDown = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameTime--;
                if (gameTime == 0) {
                    for (Snake snake : snakes) {
                        snake.dead();
                    }
                    running = false;
                    timer.stop();
                    countDown.stop();
                }
            }
        });
        timer.start();
        countDown.start();
    }
    public void newApple(){
        appleX = random.nextInt((GAME_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((GAME_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void checkApple(){
        for (Snake snake : snakes) {
            if(snake.isAlive()) {
                int[] head = snake.getHead();
                int dist = (int) Math.sqrt(Math.pow((head[0] - appleX), 2) + Math.pow((head[1] - appleY), 2));
                if(dist < UNIT_SIZE){
                    snake.eatApple();
                    newApple();
                }
            }
        }
    }
    public void checkCollision(){
        //checks if head collide the body

        boolean allDead = true;
        for (Snake snake : snakes) {
            if (snake.isAlive()) {
                snake.checkCollision();
                snake.checkCrossCollisions(snakes);
            }
            if (snake.isAlive()) {
                allDead = false;
            }
        }

        if(allDead){
            running = false;
            timer.stop();
            countDown.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            for (Snake snake : snakes) {
                if (snake.isAlive()) {
                    snake.move();
                }
            }
            checkApple();
            checkCollision();
        }
    }
}
