package com.seven.zichen.snakegame;

import com.seven.zichen.snakegame.games_handler.GH_Manager;
import com.seven.zichen.snakegame.socket.WaitingRoom;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.seven.zichen.snakegame.dao")
public class TheSnakeGameApplication {

	public static int NUM_OF_USERS = 2;
	public static String localhostIP = "192.168.1.156";

	public static void main(String[] args) throws IOException {
//		new SpringApplicationBuilder(TheSnakeGameApplication.class).headless(false).run(args);
//		new WaitingRoom();
//		new SpringApplicationBuilder(TheSnakeGameApplication.class).headless(false).run(args);
		System.out.println("Server initializing...");
		new WaitingRoom();
		new SpringApplicationBuilder(TheSnakeGameApplication.class).headless(false).run(args);
		Thread GH=new Thread(new GH_Manager(5757, 5656, "Snakes Server", 2000, NUM_OF_USERS));
		GH.start();
	}

}
