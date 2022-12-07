package com.seven.zichen.snakegame.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4;
    JTextField tf1;
    JButton btn1, btn2;
    JPasswordField p1, p2;
    private String userName;
    private String psw;

    public SignUp() {
        setVisible(true);
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SignUp");
        l1 = new JLabel("Player SignUp");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l2 = new JLabel("User Name:");
        l3 = new JLabel("Create Passowrd:");
        l4 = new JLabel("Confirm Password:");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        p2 = new JPasswordField();
        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        l4.setBounds(80, 150, 200, 30);
        tf1.setBounds(300, 70, 188, 31);
        p1.setBounds(300, 110, 188, 31);
        p2.setBounds(300, 150, 188, 31);

        btn1.setBounds(100, 200, 100, 30);
        btn2.setBounds(220, 200, 100, 30);
        add(l1);
        add(l2);
        add(tf1);
        add(l3);
        add(l4);
        add(p1);
        add(p2);
        add(btn1);
        add(btn2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1)
        {
            int x = 0;
            String s1 = tf1.getText();
            char[] s3 = p1.getPassword();
            char[] s4 = p2.getPassword();
            String s8 = new String(s3);
            String s9 = new String(s4);
            if (s8.equals(s9))
            {
                try
                {
                    //create user
                    this.userName = s1;
                    this.psw = s8;
                    JOptionPane.showMessageDialog(btn1, "Data Saved Successfully");
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(btn1, "Password Does Not Match");
            }
        }
        else
        {
            tf1.setText("");
            p1.setText("");
            p2.setText("");
        }
    }
}
