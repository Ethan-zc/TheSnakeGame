package com.seven.zichen.snakegame.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignIn extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;

    public SignIn() {
        setTitle("Login");

        setVisible(true);

        setSize(400, 270);
        getContentPane().setBackground(Color.white);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Player Login");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 26));

        l2 = new JLabel("User Name:");
        l2.setForeground(Color.black);
        l2.setFont(new Font("Bayon", Font.BOLD, 16));

        l3 = new JLabel("Password:");
        l3.setForeground(Color.black);
        l3.setFont(new Font("Bayon", Font.BOLD, 16));

        tf1 = new JTextField();
        tf1.setBackground(Color.decode("#F5F5F5"));

        p1 = new JPasswordField();
        p1.setBackground(Color.decode("#F5F5F5"));

        btn1 = new JButton("Submit");

        l1.setBounds(118, 28, 200, 47);

        l2.setBounds(34, 70, 200, 30);

        l3.setBounds(34, 110, 200, 30);

        tf1.setBounds(130, 70, 188, 30);

        p1.setBounds(130, 110, 188, 30);

        btn1.setBounds(130, 160, 100, 30);

        add(l1);

        add(l2);

        add(tf1);

        add(l3);

        add(p1);

        add(btn1);

        btn1.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFrame f1 = new JFrame();

        JLabel l, l0;

        String str1 = tf1.getText();

        char[] p = p1.getPassword();

        String str2 = new String(p);

        try {
            //Login
            this.dispose();
            new GameFrame();

        } catch (Exception ex) {

            System.out.println(ex);

        }
    }

}
