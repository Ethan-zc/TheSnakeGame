package com.seven.zichen.snakegame;

import com.seven.zichen.snakegame.models.GameFrame;
import com.seven.zichen.snakegame.models.GamePanel;
import com.seven.zichen.snakegame.models.SignUp;
import com.seven.zichen.snakegame.models.SignIn;
import com.seven.zichen.snakegame.socket.WaitingRoom;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.ArrayList;
import java.util.List;

public class TheGameClient {

    public static String localhostIP = "192.168.1.156";

    public static void main(String[] args) {

        new SignUp();

    }
}
