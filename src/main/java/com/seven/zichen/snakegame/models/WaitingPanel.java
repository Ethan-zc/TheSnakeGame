package com.seven.zichen.snakegame.models;

import com.seven.zichen.snakegame.socket.WaitingClient;

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
import java.util.Set;
import java.util.List;


public class WaitingPanel extends JFrame {
    JLabel l1, l2, l3, l4, l5, l6;
    int numPlayers = 1;
    String username;
    WaitingClient currentClient;

    public WaitingPanel(String username) {
        this.username = username;
        setTitle("Waiting Room");

        setVisible(true);

        setSize(400, 340);
        getContentPane().setBackground(Color.white);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentClient = new WaitingClient(username);
        draw();
    }

    public void draw() {
        l1 = new JLabel("Current players:");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 22));

        l2 = new JLabel(username);
        l2.setForeground(Color.black);
        l2.setFont(new Font("Bayon", Font.BOLD, 16));

        l4 = new JLabel("Waiting...");
        l5 = new JLabel("Waiting...");
        l6 = new JLabel("Waiting...");

        l4.setForeground(Color.black);
        l4.setFont(new Font("Bayon", Font.BOLD, 16));

        l5.setForeground(Color.black);
        l5.setFont(new Font("Bayon", Font.BOLD, 16));

        l6.setForeground(Color.black);
        l6.setFont(new Font("Bayon", Font.BOLD, 16));

        l3 = new JLabel("Waiting for the game to start...");
        l3.setForeground(Color.black);
        l3.setFont(new Font("Bayon", Font.BOLD, 16));


        l1.setBounds(34, 28, 400, 47);

        l2.setBounds(34, 70, 100, 30);

        l4.setBounds(34, 114, 100, 30);

        l5.setBounds(34, 158, 100, 30);

        l6.setBounds(34, 202, 100, 30);

        l3.setBounds(34, 246, 400, 30);

        add(l1);

        add(l2);

        add(l3);
        add(l4);
        add(l5);
        add(l6);
    }

    @Override
    public void repaint() {
        List<String> userList = currentClient.getUserList();
        System.out.println("The list size is: " + userList.size());
        if (userList.size() == 1) {
            System.out.println("Yes! its 1!");
            l4.setText(userList.get(0));
        } else if (userList.size() == 2) {
            System.out.println("Yes! its 2!");
            l4.setText(userList.get(0));
            l5.setText(userList.get(1));
        } else if (userList.size() == 3) {
            System.out.println("Yes! its 3!");
            l4.setText(userList.get(0));
            l5.setText(userList.get(1));
            l6.setText(userList.get(2));
        }

    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
}
