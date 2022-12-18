package com.seven.zichen.snakegame.client;

import com.seven.zichen.snakegame.utilities.Pair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.BlockingDeque;

public class ClientSender implements Runnable {
	public InetSocketAddress server;
	public BlockingDeque<Pair<Byte,Byte>> directionJobs;
	private short gamePort;
	private byte number;
	public byte id;

	public ClientSender(InetSocketAddress server, BlockingDeque<Pair<Byte,Byte>> jobs, short gp, byte num) {
		this.server = server;
		this.directionJobs = jobs;
		gamePort = gp;
		number = num;
		id = 0;
	}

	@Override
	public void run() {
		try {
			DatagramChannel speakerChannel = DatagramChannel.open();
			speakerChannel.socket().bind(new InetSocketAddress(0));
			InetSocketAddress remote = new InetSocketAddress(server.getAddress(), gamePort);
			while (true) {
				synchronized (directionJobs) {
					Pair<Byte,Byte> dirId = directionJobs.peek();
					if (dirId==null) {
						directionJobs.wait();
					}
					else speakerChannel.send(changeDirection(number, dirId), remote);
					Thread.sleep(5);
				}
			}
		} catch (IOException | InterruptedException e) {
		}
	}

	public ByteBuffer changeDirection(byte number, Pair<Byte,Byte> dirId) {
		ByteBuffer res = ByteBuffer.allocate(4);
		res.put((byte) 2);
		res.put(dirId.obj2);
		res.put(number);
		res.put(dirId.obj1);
		res.flip();
		return res;
	}
}
