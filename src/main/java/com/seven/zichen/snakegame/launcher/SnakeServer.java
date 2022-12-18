package com.seven.zichen.snakegame.launcher;

import com.seven.zichen.snakegame.gamesHandler.GameHandlerManager;

import java.io.IOException;

public class SnakeServer {

	public static void main(String[] args) throws IOException {
		int nbJoueur = (args.length > 0 ? Integer.parseInt(args[0]) : 1);
		System.out.println("Server initializing...");
		Thread GH = new Thread(new GameHandlerManager(5757, 5656, "Snakes Server", 2000, nbJoueur));
		GH.start();
	}

}
