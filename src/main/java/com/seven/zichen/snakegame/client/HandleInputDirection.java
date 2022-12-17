package com.seven.zichen.snakegame.client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

public class HandleInputDirection implements Runnable {
	private BlockingDeque<Pair<Byte, Byte>> directionJobs;
	private ArrayBlockingQueue<Byte> demandeDir;
	private byte direction, id;

	public HandleInputDirection(BlockingDeque<Pair<Byte, Byte>> jobs, ArrayBlockingQueue<Byte> demandeDir) {
		id = 0;
		directionJobs = jobs;
		this.demandeDir = demandeDir;
	}

	@Override
	public void run() {
		while (!demandeDir.isEmpty()) {
			try {
				demandeDir.take();
			} catch (InterruptedException e1) {
			}
		}
		while (true) {
			try {
				byte a = demandeDir.take();
				addInputDirection(a);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setDirection(byte directionBis) {
		direction = directionBis;
		if (!directionJobs.isEmpty()) {
			byte maDirection = directionJobs.element().a;
			if (direction == maDirection)
				directionJobs.remove();
		}
	}

	void addInputDirection(byte a) throws InterruptedException {
		if (directionJobs.isEmpty()) {
			if (direction % 2 != a % 2) {
				directionJobs.put(new Pair<>(a, id));
				id++;
				synchronized (directionJobs) {
					directionJobs.notify();
				}
			}
			return;
		}
		byte lastDir = directionJobs.peekLast().a;
		if (lastDir % 2 != a % 2) {
			directionJobs.put(new Pair<>(a, id));
			id++;
		}
	}
}
