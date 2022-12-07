package com.seven.zichen.snakegame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.seven.zichen.snakegame.dao")
public class TheSnakeGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheSnakeGameApplication.class, args);
	}

}
