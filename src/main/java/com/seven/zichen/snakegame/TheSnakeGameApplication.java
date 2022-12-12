package com.seven.zichen.snakegame;

import com.seven.zichen.snakegame.socket.WaitingRoom;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.seven.zichen.snakegame.dao")
public class TheSnakeGameApplication {

	public static void main(String[] args) throws IOException {
		new SpringApplicationBuilder(TheSnakeGameApplication.class).headless(false).run(args);
		new WaitingRoom();
	}

}
