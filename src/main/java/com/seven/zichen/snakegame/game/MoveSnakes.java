package com.seven.zichen.snakegame.game;

import com.seven.zichen.snakegame.utilities.GameOptions;
import com.seven.zichen.snakegame.utilities.Job;
import com.seven.zichen.snakegame.utilities.Snake;

import java.util.LinkedList;

public class MoveSnakes implements Runnable {
	private Game game;

	public MoveSnakes(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		LinkedList<Snake> newLosers = new LinkedList<Snake>();
		int counter = 0;
		while (!game.manager.gameOver) {
			synchronized (game.snakes) {
				for (Snake snake : game.snakes.values()) {
					Snake killer = snake.isInCollision(game.snakes);
					if (killer != null) {

						newLosers.add(snake);
						if (snake != killer)
							killer.score += 1000;
					} else {
						if (snake.isInCollision(game.apple.a)) {
							snake.grow();
							snake.score += 100;
							game.resetApple();
							counter = 0;
						} else {
							snake.move();
							snake.score += 1;

						}
					}

				}
			}
			synchronized (game.snakes) {
				while (!newLosers.isEmpty()) {

					game.snakes.remove(newLosers.poll().id & 255);
					
					if((game.snakesAtStart.size() > 1 && game.snakes.size() < 2) || game.snakes.size() < 1){
						System.out.println("Game Over");
						synchronized(game.manager){
							game.manager.gameToBeOver = true;
							try {
								game.manager.inCommunicator.put(new Job(Job.Type.UNKNOWN));
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("Game to be over set to true");
						}
						
					} else System.out.println("game is still on");
				}
			}
			if (counter % GameOptions.appleLifeTime == 0)
				game.resetApple();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
		}

	}
}
