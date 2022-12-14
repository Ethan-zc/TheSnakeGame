package com.seven.zichen.snakegame.socket;

import com.seven.zichen.snakegame.TheGameClient;
import com.seven.zichen.snakegame.models.WaitingPanel;
import com.seven.zichen.snakegame.client.Client;
import java.io.*;
import java.net.*;
import java.util.*;

public class WaitingClient implements Runnable{

    private Socket socket = null;
    private String username;
    private List<String> userList = new ArrayList<>();
    private WaitingPanel wp;
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
    public WaitingClient(String username, WaitingPanel wp) {
        try {
            this.wp = wp;
            this.username = username;
            socket = new Socket(TheGameClient.localhostIP, 8000);
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
            // While in waiting room, listen to server to get the list
            // of current user names in the waiting room.
            while (isWaiting) {
                try {
                    if (socket.isClosed()) break;
                    fromServer = new DataInputStream(socket.getInputStream());
                    String message = fromServer.readUTF();
                    if (!message.equals("GAMESTART!")) {
                        setUserList(message);
                    } else {
                        isWaiting = false;
                    }
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
            wp.setVisible(false);


            // When game started, client would create a thread to run client class
            // to handle the game process on client side.
            if (!username.equals("GAMESTART")) {
                try {
                    System.out.println("Client started!");
                    socket.close();
                    Thread C = new Thread((Runnable) new Client(username));
                    C.start();

                } catch (Exception e) {
                }
            }

        }
    }
}
