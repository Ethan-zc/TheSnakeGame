package com.seven.zichen.snakegame.game;

import com.seven.zichen.snakegame.utilities.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

public class Game extends Thread {
	public Snake s1;
	public Snake s2;
	public Snake s3;
	public Snake s4;
	
	public volatile boolean waitForClients;
	private String gameName;
	private int maxPlayers;
	public LinkedList<Snake> remainingSnakes;
	public HashMap<Integer,Snake> snakes;
	public HashSet<Snake> snakesAtStart;
	public Apple apple;
	public GameManager manager;
	
	public Game(int maxPlayers, int inputPort) throws IOException {
		this.maxPlayers = maxPlayers;
		this.gameName = "Test";
		
		s1 = new Snake(new Point(0,0),15,(byte)1, "Test");
		s2 = new Snake(new Point(40,40),15,(byte)2, "Test");
		s3 = new Snake(new Point(80,80),15,(byte)3, "Test");
		s4 = new Snake(new Point(120,120),15,(byte)4, "Test");
		
		
		snakes = new HashMap<>();
		remainingSnakes = new LinkedList<>();
		remainingSnakes.add(s1);
		remainingSnakes.add(s2);
		remainingSnakes.add(s3);
		remainingSnakes.add(s4);
		
		snakesAtStart = new HashSet<>();
		
		this.manager = new GameManager(this, inputPort);
		waitForClients=true;
		
		games.add(this);
	}

	public void addClient(String address, int port) throws IOException, InterruptedException {
		if (this.hasRoom() && waitForClients){
			Snake snake = remainingSnakes.removeFirst();
			snakesAtStart.add(snake);
			Client c = new Client(address, port, snake.id, snake.name);
			this.snakes.put(c.id, snake);
			
			ArrayBlockingQueue<Job> out_communicator = new ArrayBlockingQueue<>(100);
			this.manager.outCommunicator.put(c, out_communicator);
			Thread t=new Thread(new RunnableOutput(c.address, c.listeningPort, out_communicator, "G", manager));
			t.start();

			
			Job j = new Job(Job.Type.SEND_GAME_INFO);
			j.id(snake.id);
			j.port(manager.inputPort);
			out_communicator.put(j);
			resetApple();
			if(!this.hasRoom()) waitForClients=false;
		}
			
	}
	
	public void resetApple(){
		this.apple=new Apple();
	}

	public boolean hasRoom() {
		return snakes.size() < maxPlayers;
	}

	@Override
	public String toString() {
		String ret = gameName + ":\n";
		for (Snake c : snakes.values()) {
			ret += "\t> " + c + "\n";
		}
		return ret;
	}

	@Override
	public void run() {
		manager.run();
	}

	public static LinkedList<Game> games = new LinkedList<>();
	
	

	public static Game getGameForANewPlayer() {
		for (Game game : games) {
			if (game.hasRoom() && game.waitForClients)
				return game;
		}
		return null;
	}

}
