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

public class GamePanel extends JPanel implements ActionListener {
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 650;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (GAME_WIDTH*GAME_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    static final Color snakeColor = Color.green;
    static final Color[] snakeColors = {
            Color.decode("#16E053"),
            Color.decode("#F5E12F"),
            Color.decode("#53EEFC"),
            Color.decode("#6B6FF2")};
//    private Snake snake;
    private ArrayList<Snake> snakes;
    int applesEaten;
    int appleX;
    int appleY;
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
//        snake = new Snake("Testing");
        snakes = new ArrayList<>();
        snakes.add(new Snake("Testing1", 15, 15));
        snakes.add(new Snake("Testing2", 75, 75));
        snakes.add(new Snake("Testing3", 135, 135));
        snakes.add(new Snake("Testing4", 195, 195));

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
        boolean allDead = true;
        for (Snake s : snakes) {
            if (s.isAlive()) {
                allDead = false;
            }
        }
        if(!allDead){
            g.drawLine(0, GAME_HEIGHT, GAME_WIDTH, GAME_HEIGHT);
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            //draw the head and the body of the snake
            for (int i = 0; i < snakes.size(); i++) {
                Snake snake = snakes.get(i);
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
                g.drawString(snake.isAlive() ? String.valueOf(snakes.get(i).getScores()) : "Over", (20 + i * 150), SCREEN_HEIGHT - 10);
            }
        }else {
            gameOver(g);
        }

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
        }
    }

    public void gameOver(Graphics g){
        //score
        for (int i = 0; i < snakes.size(); i++) {
            Snake snake = snakes.get(i);
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
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            char dir1 = snakes.get(0).getDirection();
            char dir2 = snakes.get(1).getDirection();
            char dir3 = snakes.get(2).getDirection();
            char dir4 = snakes.get(3).getDirection();

            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir1 != 'R'){
                        snakes.get(0).changeDirection('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir1 != 'L'){
                        snakes.get(0).changeDirection('R');
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir1 != 'D'){
                        snakes.get(0).changeDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir1 != 'U'){
                        snakes.get(0).changeDirection('D');
                    }
                    break;
                case KeyEvent.VK_A:
                    if(dir2 != 'R'){
                        snakes.get(1).changeDirection('L');
                    }
                    break;
                case KeyEvent.VK_D:
                    if(dir2 != 'L'){
                        snakes.get(1).changeDirection('R');
                    }
                    break;
                case KeyEvent.VK_W:
                    if(dir2 != 'D'){
                        snakes.get(1).changeDirection('U');
                    }
                    break;
                case KeyEvent.VK_S:
                    if(dir2 != 'U'){
                        snakes.get(1).changeDirection('D');
                    }
                    break;
                case KeyEvent.VK_J:
                    if(dir3 != 'R'){
                        snakes.get(2).changeDirection('L');
                    }
                    break;
                case KeyEvent.VK_L:
                    if(dir3 != 'L'){
                        snakes.get(2).changeDirection('R');
                    }
                    break;
                case KeyEvent.VK_I:
                    if(dir3 != 'D'){
                        snakes.get(2).changeDirection('U');
                    }
                    break;
                case KeyEvent.VK_K:
                    if(dir3 != 'U'){
                        snakes.get(2).changeDirection('D');
                    }
                    break;
                case KeyEvent.VK_F:
                    if(dir4 != 'R'){
                        snakes.get(3).changeDirection('L');
                    }
                    break;
                case KeyEvent.VK_H:
                    if(dir4 != 'L'){
                        snakes.get(3).changeDirection('R');
                    }
                    break;
                case KeyEvent.VK_T:
                    if(dir4 != 'D'){
                        snakes.get(3).changeDirection('U');
                    }
                    break;
                case KeyEvent.VK_G:
                    if(dir4 != 'U'){
                        snakes.get(3).changeDirection('D');
                    }
                    break;
            }
        }
    }
}
