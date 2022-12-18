package com.seven.zichen.snakegame.client;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class Client{
	private InetSocketAddress server;
	private ArrayBlockingQueue<Pair<HashMap<Byte, Snake>, Point>> gridJobs;
	private BlockingDeque<Pair<Byte,Byte>> directionIdJobs;
	protected volatile boolean notReceivedPortGame = true;
	private String serverName;
	private HandleInputDirection hid;
	private DrawGame gameDisplay = null;
	private String userName;

	private byte number;

	public Client(String username) throws Exception{
		this.userName = username;
		startListener((short) 5959, bufferWaitPlayerServer(5656));
	}
	String numToName(byte num) {
		if (number == num) {
			return userName;
		}
		return "snake";
	}

	String getUserName() {
		return userName;
	}

	private void startListener(short listeningPort, short sendingPort) throws Exception {
		gridJobs = new ArrayBlockingQueue<>(1);
		new Thread(new ClientListener(gridJobs, listeningPort, this)).start();
		envoieServer(listeningPort, sendingPort, server);
	}

	void startGamePanel(byte number) {
		this.number = number;
		directionIdJobs = new LinkedBlockingDeque<>(5);
		ArrayBlockingQueue<Byte> directionJobs = new ArrayBlockingQueue<>(5);
		hid = new HandleInputDirection(directionIdJobs, directionJobs);
		gameDisplay = new DrawGame(number, directionJobs);
		new Thread(new HandleReturnedGrid(gridJobs, gameDisplay, number, hid)).start();
	}

	void startSpeaker(byte number, short gamePort) {
		new Thread(new ClientSender(server, directionIdJobs, gamePort, number)).start();
	}

	void startHandleDirection(){
		new Thread(hid).start();
	}

	public void print(String string) {
		//text.setText(string);
		if(gameDisplay != null)
			gameDisplay.print(string);
	}

	public void gameOver() {
		gameDisplay.gameOver();
	}

	private void envoieServer(short listeningPort, short portConnexion,
			InetSocketAddress server) throws Exception {
		DatagramChannel speakerChannel = DatagramChannel.open();
		speakerChannel.socket().bind(new InetSocketAddress(0));
		InetSocketAddress remote = new InetSocketAddress(server.getAddress(), portConnexion);
		
		ByteBuffer jeVeuxJouer = clientConnection(listeningPort);
		while(notReceivedPortGame){
			speakerChannel.send(jeVeuxJouer, remote);
			jeVeuxJouer.position(0);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}
		speakerChannel.close();
	}

	private ByteBuffer clientConnection(short listeningPort) {
		ByteBuffer res = ByteBuffer.allocate(3);
		res.put((byte) 0);
		res.putShort(listeningPort);
		res.flip();
		return res;
	}

	private short bufferWaitPlayerServer(int serverPort)
			throws Exception {
		DatagramChannel clientSocket = DatagramChannel.open();
		InetSocketAddress local = new InetSocketAddress(serverPort);
		clientSocket.socket().bind(local);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		server = (InetSocketAddress) clientSocket
				.receive(buffer);
		buffer.flip();
		try {
			byte nbChar = buffer.get();
			serverName = "";
			for (int i = 0; i < nbChar; i++)
				serverName += (char) buffer.get();
			short portConnexion = buffer.getShort();
			clientSocket.close();
			return portConnexion;
		} catch (BufferUnderflowException e) {
			clientSocket.close();
			throw new Exception("Game Server is corrupted");
		}
	}


}
