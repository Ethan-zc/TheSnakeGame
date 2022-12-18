package com.seven.zichen.snakegame.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import com.seven.zichen.snakegame.TheGameClient;

public class SignIn extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;
    String ipAddr = "";

    public SignIn() {
        this.ipAddr = TheGameClient.localhostIP;
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
            URL url = new URL("http://" + this.ipAddr + ":8080/account/login");
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("accname", str1);
            params.put("pwd", str2);

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
            if (strCurrentLine.equals(str1)) {
                JOptionPane.showMessageDialog(btn1, "Logged in");
                this.dispose();
                new WelcomePage(str1);
            } else {
                JOptionPane.showMessageDialog(btn1, "Error, please try again");
            }
        } catch (Exception ex) {

            System.out.println(ex);

        }
    }
}
