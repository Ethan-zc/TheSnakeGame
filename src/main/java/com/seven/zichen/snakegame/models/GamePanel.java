package com.seven.zichen.snakegame.models;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.net.*;

public class GamePanel extends JPanel implements ActionListener {
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 650;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (GAME_WIDTH*GAME_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    static final Color snakeColor = Color.green;
    private Snake snake;
    int applesEaten;
    int appleX;
    int appleY;
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        snake = new Snake("Testing", GAME_UNITS);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame () {
        newApple();
        running=true;
        timer= new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(snake.isAlive()){
            //draw the grid line on the screen
//            for(int i = 0;i<GAME_HEIGHT/UNIT_SIZE;i++){
//                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,GAME_HEIGHT);
//                g.drawLine(0,i*UNIT_SIZE,GAME_WIDTH,i*UNIT_SIZE);
//            }
            //draw the food on the screen on a random place
            g.drawLine(0, GAME_HEIGHT, GAME_WIDTH, GAME_HEIGHT);
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            //draw the head and the body of the snake
            int bodyParts = snake.getBodyParts();
            int[] X = snake.getX();
            int[] Y = snake.getY();
            for(int i =0;i<bodyParts;i++){
                if(i==0){
                    g.setColor(Color.blue);
                }else {
                    g.setColor(snakeColor); //for the body

                }
                g.fillRect(X[i],Y[i],UNIT_SIZE,UNIT_SIZE);
                //score
                g.setColor(snakeColor);
                g.setFont(new Font("Bayon",Font.BOLD,40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString(String.valueOf(snake.getScores()),(GAME_WIDTH - metrics.stringWidth(String.valueOf(applesEaten)))/2, SCREEN_HEIGHT - 10);
            }
        }else {
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int) (GAME_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int) (GAME_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void checkApple(){
        int[] head = snake.getHead();
        int dist = (int) Math.sqrt(Math.pow((head[0] - appleX), 2) + Math.pow((head[1] - appleY), 2));
        if(dist < UNIT_SIZE){
            snake.eatApple();
            newApple();
        }
    }
    public void checkCollision(){
        //checks if head collide the body
        snake.checkCollision();
        if(!snake.isAlive()){
            running = false;
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+snake.getScores(), (GAME_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(GAME_WIDTH - metrics.stringWidth("Game Over"))/2,GAME_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){

            snake.move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            char dir = snake.getDirection();
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir != 'R'){
                        snake.changeDirection('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L'){
                        snake.changeDirection('R');
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir != 'D'){
                        snake.changeDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U'){
                        snake.changeDirection('D');
                    }
                    break;
            }
        }
    }
}
