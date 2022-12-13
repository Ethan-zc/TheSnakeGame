package com.seven.zichen.snakegame;

import com.seven.zichen.snakegame.models.GameFrame;
import com.seven.zichen.snakegame.models.GamePanel;
import com.seven.zichen.snakegame.models.SignUp;
import com.seven.zichen.snakegame.models.SignIn;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.ArrayList;
import java.util.List;

public class TheGameClient {

    public static void main(String[] args) {
//        new SignUp();
        List<String> test = new ArrayList<>();
        test.add("Haha");
        test.add("Yes");
        GamePanel gamePanel = new GamePanel(test);
        for (int i = 0; i < gamePanel.getSnakes().size(); i++) {
            new GameFrame(gamePanel);
        }
    }
}
