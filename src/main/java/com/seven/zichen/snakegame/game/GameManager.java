package com.seven.zichen.snakegame.game;

import com.seven.zichen.snakegame.utilities.*;

import java.io.IOException;
import java.util.HashMap;
import javax.swing.Timer;
import java.util.concurrent.ArrayBlockingQueue;

public class GameManager implements Runnable {
	public Game thisGame;

	public volatile boolean sendGameInfo;
	public volatile boolean sendTimer;
	public volatile boolean gameToBeOver;
	public volatile boolean gameOver;
	public volatile boolean sendScore;

	private Thread input;
	public int inputPort;
	public ArrayBlockingQueue<Job> inCommunicator;

	public HashMap<Client, ArrayBlockingQueue<Job>> outCommunicator;

	private Timer countDown;
	private int gameTime;

	public GameManager(Game g, int inputPort)
			throws IOException {

		System.out.println("GameManager has been initialized:");

		thisGame = g;
		inCommunicator = new ArrayBlockingQueue<>(100);
		this.inputPort = inputPort;
		input = new Thread(new RunnableInput(inputPort, inCommunicator, "G"));
		System.out.println("input Thread initialized on port " + inputPort);
		outCommunicator = new HashMap<>();

		sendGameInfo = true;
		sendTimer = false;
		gameToBeOver = false;
		gameOver = false;
		sendScore = false;
		gameTime = GameOptions.gameTime;
	}

	@Override
	public void run() {
		input.start();

		try {
			sendGameInfo = true;
			while (sendGameInfo) {
				sendGameInfo = thisGame.hasRoom();
			}

			sendTimer = true;
			for (byte timer = 5; timer > 0; timer--) {
				for (Client c : outCommunicator.keySet()) {
					Job j = new Job(Job.Type.SEND_TIMER);
					j.timer(timer);
					outCommunicator.get(c).put(j);
				}
				Thread.sleep(950);// a bit less than a second
			}
			sendTimer = false;

			Thread.sleep(1000);
			gameOver = false;

			for (Client c : outCommunicator.keySet()) {
				Job j = new Job(Job.Type.SEND_POSITIONS);
				j.id(c.id);
				j.port(this.inputPort);
				outCommunicator.get(c).put(j);
			}

			Thread moveSnakes;
			moveSnakes = new Thread(new MoveSnakes(thisGame));
			moveSnakes.start();

			byte id = -1;
			gameToBeOver = false;
			countDown = new Timer(1000, ae -> {
				gameTime--;
				if (gameTime == 0) {
					gameToBeOver = true;
					gameOver = true;
					countDown.stop();
				}
			});
			countDown.start();

			while (!gameOver) {
				
				Job j = inCommunicator.take();
				if(gameToBeOver){
					break;
				}

				if (id == j.jobId()
						|| !thisGame.snakes.containsKey(j.id() & 255)) {
					continue;
				}

				else
					System.out.println(j.jobId());
				id = j.jobId();
				synchronized (thisGame.snakes) {
					Snake s = thisGame.snakes.get(j.id() & 255);
					s.direction(j.direction());

				}
			}

			gameOver = true;
			System.out.println(">>>>>>>>>>>>>> GameOver <<<<<<<<<<<<<<<");

			sendScore = true;
			for (Client c : outCommunicator.keySet()) {
				Job j = new Job(Job.Type.SEND_SCORES);
				j.id(c.id);
				j.snakes = thisGame.snakesAtStart;
				outCommunicator.get(c).put(j);
			}
			Thread.sleep(2000);
			sendScore = false;


		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
