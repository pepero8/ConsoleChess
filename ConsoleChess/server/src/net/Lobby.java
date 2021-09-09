//package server.src.net;

import java.util.Vector;

class Lobby extends WorkThread {
	static final int MAX_ROOMS = 5;
	Room[] rooms;
	static Vector<ClientHandler> connected_clients; //clients bound to this work thread

	@Override
	void create() {
		//state_buffer = new char[MAX_ROOMS * 35];
		rooms = new Room[MAX_ROOMS];
		connected_clients = new Vector<ClientHandler>();
	}

	@Override
	void updateState() {
		//=========================test===========================
		System.out.println("updating lobby state_buffer");
		System.out.println("current client number in lobby: " + connected_clients.size());
		//StringBuilder state_buffer_temp = new StringBuilder();
		state_buffer.delete(0, state_buffer.length());
		//state_buffer = new StringBuilder();
		//state_buffer_temp.setLength(state_buffer.length);
		for (int room_num = 0; room_num != 5; room_num++) {
			state_buffer.append((char)room_num)
						.append("Lee's room".toCharArray())
						.append('-')
						.append((char)1)
						.append((char)1)
						.append('\n');
		}

		//state_buffer_temp.getChars(0, state_buffer_temp.length(), state_buffer, 0);
		state_buffer.trimToSize();

		System.out.println("updated state(" + state_buffer.length() + "chars): [" + state_buffer + "]");
		//=========================test===========================
	}

	@Override
	void processInput(ClientHandler input) {
		System.out.println("processing input from " + input.getIP() + ":" + input.getPort() + " - [" + String.valueOf(input.getMsgClient()) + "]");
	}

	@Override
	void broadcastState() {
		for (ClientHandler ch : connected_clients) {
			ch.send(state_buffer);
		}
	}

	@Override
	void bind(ClientHandler ch) {
		connected_clients.add(ch);
	}

	@Override
	void exit(ClientHandler ch) {
		connected_clients.remove(ch);
	}

	@Override
	void relay_msg(ClientHandler ch) {
		msg_que.add(ch);
		System.out.println("msg added");
	}
}
