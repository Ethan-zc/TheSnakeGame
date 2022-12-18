package com.seven.zichen.snakegame.utilities;


public class Client {
	public int id;
	public String address;
	public String name;
	public int listeningPort;
	
	public Client(String address, int listeningPort, byte id, String name){
		this.id = id&255;
		this.address = address;
		this.listeningPort = listeningPort;
		this.name = name;
	}
	
	
	@Override
	public String toString(){
		return "Client " + this.id + this.name + " listening on " + this.address+":" + this.listeningPort;
	}

}
