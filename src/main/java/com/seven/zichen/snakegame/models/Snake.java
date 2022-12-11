package com.seven.zichen.snakegame.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.seven.zichen.snakegame.models.GamePanel.GAME_HEIGHT;
import static com.seven.zichen.snakegame.models.GamePanel.GAME_WIDTH;

public class Snake {

    private int[] X;
    private int[] Y;
    private static final int UNIT_SIZE = 15;
    private Boolean alive = false;
    int bodyParts = 6;
    char direction = 'R';
    private String username = null;
    private Integer scores;

    public Snake(String username, int allDots) {
        this.username = username;
        this.alive = true;
        this.scores = 0;
        this.X = new int[allDots];
        this.Y = new int[allDots];

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
        for(int i = bodyParts;i>0;i--){
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


//    public List<>
}
