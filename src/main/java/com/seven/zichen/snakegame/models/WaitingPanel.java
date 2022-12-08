package com.seven.zichen.snakegame.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WaitingPanel extends JFrame {
    JLabel l1, l2, l3;
    int numPlayers = 1;

    public WaitingPanel() {
        setTitle("Waiting Room");

        setVisible(true);

        setSize(400, 270);
        getContentPane().setBackground(Color.white);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Current number of players:");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 22));

        l2 = new JLabel(String.valueOf(numPlayers));
        l2.setForeground(Color.black);
        l2.setFont(new Font("Bayon", Font.BOLD, 16));

        l3 = new JLabel("Waiting for the game to start...");
        l3.setForeground(Color.black);
        l3.setFont(new Font("Bayon", Font.BOLD, 16));


        l1.setBounds(34, 28, 400, 47);

        l2.setBounds(34, 70, 100, 30);

        l3.setBounds(34, 110, 400, 30);

        add(l1);

        add(l2);

        add(l3);

    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
}
