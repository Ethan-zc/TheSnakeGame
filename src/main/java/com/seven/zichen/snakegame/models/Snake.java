package com.seven.zichen.snakegame.models;

import java.util.ArrayList;
import java.util.HashSet;

import static com.seven.zichen.snakegame.models.GamePanel.GAME_HEIGHT;
import static com.seven.zichen.snakegame.models.GamePanel.UNIT_SIZE;
import static com.seven.zichen.snakegame.models.GamePanel.GAME_WIDTH;
import static com.seven.zichen.snakegame.models.GamePanel.GAME_UNITS;


public class Snake {

    private int[] X;
    private int[] Y;
    private Boolean alive;
    int bodyParts = 6;
    char direction = 'R';
    private String userName;
    private Integer scores;

    public Snake(String userName, int x, int y) {
        this.userName = userName;
        this.alive = true;
        this.scores = 0;
        this.X = new int[GAME_UNITS];
        this.X[0] = x;
        this.Y = new int[GAME_UNITS];
        this.Y[0] = y;
    }

    public void move() {
        for(int i = bodyParts;i>0;i--){
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        switch (direction){
            case 'U':
                Y[0]=Y[0]-UNIT_SIZE;
                break;
            case 'D':
                Y[0]=Y[0]+UNIT_SIZE;
                break;
            case 'L':
                X[0]=X[0]-UNIT_SIZE;
                break;
            case 'R':
                X[0]=X[0]+UNIT_SIZE;
                break;
        }

    }

    public void changeDirection(char dir) {
        if (!(direction == 'U' && dir == 'D' ||
                direction == 'D' && dir == 'U' ||
                direction == 'L' && dir == 'R' ||
                direction == 'R' && dir == 'L')) {
            this.direction = dir;
        }
    }

    public char getDirection() {
        return this.direction;
    }

    public int[] getX() {
        return this.X;
    }

    public int[] getY() {
        return this.Y;
    }

    public int getBodyParts() {
        return this.bodyParts;
    }

    public void eatApple() {
        bodyParts++;
        scores += 10;
    }

    public int getScores() {
        return this.scores;
    }

    public void dead() {
        this.alive = false;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public int[] getHead() {
        int[] headPos = new int[2];
        headPos[0] = X[0];
        headPos[1] = Y[0];
        return headPos;
    }

    public void checkCollision() {
        for(int i = bodyParts; i > 0; i--){
            if((X[0] == X[i]) && (Y[0] == Y[i])){
                dead();
            }
        }
        //check if head touches left border
        if(X[0] < 0){
            X[0] = GAME_WIDTH;
        }
        //checks if head touches right border
        if(X[0] > GAME_WIDTH){
            X[0] = 0;
        }
        //checks if head touched top border
        if(Y[0] < 0){
            Y[0] = GAME_HEIGHT - UNIT_SIZE;
        }
        //checks if head touched bottom border
        if(Y[0] > GAME_HEIGHT - UNIT_SIZE){
            Y[0] = 0;
        }
    }

    public void checkCrossCollisions(ArrayList<Snake> snakes) {
        for (Snake snake : snakes) {
            if (snake.equals(this) || !snake.isAlive()) {
            } else {
                int headX = this.getHead()[0];
                int headY = this.getHead()[1];
                if (headX == snake.getHead()[0] && headY == snake.getHead()[1]) {
                    snake.dead();
                    this.dead();
                    break;
                }
                int Xs[] = snake.getX();
                int Ys[] = snake.getY();
                for (int i = 0; i < Xs.length; i++) {
                    if (headX == Xs[i] && headY == Ys[i]) {
                        this.dead();
                        break;
                    }
                }
            }
        }
    }

//    public List<>
}
