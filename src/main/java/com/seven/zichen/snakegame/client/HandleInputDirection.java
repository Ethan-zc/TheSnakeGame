package com.seven.zichen.snakegame.client;

import com.seven.zichen.snakegame.utilities.Pair;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

public class HandleInputDirection implements Runnable {
	private final BlockingDeque<Pair<Byte, Byte>> directionJobs;
	private ArrayBlockingQueue<Byte> inputDir;
	private byte direction, id;

	public HandleInputDirection(BlockingDeque<Pair<Byte, Byte>> jobs, ArrayBlockingQueue<Byte> inputDir) {
		id = 0;
		directionJobs = jobs;
		this.inputDir = inputDir;
	}

	@Override
	public void run() {
		while (!inputDir.isEmpty()) {
			try {
				inputDir.take();
			} catch (InterruptedException e1) {
			}
		}
		while (true) {
			try {
				byte a = inputDir.take();
				addInputDirection(a);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setDirection(byte dir) {
		direction = dir;
		if (!directionJobs.isEmpty()) {
			byte maDirection = directionJobs.element().obj1;
			if (direction == maDirection)
				directionJobs.remove();
		}
	}

	void addInputDirection(byte dir) throws InterruptedException {
		if (directionJobs.isEmpty()) {
			if (direction % 2 != dir % 2) {
				directionJobs.put(new Pair<>(dir, id));
				id++;
				synchronized (directionJobs) {
					directionJobs.notify();
				}
			}
			return;
		}
		byte lastDir = directionJobs.peekLast().obj1;
		if (lastDir % 2 != dir % 2) {
			directionJobs.put(new Pair<>(dir, id));
			id++;
		}
	}
}
