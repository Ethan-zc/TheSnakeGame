package com.seven.zichen.snakegame.socket;
import com.seven.zichen.snakegame.models.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;


public class GameClient extends JFrame {
    static ObjectOutputStream objectOutputStream;
    public GameClient(GamePanel game){
        this.add(new DrawGame(game, this));
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // take our J frame and fit it around all of the component that we add
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.addKeyListener(new MyKeyAdapter());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // need host and port, we want to connect to the ServerSocket at port 7777
        Socket socket = new Socket("localhost", 7777);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        GamePanel game = (GamePanel) objectInputStream.readObject();
            // make a bunch of messages to send.

        System.out.println("Received: " + game.isRunning());
        System.out.println("Creating game frame...");
        game.startGame();
        new GameClient(game);

//        System.out.println("Closing socket and terminating program.");
//        socket.close();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            try {
                objectOutputStream.reset();
                objectOutputStream.writeObject(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

}
