package com.seven.zichen.snakegame.client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import com.seven.zichen.snakegame.utilities.GameOptions;

import javax.swing.*;

//calculate new grid to display from snake hashmaps
class HandleReturnedGrid implements Runnable {
	private ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> gridJobs;
	private DrawGame gameDisplay;
	private byte thisNumber;
	private HandleInputDirection hid;
	private Timer countDown;
	private int gameTime;

	protected HandleReturnedGrid(ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> jobs, DrawGame display, byte number, HandleInputDirection hid){
		this.gridJobs = jobs;
		this.gameDisplay = display;
		this.thisNumber = number;
		this.hid = hid;
		this.gameTime = GameOptions.gameTime + 5;
	}

	@Override
	public void run() {
		try {
			this.countDown = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					gameTime--;
					if (gameTime == 0) {
						countDown.stop();
					}
				}
			});
			countDown.start();
			while (true) {
				Pair<HashMap<Byte, Snake>, Point> req = gridJobs.take();

				byte[][] backGrid = calcReturnedGrid(req.a);
				backGrid[req.b.x][req.b.y] = DrawGame.APPLE;
				gameDisplay.swap(backGrid, gameTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private byte[][] calcReturnedGrid(HashMap<Byte, Snake> req) {

		byte[][] grid = new byte[GameOptions.gridSize][GameOptions.gridSize];
		for (Snake s : req.values())
			afficher(s, grid);
		return grid;
	}
	
	private void afficher(Snake snake, byte[][] grid) {
		// System.out.print("On regarde le serpent numero " + s.numero + "... ");
		byte color = DrawGame.FULL;
		if(snake.number == thisNumber){
			// System.out.println("je vais dans la direction " + s.direction);
			color = DrawGame.USER;
			hid.setDirection(snake.direction);
		}
		for (Point p : snake.points)
			grid[p.x][p.y] = color;
	}
}
