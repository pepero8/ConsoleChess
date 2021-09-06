//package server.src.net;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//import java.util.ArrayDeque;
//import java.util.Queue;

abstract class WorkThread extends Thread {
	static final int TICK_RATE = 1; //number of updates per second
	//Queue<char[]> msg_que;
	BlockingQueue<char[]> msg_que;
	StringBuilder state_buffer; //current state

	public WorkThread() {
		msg_que = new LinkedBlockingQueue<char[]>();
		state_buffer = new StringBuilder();
		create();
	}
	
	@Override
	public void run() {
		char[] next_input = null;
		while (true) {
			try {
				//take and process all inputs from msg_que
				while (!msg_que.isEmpty())  {
					next_input = msg_que.remove();
					processInput(next_input);
				}

				updateState();
				broadcastState();
				Thread.sleep(1000 / TICK_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	}

	/**
	 * serves as constructor
	 */
	abstract void create();

	/**
	 * update state_buffer
	 */
	abstract void updateState();

	abstract void processInput(char[] input);

	/**
	 * broadcast state_buffer to all clients connected to this WorkThread
	 */
	abstract void broadcastState();
}
