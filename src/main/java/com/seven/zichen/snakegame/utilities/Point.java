package com.seven.zichen.snakegame.utilities;


public class Point {
	public int x, y;
	
	public Point(int x, int y) {
		while (x < 0) {
			x += GameOptions.gridSize;
		}
		while (y < 0) {
			y += GameOptions.gridSize;
		}

		this.x = x % GameOptions.gridSize;
		this.y = y % GameOptions.gridSize;
	}

	@Override
	public boolean equals(Object o) {
		Point point = (Point) o;
		return point.x == this.x && point.y == this.y;
	}
	
	@Override
	public String toString(){
		return "(" + x + "," + y + ")";
	}
}
