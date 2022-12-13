package com.seven.zichen.snakegame.socket;

import com.mysql.cj.protocol.x.XMessage;
import com.seven.zichen.snakegame.models.Game;
import com.seven.zichen.snakegame.models.GameFrame;
import com.seven.zichen.snakegame.models.GamePanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.*;

public class WaitingClient implements Runnable{

    private Socket socket = null;
    private String username;
    private List<String> userList = new ArrayList<>();
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
    public WaitingClient(String username) {
        try {
            this.username = username;
            socket = new Socket("localhost", 8000);
            new Thread(new HandleServer(socket, username)).start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void setUserList(String list) {
        String[] receivedList = list.split(",");
        List<String> newList = new ArrayList<>();
        for (String user : receivedList) {
            if (!user.equals(username)) {
                newList.add(user);
            }
        }
        this.userList = newList;
        System.out.println("The transformed: " + userList.toString());
    }

    public List<String> getUserList() {
        return this.userList;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {

    }

    class HandleServer implements Runnable {
        private Socket socket;
        private String username;// A connected socket

        private boolean isWaiting = true;
        private boolean gameStart = false;

        public HandleServer(Socket socket, String username) {
            this.socket = socket;
            this.username = username;
        }

        public void run() {
            try {

                toServer = new DataOutputStream(socket.getOutputStream());
            }
            catch (IOException ex) {
                System.out.println(ex);
            }

            try {

                String username = this.username;
                toServer.writeUTF(username);
                toServer.flush();

            }
            catch (IOException ex) {
                System.err.println(ex);
            }
            while (isWaiting) {
                try {
                    if (socket.isClosed()) break;
                    fromServer = new DataInputStream(socket.getInputStream());
                    String message = fromServer.readUTF();
                    if (!message.equals("GAMESTART!")) {
                        setUserList(message);
                    } else {
                        isWaiting = false;
                        gameStart = true;
                    }
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }

            while (gameStart) {
                try {
                    ObjectInputStream fromServerObj = new ObjectInputStream(socket.getInputStream());

                    GamePanel serverGP = (GamePanel) fromServerObj.readObject();
                    GameFrame clientGame = new GameFrame(serverGP);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

//            while (gameStart) {
//
//            }
        }
    }
}
