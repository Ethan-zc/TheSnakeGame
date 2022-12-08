package com.seven.zichen.snakegame;

import com.seven.zichen.snakegame.models.SignIn;
import com.seven.zichen.snakegame.models.SignUp;
import org.mybatis.spring.annotation.MapperScan;
import com.seven.zichen.snakegame.models.GameFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@MapperScan("com.seven.zichen.snakegame.dao")
public class TheSnakeGameApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TheSnakeGameApplication.class).headless(false).run(args);
//		new SignUp();
	}

}
