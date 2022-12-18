package com.seven.zichen.snakegame.gamesHandler;

import com.seven.zichen.snakegame.game.Game;
import com.seven.zichen.snakegame.utilities.Job;
import com.seven.zichen.snakegame.utilities.RunnableInput;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class GameHandlerManager implements Runnable{
	private Thread output;

	private Thread input;
	private ArrayBlockingQueue<Job> inCommunicator;
	private int nextPortToUseForGame, nbPlayers;
	
	
	public GameHandlerManager(int inputPort, int outputPort, String serverName, long broadcastTimeInterval, int nbP) throws IOException{
		nbPlayers = Math.max(1, Math.min(nbP, 4));
		
		inCommunicator = new ArrayBlockingQueue<>(100);
		input = new Thread(new RunnableInput(inputPort, inCommunicator, "GH"));
		
		output = new Thread(new GameHandlerOutput(outputPort, serverName, broadcastTimeInterval, inputPort));

		nextPortToUseForGame=30000;
		
		
	}

	@Override
	public void run() {
		input.start();
		output.start();
		
		while(true){
			try {
				Job j = inCommunicator.take();
				switch(j.type()){
				case WANT_TO_PLAY:
					Game game = Game.getGameForANewPlayer();
					if(game == null && nextPortToUseForGame < 32000){
						game = new Game(nbPlayers, nextPortToUseForGame++);
						game.start();
					}
					game.addClient(j.address(), j.port());
					break;
				default:
					break;
				
				}
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
