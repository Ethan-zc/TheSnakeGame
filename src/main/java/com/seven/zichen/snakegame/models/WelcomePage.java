package com.seven.zichen.snakegame.models;

import com.seven.zichen.snakegame.TheGameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WelcomePage extends JFrame implements ActionListener {
    private JLabel l1;
    private JButton btn1, btn2;
    private String userName;
    public WelcomePage(String userName) {
        this.userName = userName;
        setTitle("Welcome!");

        setVisible(true);

        setBounds(400, 100, 400, 270);
        getContentPane().setBackground(Color.white);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Hi " + userName + "!");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 26));

        btn1 = new JButton("Start Game");
        btn2 = new JButton("Show Leaderboard");

        l1.setBounds(130, 28, 200, 47);

        btn1.setBounds(115, 120, 150, 30);
        btn2.setBounds(115, 160, 150, 30);
        btn1.addActionListener(this);
        btn2.addActionListener(this);

        getContentPane().add(l1);

        getContentPane().add(btn1);
        getContentPane().add(btn2);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn1) {
            this.dispose();
//            new GameFrame();
            WaitingPanel wp = new WaitingPanel(userName);
            Timer timer = new Timer(1000, new UpdateWaitingPanel(wp));
            timer.start();
        } else if (actionEvent.getSource() == btn2) {
            this.dispose();
            String response = null;
            try {
                response = LeaderBoard.getLeaderBoardData(TheGameClient.localhostIP);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new LeaderBoard(response, userName);
        }
    }

    public class UpdateWaitingPanel implements ActionListener {

        public WaitingPanel wp;

        public UpdateWaitingPanel(WaitingPanel wp) {
            this.wp = wp;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            wp.invalidate();
            wp.validate();
            wp.repaint();
        }
    }

}
