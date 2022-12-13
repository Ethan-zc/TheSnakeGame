package com.seven.zichen.snakegame.socket;
import com.seven.zichen.snakegame.models.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;


public class GameClient {

    class HandleClient implements Runnable {
        public class GameDraw extends JFrame {
            public GameDraw(GamePanel game) {
                this.add(new DrawGame(game, this));
                this.setTitle("Snake");
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setResizable(false);
                this.pack(); // take our J frame and fit it around all of the component that we add
                this.setVisible(true);
                this.setLocationRelativeTo(null);
                this.addKeyListener(new MyKeyAdapter());
            }
        }

        private Socket socket;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        public HandleClient(String host, int port) throws IOException {
            this.socket = new Socket(host, port);
            System.out.println("Connected!");
        }

        @Override
        public void run() {
            try {
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                oos = new ObjectOutputStream(outputStream);
                ois = new ObjectInputStream(inputStream);
                GamePanel game = (GamePanel) ois.readObject();
                System.out.println("Received: " + game.isRunning());
                System.out.println("Creating game frame...");
                game.startGame();
                new GameDraw(game);

//                System.out.println("Closing socket and terminating program.");
//                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        public class MyKeyAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    oos.reset();
                    oos.writeObject(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
////        new Thread(new HandleClient("localhost", 7777)).start();
//    }

}
