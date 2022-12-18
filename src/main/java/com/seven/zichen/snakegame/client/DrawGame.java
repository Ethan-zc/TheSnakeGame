package com.seven.zichen.snakegame.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ArrayBlockingQueue;
import com.seven.zichen.snakegame.TheGameClient;

import com.seven.zichen.snakegame.models.LeaderBoard;
import com.seven.zichen.snakegame.utilities.GameOptions;

@SuppressWarnings("serial")
public class DrawGame extends JComponent implements KeyListener {
	protected static final byte BLANK = 0, FULL = 1, APPLE = 2, USER = 3, cellSize = 10;
	private byte[][] grid;
	private JFrame gameGraph;
	private ArrayBlockingQueue<Byte> inputDirection;
	private final JLabel l = new JLabel();
	private final JButton btn = new JButton("Show Leaderboard");
	private boolean isGameOver;

	protected void swap(byte[][] returnedGrid) {
		try {
			EventQueue.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					grid = returnedGrid;
					paintImmediately(0, 0, getWidth(), getHeight());
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected DrawGame(int num, ArrayBlockingQueue<Byte> dir) {
		grid = new byte[GameOptions.gridSize][GameOptions.gridSize];
		setGraph(num);
		inputDirection = dir;
		isGameOver = false;
	}

	void gameOver() {
		this.isGameOver = true;
	}

	private void setGraph(int num) {
		gameGraph = new JFrame("Snake Game");
		addKeyListener(this);
		gameGraph.setBounds(400, 100, (GameOptions.gridSize + 1) * cellSize, (GameOptions.gridSize + 3) * cellSize);
		l.setBounds(GameOptions.gridSize * cellSize / 4,GameOptions.gridSize * cellSize / 2, GameOptions.gridSize * cellSize, GameOptions.gridSize * cellSize / 4);
		gameGraph.add(l);
		btn.setBounds(220, 540, 150, 30);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameGraph.dispose();
				String response = null;
				try {
					response = LeaderBoard.getLeaderBoardData(TheGameClient.localhostIP);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
				new LeaderBoard(response, ""); // TODO: Pass current username
			}
		});
		btn.setVisible(false);
		gameGraph.add(btn);
		gameGraph.add(this);
		setFocusable(true);
		requestFocusInWindow();
		gameGraph.setVisible(true);
		setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < GameOptions.gridSize; i++)
			for (int j = 0; j < GameOptions.gridSize; j++)
				fill(g, i, j, grid[i][j]);
		// this.remove(a);
	}

	private void fill(Graphics g, int i, int j, byte color) {
		g.setColor((color == BLANK) ? Color.WHITE
				: (color == FULL) ? Color.BLACK : (color == APPLE) ? Color.RED
						: (color == USER) ? Color.blue : Color.white);
		g.fillRect(i * cellSize + cellSize / 16, j * cellSize + cellSize / 16,
				cellSize - cellSize / 8, cellSize - cellSize / 8);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		byte keycode = (byte) (e.getKeyCode()-37);
		if(keycode >= 0 && keycode < 4)
			try{
				inputDirection.add(keycode);
			}catch(IllegalStateException t){
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void print(String string) {
		paintImmediately(0, 0, getWidth(), getHeight());
		l.setText(string);
		if (isGameOver) {
			btn.setVisible(true);
		}
	}
}
