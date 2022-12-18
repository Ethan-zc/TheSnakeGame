package com.seven.zichen.snakegame.client;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import com.seven.zichen.snakegame.utilities.GameOptions;
import com.seven.zichen.snakegame.utilities.Pair;
import com.seven.zichen.snakegame.utilities.Point;

import javax.swing.*;

class HandleReturnedGrid implements Runnable {
	private ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> gridJobs;
	private DrawGame gameDisplay;
	private byte number;
	private HandleInputDirection hid;
	private Timer countDown;
	private int gameTime;

	protected HandleReturnedGrid(ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> jobs,
								 DrawGame display, byte number, HandleInputDirection hid) {
		this.gridJobs = jobs;
		this.gameDisplay = display;
		this.number = number;
		this.hid = hid;
		this.gameTime = GameOptions.gameTime + 5;
	}

	@Override
	public void run() {
		try {
			this.countDown = new Timer(1000, actionEvent -> {
				gameTime--;
				if (gameTime == 0) {
					countDown.stop();
				}
			});
			countDown.start();
			while (true) {
				Pair<HashMap<Byte, Snake>, Point> req = gridJobs.take();

				byte[][] backGrid = calcReturnedGrid(req.obj1);
				backGrid[req.obj2.x][req.obj2.y] = DrawGame.APPLE;
				gameDisplay.swap(backGrid, gameTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private byte[][] calcReturnedGrid(HashMap<Byte, Snake> req) {

		byte[][] grid = new byte[GameOptions.gridSize][GameOptions.gridSize];
		for (Snake s : req.values())
			paintSnake(s, grid);
		return grid;
	}
	
	private void paintSnake(Snake snake, byte[][] grid) {
		byte color = DrawGame.OTHER;
		if(snake.number == number){
			color = DrawGame.USER;
			hid.setDirection(snake.direction);
		}
		for (Point p : snake.points)
			grid[p.x][p.y] = color;
	}
}
