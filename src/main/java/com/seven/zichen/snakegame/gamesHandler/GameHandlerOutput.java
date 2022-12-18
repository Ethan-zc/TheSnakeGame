package com.seven.zichen.snakegame.gamesHandler;

import com.seven.zichen.snakegame.utilities.BufferHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class GameHandlerOutput implements Runnable {
	private int outputPort;
	private String serverName;
	private int inputPort;
	private long interval;

	public GameHandlerOutput(int outputPort, String serverName, long interval,
							 int inputPort) {
		this.outputPort = outputPort;
		this.serverName = serverName;
		this.inputPort = inputPort;
		this.interval = interval;
	}

	@Override
	public void run() {
		try {
			DatagramChannel clientSocket = DatagramChannel.open();
			clientSocket.socket().setBroadcast(true);
			clientSocket.socket().bind(new InetSocketAddress(0));
			while (true) {
				clientSocket.send(
						BufferHandler.helloClient(serverName, inputPort),
						new InetSocketAddress("255.255.255.255", outputPort));
				Thread.sleep(interval);// broadcast time interval
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
