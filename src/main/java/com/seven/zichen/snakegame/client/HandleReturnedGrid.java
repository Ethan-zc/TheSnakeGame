package com.seven.zichen.snakegame.client;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import com.seven.zichen.snakegame.utilities.GameOptions;

//calculate new grid to display from snake hashmaps
class HandleReturnedGrid implements Runnable {
	private ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> gridJobs;
	private DrawGame gameDisplay;
	private byte thisNumber;
	private HandleInputDirection hid;

	protected HandleReturnedGrid(ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> jobs, DrawGame display, byte number, HandleInputDirection hid){
		this.gridJobs = jobs;
		this.gameDisplay = display;
		this.thisNumber = number;
		this.hid = hid;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Pair<HashMap<Byte, Snake>, Point> req = gridJobs.take();

				byte[][] backGrille = calculBackGrille(req.a);
				backGrille[req.b.x][req.b.y] = DrawGame.APPLE;
				gameDisplay.swap(backGrille);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private byte[][] calculBackGrille(HashMap<Byte, Snake> req) {
		// System.out.print("On appele calculBackGrille... ");
		byte[][] grille = new byte[GameOptions.gridSize][GameOptions.gridSize];
		for (Snake s : req.values())
			afficher(s, grille);
		return grille;
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
