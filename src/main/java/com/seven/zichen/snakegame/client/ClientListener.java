package com.seven.zichen.snakegame.client;

import com.seven.zichen.snakegame.utilities.Pair;
import com.seven.zichen.snakegame.utilities.Point;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

// TEST OK
public class ClientListener implements Runnable {
	private ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> gridJobs;
	private DatagramChannel listenerChannel;
	private Client client;
	private boolean dirNotStarted = true;

	protected ClientListener(ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> jobs, short listeningPort, Client c) {
		gridJobs = jobs;
		client = c;

		try {
			listenerChannel = DatagramChannel.open();
			System.out.println("The port is: " + listeningPort);
			listenerChannel.socket().bind(new InetSocketAddress(listeningPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			boolean gameOver = false;
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				listenerChannel.receive(buffer);
				buffer.flip();
				byte type = buffer.get();
				switch (type) {
				case 0:
					if (client.notReceivedPortGame) {
						client.notReceivedPortGame = false;
						short gamePort = buffer.getShort();
						byte number = buffer.get();

						client.startGamePanel(number);
						client.startSpeaker(number, gamePort);
					}
					break;
				case 1:
					client.print("<HTML><h2>Game will start in " + buffer.get() + " seconds</h2></HTML>");
					break;
				case 2:
					if (dirNotStarted) {
						client.startHandleDirection();
						dirNotStarted = false;
						client.print("");
					}
					decodeSnakes(buffer);
					break;
				case 3:
					if (!gameOver) {
						gameOver = true;
						client.gameOver();
						client.print(readFinalBuffer(buffer));
					}
					break;
				default:
					throw new Exception("Game server is corrupted");
				}
				buffer.clear();
			}
		} catch (Exception e) {
		}
	}

	private String readFinalBuffer(ByteBuffer buffer) {
		String s = "<HTML><h2>Game Over!</h2>";
		byte nbSnakes = buffer.get();
		for (int i = 0; i < nbSnakes; i++) {
			byte num = buffer.get();
			short score = buffer.getShort();
			s += "<h3>Player " + client.numToName(num) + " got " + score + " points</h3>";
		}
		return s + "</HTML>";
	}

	private void decodeSnakes(ByteBuffer buffer) throws Exception {
		Pair<HashMap<Byte, Snake>, Point> req = decodeBufferToGame(buffer);
		while (gridJobs.size() > 0)
			gridJobs.poll();
		gridJobs.put(req);
	}

	private static Pair<HashMap<Byte, Snake>, Point> decodeBufferToGame(ByteBuffer buf) throws Exception {
		HashMap<Byte, Snake> snakes = new HashMap<>();
		try {
			byte nbSnakes = buf.get();
			for (int i = 0; i < nbSnakes; i++) {
				byte numSnake = buf.get();
				LinkedList<Point> curSnake = new LinkedList<>();
				Point cur = new Point(buf.get(), buf.get());
				curSnake.add(cur);
				byte nbDir = buf.get();
				byte dir = -1;
				for (int j = 0; j < nbDir; j++) {
					dir = buf.get();
					byte length = buf.get();
					int k = j == 0 ? 1 : 0;
					for (; k < length; k++) {
						Point tmp = new Point(cur.x + (dir % 2 == 0 ? dir - 1 : 0),
								cur.y + (dir % 2 == 1 ? dir - 2 : 0));
						cur = tmp;
						curSnake.add(cur);
					}
				}
				Snake c = new Snake(dir, numSnake, curSnake);
				snakes.put(numSnake, c);
			}
			Point point = new Point(buf.get(), buf.get());
			return new Pair<>(snakes, point);
		} catch (Exception e) {
		}
		throw new Exception("The message from the server is incorrectly decoded");
	}
}