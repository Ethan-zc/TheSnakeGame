package com.seven.zichen.snakegame.utilities;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPListener {
	private InetSocketAddress local;
	private DatagramChannel channel;
	
	public UDPListener(int port) throws IOException{
		this.channel = DatagramChannel.open();
		this.local=new InetSocketAddress(port);
		this.channel.socket().bind(local);
	}
	
	public InetSocketAddress listen(ByteBuffer b) throws IOException{
		InetSocketAddress remote  = (InetSocketAddress) this.channel.receive(b);
		return remote;
	}
	
	
}
