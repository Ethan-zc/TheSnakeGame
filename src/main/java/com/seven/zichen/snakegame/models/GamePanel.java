package com.seven.zichen.snakegame.models;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
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
    static final Color snakeColor = Color.decode("#000000");
    final int X[]= new int[GAME_UNITS];
    final int Y[]=new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
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
        if(running){
            //draw the grid line on the screen
            for(int i = 0;i<GAME_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,GAME_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,GAME_WIDTH,i*UNIT_SIZE);
            }
            //draw the food on the screen on a random place
            g.drawLine(0, GAME_HEIGHT, GAME_WIDTH, GAME_HEIGHT);
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            //draw the head and the body of the snake
            for(int i =0;i<bodyParts;i++){
                if(i==0){
                    g.setColor(Color.blue);             //for the head
                }else {
                    g.setColor(snakeColor); //for the body

                }
                g.fillRect(X[i],Y[i],UNIT_SIZE,UNIT_SIZE);
                //score
                g.setColor(snakeColor);
                g.setFont(new Font("Bayon",Font.BOLD,40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString(String.valueOf(applesEaten),(GAME_WIDTH - metrics.stringWidth(String.valueOf(applesEaten)))/2, SCREEN_HEIGHT - 10);
            }
        }else {
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int) (GAME_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int) (GAME_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move() throws IOException {
        for(int i = bodyParts;i>0;i--){
            X[i] = X[i-1];
            Y[i]=Y[i-1];

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

        URL url = new URL("http://127.0.0.1:8080/game/printhist");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("X", Arrays.toString(X));
        params.put("Y", Arrays.toString(Y));

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

        for (int c; (c = in.read()) >= 0;)
            System.out.print((char)c);
    }
    public void checkApple(){
        if((X[0] == appleX) && (Y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollision(){
        //checks if head collide the body
        for(int i = bodyParts;i>0;i--){
            if((X[0]== X[i]) && (Y[0]== Y[i])){
                running = false;
            }
        }
        //check if head touches left border
        if(X[0] < 0){
            running = false;
        }
        //checks if head touches right border
        if(X[0] >GAME_WIDTH){
            running = false;
        }
        //checks if head touched top border
        if(Y[0] < 0){
            running = false;
        }
        //checks if head touched bottom border
        if(Y[0] > GAME_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (GAME_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(GAME_WIDTH - metrics.stringWidth("Game Over"))/2,GAME_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            try {
                move();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
