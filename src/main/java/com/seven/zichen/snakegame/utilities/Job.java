package com.seven.zichen.snakegame.utilities;

import java.nio.ByteBuffer;
import java.util.HashSet;

public class Job {
	
	private Type type;
	private String address;
	private int port;
	private byte id;
	private byte direction;
	private byte jobId;
	private byte timer;
	public HashSet<Snake> snakes;

	public enum Type {
		WANT_TO_PLAY,
		READY_TO_PLAY,
		MOVE,
		SEND_GAME_INFO,
		SEND_TIMER,
		SEND_POSITIONS,
		SEND_SCORES,
		UNKNOWN
	}

	public Job(Type type) {
		this.type = type;
		snakes = new HashSet<>();
	}

	public Job(ByteBuffer buf, String address) {
		this.address=address;
		byte type = buf.get();
		switch (type) {
		case 0:
			this.type = Type.WANT_TO_PLAY;
			this.port = buf.getShort();
			break;
		case 1:
			this.type = Type.READY_TO_PLAY;
			this.id = buf.get();
			break;
		case 2:
			this.type = Type.MOVE;
			this.jobId = buf.get();
			this.id = buf.get();
			this.direction = buf.get();
			break;
		default:
			this.type = Type.UNKNOWN;
			break;
		}
	}
	
	public Type type() {
		return type;
	}
	public String address(){
		return address;
	}
	public int port(){
		return this.port;
	}
	public void port(int p){
		this.port=p;
	}
	public byte direction(){
		return this.direction;
	}
	public byte id(){
		return this.id;
	}
	public void id(int id){
		this.id=(byte) id;
	}
	public byte jobId(){
		return this.jobId;
	}
	public byte timer(){
		return this.timer;
	}
	public void timer(byte t){
		this.timer=t;
	}
}
