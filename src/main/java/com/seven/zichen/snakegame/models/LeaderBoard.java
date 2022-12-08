package com.seven.zichen.snakegame.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class LeaderBoard extends JFrame {
    private JLabel l1;
    private ArrayList<JLabel> ls;
    private static final int numLeaders = 6;
    private Map<String, Integer> leaderScore = new HashMap<>();

    private static final String[] suffixes = new String[] {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};

    public LeaderBoard(String response) {
        setTitle("Leader Board");

        setVisible(true);

        setSize(388, 470);
        getContentPane().setBackground(Color.white);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("LEADER BOARD:");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 28));
        l1.setBounds(108, 25, 300, 42);

        processResponse(response);


//        leaderScore.put("ALEX", 35);
//        leaderScore.put("BOB", 30);
//        leaderScore.put("CATHERINE", 27);
//        leaderScore.put("DAVID", 25);
//        leaderScore.put("EASON", 23);
//        leaderScore.put("FALCON", 20);

        List<Map.Entry<String, Integer>> leaderList = new LinkedList<>(leaderScore.entrySet());
        int boardLength = Math.min(numLeaders, leaderList.size());

        for (int i = 0; i < boardLength; i++) {
            JLabel l = new JLabel((i + 1) + suffixes[i] + "\t\t" + leaderList.get(i).getKey() + "\t\t" + leaderList.get(i).getValue());
            l.setForeground(Color.black);
            l.setFont(new Font("Bayon", Font.BOLD, 26));
            l.setBounds(46, 50 * i + 107, 350, 43);
            add(l);
        }
        System.out.println("finish");
        add(l1);
    }

    public static String getLeaderBoardData() throws IOException {
        URL url = new URL("http://localhost:8080/account/getLeaderBoard");
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
            leaderScore.put(name, score);
        }

    }
}
