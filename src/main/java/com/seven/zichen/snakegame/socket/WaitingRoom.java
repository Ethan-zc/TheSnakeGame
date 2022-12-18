package com.seven.zichen.snakegame.socket;

import com.seven.zichen.snakegame.TheGameClient;
import com.seven.zichen.snakegame.games_handler.GameHandlerManager;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WaitingRoom implements Runnable{

    private int clientNo = 0;

    private ConcurrentHashMap<Integer, Socket> activeClients;
    private ConcurrentHashMap<String, Integer> clientInRoom;

    public WaitingRoom() {

        activeClients = new ConcurrentHashMap<Integer, Socket>();
        clientInRoom = new ConcurrentHashMap<>();
        Thread t = new Thread(this);
        t.start();
    }

    public static void main(String[] args) {
        WaitingRoom waitingRoom = new WaitingRoom();
    }


    @Override
    public void run() {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Waitingroom Started at: " + new Date() + "/n");

            while (true) {
                // Listen for a new connection request
                Socket socket = serverSocket.accept();

                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());
                if (!clientInRoom.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (String un : clientInRoom.keySet()) {
                        sb.append(un);
                        sb.append(",");
                    }
                    outputToClient.writeUTF(sb.toString());
                }

                // Increment clientNo
                clientNo++;

                System.out.println("Starting thread for client " + clientNo +
                        " at " + new Date() + '\n');

                // Find the client's host name, and IP address
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("Client " + clientNo + "'s host name is "
                        + inetAddress.getHostName() + "\n");
                System.out.println("Client " + clientNo + "'s IP Address is "
                        + inetAddress.getHostAddress() + "\n");

                // Create and start a new thread for the connection
                new Thread(new HandleAClient(socket, clientNo)).start();
            }
        }
        catch(IOException ex) {
            System.err.println(ex);
        }
    }

    class HandleAClient implements Runnable {
        private final Socket socket; // A connected socket
        private final int clientNum;

        private boolean isWaiting = true;
        private boolean gameInit = false;
        private boolean gameRunning = false;

        public HandleAClient(Socket socket, int clientNum) {
            this.socket = socket;
            this.clientNum = clientNum;
            activeClients.put(clientNum, socket);
        }

        /** Run a thread */
        public void run() {
            try {
                // Create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());

                // Continuously serve the client
                while (isWaiting) {
                    String username = inputFromClient.readUTF();
                    if (!username.equals("GAMESTART")) {
                        clientInRoom.put(username, 1);
                        StringBuilder sb = new StringBuilder();
                        for (String un : clientInRoom.keySet()) {
                            sb.append(un);
                            sb.append(",");
                        }
                        for (Integer client : activeClients.keySet()) {
                            Socket currSocket = activeClients.get(client);
                            if (!currSocket.isClosed()) {
                                DataOutputStream outputToClient = new DataOutputStream(
                                        currSocket.getOutputStream());
                                if (client != this.clientNum) {
                                    outputToClient.writeUTF(sb.toString());
                                }
                            }
                        }

                        System.out.println("All users: " + sb.toString());
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (String un : clientInRoom.keySet()) {
                            sb.append(un);
                            sb.append(",");
                        }
                        for (Integer client : activeClients.keySet()) {
                            Socket currSocket = activeClients.get(client);
                            if (!currSocket.isClosed()) {
                                DataOutputStream outputToClient = new DataOutputStream(
                                        currSocket.getOutputStream());
                                if (client != this.clientNum) {
                                    outputToClient.writeUTF(sb.toString());
                                }
                            }
                        }

                        System.out.println("All users: " + sb.toString());
                        System.out.println("Game Started!");
                        isWaiting = false;
                    }
                }

                System.out.println("Game initializing...");
//                List<String> userList = new ArrayList<>();
//                for (int i = 1; i <= clientInRoom.size(); i++) {
//                    userList.add(Integer.toString(i));
//                }
//                GamePanel mainGame = new GamePanel(userList);
//                System.out.println("Game initialization finished!");

                for (Integer client : activeClients.keySet()) {
                    Socket currSocket = activeClients.get(client);
                    if (!currSocket.isClosed()) {
                        DataOutputStream outputToClient = new DataOutputStream(
                                currSocket.getOutputStream());
                        outputToClient.writeUTF("GAMESTART!");

                    }
                }
                socket.close();
                clientInRoom.clear();

                URL url = new URL("http://" + TheGameClient.localhostIP + ":8080/game/addgame");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = conn.getResponseCode();
                System.out.println("responseCode: " + responseCode);
                Thread GH=new Thread(new GameHandlerManager(5757, 5656, "Snakes Server", 2000, activeClients.size() - 1));
        		GH.start();
                activeClients.clear();
//                GamePanel game = new GamePanel(userList);
//                for (int i = 0; i < userList.size(); i++) {
//                    new GameFrame(game);
//                }
//                gameRunning = true;
//                System.out.println("Server initializing...");
//
//                URL url = new URL("http://localhost:8080/game/newgame");
//                Map<String,Object> params = new LinkedHashMap<>();
//                params.put("num", activeClients.size() - 1);
//
//                StringBuilder postData = new StringBuilder();
//                for (Map.Entry<String,Object> param : params.entrySet()) {
//                    if (postData.length() != 0) postData.append('&');
//                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//                    postData.append('=');
//                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//                }
//                byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
//
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//                conn.setDoOutput(true);
//                conn.getOutputStream().write(postDataBytes);
//                System.out.println("Sent!");


            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
