package com.seven.zichen.snakegame.utilities;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

public class RunnableInput extends UDPListener implements Runnable {
	private ByteBuffer buffer;
	private ArrayBlockingQueue<Job> communicator;
	String parentPrefix;

	public RunnableInput(int port, ArrayBlockingQueue<Job> communicator,
						 String prefix) throws IOException {
		super(port);
		buffer = ByteBuffer.allocate(1000);
		this.communicator = communicator;
		this.parentPrefix = prefix;
	}

	@Override
	public void run() {
		while (true) {
			try {
				InetSocketAddress remote = listen(buffer);
				buffer.flip();
				Job j = new Job(buffer, remote.getHostName());
				communicator.put(j);
				buffer.clear();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
