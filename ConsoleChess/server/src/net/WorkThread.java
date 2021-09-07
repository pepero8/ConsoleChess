//package server.src.net;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//import java.util.ArrayDeque;
//import java.util.Queue;

abstract class WorkThread extends Thread {
	static final int TICK_RATE = 1; //number of updates per second
	//Queue<char[]> msg_que;
	BlockingQueue<ClientHandler> msg_que;
	StringBuilder state_buffer; //current state

	public WorkThread() {
		msg_que = new LinkedBlockingQueue<ClientHandler>();
		state_buffer = new StringBuilder();
		create();
	}
	
	@Override
	public void run() {
		//char[] next_input = null;
		ClientHandler next_input;
		long time_frame_start, time_frame_end, time_elapsed, time_wait;
		while (true) {
			try {
				time_frame_start = System.currentTimeMillis();
				//take and process all inputs from msg_que
				while (!msg_que.isEmpty())  {
					next_input = msg_que.remove();
					processInput(next_input);
				}

				updateState();

				time_frame_end = System.currentTimeMillis();
				time_elapsed = time_frame_end - time_frame_start;
				System.out.println("time elapsed before tick: " + time_elapsed + "ms");
				time_wait = ((time_wait = 1000 / TICK_RATE - time_elapsed) > 0) ? time_wait : 0;
				Thread.sleep(time_wait);

				broadcastState();
				
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

	abstract void processInput(ClientHandler input);

	/**
	 * broadcast state_buffer to all clients connected to this WorkThread
	 */
	abstract void broadcastState();

	/**
	 * send client's input to WorkThread's message queue
	 * @param ch
	 */
	abstract void relay_msg(ClientHandler ch);

	/**
	 * register ClientHandler to this work thread called by ClientHandler instances
	 */
	abstract void bind(ClientHandler ch);

	abstract void exit(ClientHandler ch);
}
