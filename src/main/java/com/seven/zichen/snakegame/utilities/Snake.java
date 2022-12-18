package com.seven.zichen.snakegame.utilities;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

public class Snake {
	enum Direction {
		Left, Up, Right, Down
	}

	public Direction direction;
	public Point head;
	public Point tail;
	public byte id;
	public String name;
	public int score;
	LinkedList<Point> points;

	public Snake(Point point, int size, byte id, String name) {
		this.id = id;
		this.points = new LinkedList<>();
		this.name = name;

		for (int i = 0; i < size; i++) {
			Point pt = new Point(point.x + i, point.y);
			this.points.add(pt);
		}

		direction = Direction.Right;
		head = points.getLast();
		tail = points.getFirst();
		score = 0;
	}

	synchronized byte direction() {
		switch (this.direction) {
		case Left:
			return (byte) 0;
		case Up:
			return (byte) 1;
		case Right:
			return (byte) 2;
		case Down:
			return (byte) 3;
		default:
			return (byte) -1;
		}
	}

	synchronized public void direction(byte i) {
		if (direction() % 2 != i % 2) { //check cases unable to make a turn
			switch (i) {
			case 0:
				this.direction = Direction.Left;
				break;
			case 1:
				this.direction = Direction.Up;
				break;
			case 2:
				this.direction = Direction.Right;
				break;
			case 3:
				this.direction = Direction.Down;
				break;
			default:
				break;
			}
		}
	}

	synchronized public void move() {
		points.removeFirst();

		int x = head.x;
		int y = head.y;

		switch (this.direction) {
		case Up:
			y--;
			break;
		case Down:
			y++;
			break;
		case Right:
			x++;
			break;
		case Left:
			x--;
			break;
		default:
			break;
		}
		Point newHead = new Point(x, y);
		points.addLast(newHead);
		head = newHead;
		tail = points.getFirst();
	}

	synchronized public void grow() {
		points.addFirst(null);
		move();
	}

	synchronized public boolean isInCollision(Point a) {
		if (a.equals(head) && a != head) {
			return true;
		}
		return false;
	}

	synchronized public boolean isInCollision(Snake a) {
		for (Point pt : a.points) {
			if (isInCollision(pt)) {
				return true;
			}
		}
		return false;
	}

	synchronized public Snake isInCollision(HashMap<Integer,Snake> snakes) {
		for (Snake snake : snakes.values())
			if (isInCollision(snake))
				return snake;
		return null;
	}

	@Override
	synchronized public String toString() {
		String s = "Snake " + id + " [" + name + ",";
		for (Point p : points) {
			s += p.toString();
			if (!p.equals(head))
				s += ",";
		}
		s += "] goes " + direction;
		return s;
	}

	@Override
	public boolean equals(Object o) {
		Snake snake = (Snake) o;
		return snake.id == this.id;
	}
	
	synchronized static ByteBuffer encodeOneSnake(Snake snake){
		LinkedList<Point> points = new LinkedList<>();
		for(Point pt: snake.points){
			points.addLast(pt);
		}
		// s: queue > point > point > head
		
		
		LinkedList<Byte> dirs = new LinkedList<>();// collection of directions
		LinkedList<Byte> lens = new LinkedList<>();// collection of lengths
		
		//Snake should be at least 2 Points long
		
		Point p = points.poll();
		Point q = p; // queue
		Point n = points.poll();
		byte direction = dir(p,n);
		byte length = 1;
		
		while(n != null){
			byte dir = dir(p,n);
			if(direction == dir)
				length++;
			else{
				dirs.addLast(direction);
				lens.addLast(length);
				direction = dir;
				length = 1;
			}
			direction = dir;
			p = n;
			n = points.poll();
		}
		dirs.addLast(direction);
		lens.addLast(length);
		
		length = (byte) lens.size();
		
		ByteBuffer buf=ByteBuffer.allocate(length*2+4);

		buf.put(snake.id);
		buf.put((byte) q.x);
		buf.put((byte) q.y);
		buf.put(length);
		while (!lens.isEmpty()) {
			byte dir = dirs.poll();
			buf.put(dir);
			byte len = lens.poll();
			buf.put(len);
		}
		
		return buf;
		
	}
	
	static byte dir(Point p, Point n){
		int mod = GameOptions.gridSize;
		if(p.x % mod == n.x % mod){
			if((p.y + 1) % mod == n.y % mod) return 3;
			return 1;
		}
		//going East (2) or West (0)
		if((p.x + 1) % mod == n.x % mod) return 2;
		return 0;
	}

	synchronized static public ByteBuffer encodeAllSnakes(HashMap<Integer, Snake> snakes) {
		LinkedList<ByteBuffer> bb = new LinkedList<>();
		int size = 0;
		for (Snake s : snakes.values()) {
			// System.out.println("encodage du Snake  "+i);
			bb.add(encodeOneSnake(s));
			size += bb.getLast().capacity();
		}
		ByteBuffer buf = ByteBuffer.allocate(size + 2 + 2);//+2 for the apple;
		byte nb = (byte) snakes.size();
		buf.put((byte) 2);// TYPE
		buf.put(nb);// NB SNAKES

		for (ByteBuffer b : bb) {
			b.flip();
			buf.put(b);
		}

		return buf;
	}
}
