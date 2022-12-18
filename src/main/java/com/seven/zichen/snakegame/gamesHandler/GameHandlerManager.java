package com.seven.zichen.snakegame.gamesHandler;

import com.seven.zichen.snakegame.game.Game;
import com.seven.zichen.snakegame.utilities.Job;
import com.seven.zichen.snakegame.utilities.RunnableInput;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class GameHandlerManager implements Runnable{
	/**
	 * Handles the new player asking to play
	 *
	 *
	 */
	private Thread output;

	private Thread input;
	private ArrayBlockingQueue<Job> inCommunicator;
	private int nextPortToUseForGame, nbPlayers;
	
	
	public GameHandlerManager(int inputPort, int outputPort, String serverName, long broadcastTimeInterval, int nbP) throws IOException{
		/**
		 * A GameHandlerManager:
		 * - listening on port inputPort (using 1 Thread)
		 * - broadcasting this inputPort and the serverName every broadcastTimeInterval (ms) on outputPort (using another Thread)
		 */
		nbPlayers = Math.max(1, Math.min(nbP, 4));
		System.out.println("GameHandlerManager has been initialized:");
		
		inCommunicator = new ArrayBlockingQueue<>(100);
		input = new Thread(new RunnableInput(inputPort, inCommunicator, "GH"));
		System.out.println("\t> input Thread initialized on port " + inputPort);
		
		output = new Thread(new GameHandlerOutput(outputPort, serverName, broadcastTimeInterval, inputPort));
		System.out.println("\t> output Thread initialized on port " + outputPort+" (for broadcast)");
		
		System.out.println("\t> END");
		nextPortToUseForGame=30000;
		
		
	}

	@Override
	public void run() {
		System.out.println("GameHandlerManager has been started");
		input.start();
		output.start();
		
		while(true){
			try {
				Job j = inCommunicator.take();
				System.out.println(">>>>>>>>>>>>>>>>>>>>> Received a message");
				switch(j.type()){
				case WANT_TO_PLAY:

					System.out.println("A player want to play");
					Game game = Game.getGameForANewPlayer();
					if(game == null && nextPortToUseForGame < 32000){
						game = new Game(nbPlayers, nextPortToUseForGame++);
						game.start();
					}
					game.addClient(j.address(), j.port());
					break;
					
				default:
					System.out.println("received another type: " + j.type());
					break;
				
				}
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
