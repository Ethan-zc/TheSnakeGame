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

        setSize(400, 300);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Player Login");

        l1.setForeground(Color.blue);

        l1.setFont(new Font("Serif", Font.BOLD, 20));

        l2 = new JLabel("Enter User Name:");

        l3 = new JLabel("Enter Password:");

        tf1 = new JTextField();

        p1 = new JPasswordField();

        btn1 = new JButton("Submit");

        l1.setBounds(100, 30, 400, 30);

        l2.setBounds(80, 70, 200, 30);

        l3.setBounds(80, 110, 200, 30);

        tf1.setBounds(300, 70, 200, 30);

        p1.setBounds(300, 110, 200, 30);

        btn1.setBounds(150, 160, 100, 30);

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


        } catch (Exception ex) {

            System.out.println(ex);

        }
    }

}
