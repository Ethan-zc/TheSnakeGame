package com.seven.zichen.snakegame.game;

import com.seven.zichen.snakegame.utilities.GameOptions;
import com.seven.zichen.snakegame.utilities.Job;
import com.seven.zichen.snakegame.utilities.Snake;

import java.util.LinkedList;

public class G_MoveSnakes implements Runnable {
	private Game thisGame;

	public G_MoveSnakes(Game g) {
		thisGame = g;
	}

	@Override
	public void run() {
		LinkedList<Snake> newLoosers = new LinkedList<Snake>();
		int counter = 0;
		while (!thisGame.manager.gameOver) {
			synchronized (thisGame.snakes) {
				for (Snake snake : thisGame.snakes.values()) {
					Snake killer = snake.isInCollision(thisGame.snakes);
					if (killer != null) {
						
						newLoosers.add(snake);
						if (snake != killer)
							killer.score += 1000;
					} else {
						if (snake.isInCollision(thisGame.apple.a)) {
							snake.grow();
							snake.score += 100;
							thisGame.resetApple();
							counter = 0;
						} else {
							snake.move();
							snake.score+=1;
						}
					}

				}
			}
			synchronized (thisGame.snakes) {
				while (!newLoosers.isEmpty()) {
					
					thisGame.snakes.remove(newLoosers.poll().id & 255);
					
					if((thisGame.snakesAtStart.size() > 1 && thisGame.snakes.size() < 2) || thisGame.snakes.size() < 1){
						System.out.println("Game Over");
						synchronized(thisGame.manager){
							thisGame.manager.gameToBeOver = true;
							try {
								thisGame.manager.in_communicator.put(new Job(Job.Type.UNKNOWN));
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
				thisGame.resetApple();
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
