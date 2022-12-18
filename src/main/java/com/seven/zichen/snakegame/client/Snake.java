package com.seven.zichen.snakegame.client;

import com.seven.zichen.snakegame.utilities.Point;

import java.util.LinkedList;

public class Snake {
	public byte direction;
	byte number;
	LinkedList<Point> points;

	public Snake(byte direction, byte number, LinkedList<Point> points){
		this.direction = direction;
		this.number = number;
		this.points = points;
	}
	
	@Override
	public String toString(){
		String s = "Snake " + number + " [";
		for (Point p : points) {
			s += p.toString();
			s += ",";
		}
		s += "] goes " + direction;
		return s;
	}
}