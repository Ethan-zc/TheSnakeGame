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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WelcomePage extends JFrame implements ActionListener {
    JLabel l1;
    JButton btn1, btn2;
    public WelcomePage(String userName) {
        setTitle("Welcome!");

        setVisible(true);

        setSize(400, 270);
        getContentPane().setBackground(Color.white);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Hi " + userName + "!");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 26));

        btn1 = new JButton("Start Game");
        btn2 = new JButton("Show Leaderboard");

        l1.setBounds(118, 28, 200, 47);

        btn1.setBounds(90, 160, 200, 30);
        btn2.setBounds(170, 160, 200, 30);

        add(l1);

        add(btn1);
        add(btn2);

        btn1.addActionListener(this);
        btn2.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn1) {
            this.dispose();
            new GameFrame();
        } else if (actionEvent.getSource() == btn2) {
            this.dispose();
            String response = null;
            try {
                response = LeaderBoard.getLeaderBoardData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new LeaderBoard(response);
        }
    }
}
