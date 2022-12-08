package com.seven.zichen.snakegame.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class WaitingClient implements Runnable{

    private Socket socket = null;
    private String username;
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
    public WaitingClient(String username) {
        try {
            this.username = username;
            socket = new Socket("localhost", 8000);
            System.out.println("connected" + "\n");
            new Thread(new HandleServer(socket, username)).start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        int rand = new Random().nextInt();
        String userName = "testing " + Integer.toString(rand);
        WaitingClient waitingClient = new WaitingClient(userName);

    }

    class HandleServer implements Runnable {
        private Socket socket;
        private String username;// A connected socket

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
            while (true) {
                try {
                    if (socket.isClosed()) break;
                    fromServer = new DataInputStream(socket.getInputStream());
                    String message = fromServer.readUTF();
                    System.out.println("receive from server: " + message);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }
}
