package com.seven.zichen.snakegame.socket;

import com.seven.zichen.snakegame.models.*;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IOException {

        ServerSocket ss = new ServerSocket(7777);
        System.out.println("ServerSocket sending game panel");
        Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
        System.out.println("Connection from " + socket + "!");
        List<String> userNameList = new ArrayList<>();
        userNameList.add("Testing1");
        userNameList.add("Testing2");
        userNameList.add("Testing3");
        userNameList.add("Testing4");
        GamePanel game = new GamePanel(userNameList);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        objectOutputStream.reset();
        objectOutputStream.writeObject(game);
        System.out.println("Sent out!");

        while(true) {
            KeyEvent e = (KeyEvent) objectInputStream.readObject();
            System.out.println("Received key!");
            game.handleKeyPressed(e.getKeyCode());
        }
//        System.out.println("Closing sockets.");
//        ss.close();
//        socket.close();
    }

}
