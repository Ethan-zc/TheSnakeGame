package com.seven.zichen.snakegame.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.seven.zichen.snakegame.TheGameClient;
import com.seven.zichen.snakegame.utilities.Pair;

public class LeaderBoard extends JFrame implements ActionListener {
    private JLabel l1;
    private JButton btn;
    private static final int numLeaders = 6;
    private PriorityQueue<Pair<Integer, String>> scoreToLeader;

    private static final String[] suffixes = new String[] {"st", "nd", "rd", "th", "th", "th"};
    private String userName;
    private String ipAddr;

    public LeaderBoard(String response, String userName) {
        this.userName = userName;
        this.ipAddr = TheGameClient.localhostIP;
        this.scoreToLeader = new PriorityQueue<>(Comparator.comparingInt(t -> -t.obj1));

        setTitle("Leader Board");

        setVisible(true);
        setBounds(400, 100, 388, 470);
        getContentPane().setBackground(Color.white);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("LEADER BOARD:");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 28));
        l1.setBounds(90, 25, 300, 42);

        processResponse(response);

        int boardLength = Math.min(numLeaders, scoreToLeader.size());

        for (int i = 0; i < boardLength; i++) {
            Pair<Integer, String> pair = scoreToLeader.poll();
            JLabel l = new JLabel(String.format("%-6s %-15s %-5d", (i + 1) + suffixes[i], pair.obj2, pair.obj1));
            l.setForeground(Color.black);
            l.setFont(new Font("Bayon", Font.BOLD, 22));
            l.setBounds(46, 50 * i + 90, 350, 43);
            getContentPane().add(l);
        }

        btn = new JButton("Go Back");
        btn.setBounds(119, 400, 150, 30);
        btn.addActionListener(this);

        getContentPane().add(btn);
        getContentPane().add(l1);
    }

    public static String getLeaderBoardData(String ipAddr) throws IOException {
        URL url = new URL("http://"+ ipAddr +":8080/account/getLeaderBoard");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);


        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }

    private void processResponse(String response) {
        String[] records = response.split("\"accname\":\"");
        for (int i = 1; i < records.length; i++) {
            String name = records[i].split("\"")[0];
            int score = Integer.parseInt(records[i].split("}")[0].split("\"score\":")[1]);
            Pair<Integer, String> pair = new Pair<>(score, name);
            scoreToLeader.add(pair);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.dispose();
        new WelcomePage(userName);
    }
}
