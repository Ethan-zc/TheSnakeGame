package com.seven.zichen.snakegame.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import com.seven.zichen.snakegame.TheGameClient;

public class SignUp extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4;
    JTextField tf1;
    JButton btn1, btn2, btn3;
    JPasswordField p1, p2;
    private String userName;
    private String pwd;
    private String ipAddr = "";

    public SignUp() {
        this.ipAddr = TheGameClient.localhostIP;
        setVisible(true);
        setBounds(400, 100, 400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SignUp");
        getContentPane().setBackground(Color.white);

        l1 = new JLabel("Player SignUp");
        l1.setForeground(Color.black);
        l1.setFont(new Font("Bayon", Font.BOLD, 26));

        l2 = new JLabel("User Name:");
        l2.setForeground(Color.black);
        l2.setFont(new Font("Bayon", Font.BOLD, 16));

        l3 = new JLabel("Create Passowrd:");
        l3.setForeground(Color.black);
        l3.setFont(new Font("Bayon", Font.BOLD, 16));

        l4 = new JLabel("Confirm Password:");
        l4.setForeground(Color.black);
        l4.setFont(new Font("Bayon", Font.BOLD, 16));

        tf1 = new JTextField();
        tf1.setBackground(Color.decode("#F5F5F5"));

        p1 = new JPasswordField();
        p1.setBackground(Color.decode("#F5F5F5"));

        p2 = new JPasswordField();
        p2.setBackground(Color.decode("#F5F5F5"));

        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        btn3 = new JButton("Log in");
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        l1.setBounds(118, 28, 200, 47);
        l2.setBounds(34, 70, 200, 30);
        l3.setBounds(34, 110, 200, 30);
        l4.setBounds(34, 150, 200, 30);
        tf1.setBounds(190, 70, 150, 31);
        p1.setBounds(190, 110, 150, 31);
        p2.setBounds(190, 150, 150, 31);

        btn1.setBounds(80, 200, 100, 30);
        btn2.setBounds(200, 200, 100, 30);
        btn3.setBounds(140, 230, 100, 30);
        add(l1);
        add(l2);
        add(tf1);
        add(l3);
        add(l4);
        add(p1);
        add(p2);
        add(btn1);
        add(btn2);
        add(btn3);
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
                    this.pwd = s8;

                    URL url = new URL("http://" + this.ipAddr + ":8080/account/register");
                    Map<String,Object> params = new LinkedHashMap<>();
                    params.put("accname", userName);
                    params.put("pwd", pwd);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    BufferedReader br = null;
                    if (100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }
                    String strCurrentLine = br.readLine();
                    System.out.print(strCurrentLine);
                    if (strCurrentLine.equals("Success!")) {
                        JOptionPane.showMessageDialog(btn1, "Data Saved Successfully");
                        this.dispose();
                        new SignIn();
                    } else {
                        JOptionPane.showMessageDialog(btn1, "Error, please try again");
                    }
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
            else
            {
                JOptionPane.showMessageDialog(btn1, "Password Does Not Match");
            }
        }
        else if (e.getSource() == btn2) {
            tf1.setText("");
            p1.setText("");
            p2.setText("");
        }
        else {
            this.dispose();
            new SignIn();
        }
    }
}
