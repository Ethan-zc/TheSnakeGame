package com.seven.zichen.snakegame.launcher;
import com.seven.zichen.snakegame.client.Client;

public class LaunchClient {
	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
		}
	}
}
