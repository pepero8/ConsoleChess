//package server.src.net;

import java.util.Vector;

class Lobby extends WorkThread {
	private static final int MAX_ROOMS = 5;
	private Room[] rooms;
	private static Vector<ClientHandler> connected_clients; //clients bound to this work thread
	//private static final String wt_name = "lobby"; //work thread name
	private static final char wt_code = MsgCodes.WT.LOBBY; //Lobby work thread code(for network communication)

	@Override
	protected void create() {
		rooms = new Room[MAX_ROOMS];
		connected_clients = new Vector<ClientHandler>();
	}

	@Override
	protected void updateState() {
		//=========================test===========================
		System.out.println("updating lobby state_buffer");
		System.out.println("(Lobby)current client number in lobby: " + connected_clients.size());
		emptyStateBuffer();

		for (int room_num = 0; room_num != 5; room_num++) {
			state_buffer.append((char)room_num)
						.append("Lee's room".toCharArray())
						.append('-')
						.append((char)1)
						.append((char)1)
						.append('\n');
		}

		state_buffer.trimToSize();

		System.out.println("updated state(" + state_buffer.length() + "chars): [" + state_buffer + "]");
		//=========================test===========================
	}

	@Override
	protected void processInput(ClientHandler ch) {
		char[] msg_client = ch.getMsgClient();
		System.out.println("processing input from " + ch.getIP() + ":" + ch.getPort() + " - [" + String.valueOf(msg_client) + "]");
		// =========================test===========================
		if (msg_client[0] == MsgCodes.Client.UNBIND_WT) {
			exit(ch);
			ch.setCurrentwt(null);
			ch.send(MsgCodes.Server.UNBIND_SUCCESS + wt_code);
		}
		// =========================test===========================
	}

	@Override
	protected void broadcastState() {
		// =========================test===========================
		for (ClientHandler ch : connected_clients) {
			ch.send(state_buffer);
		}
		// =========================test===========================
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

	// //get work thread name
	// @Override
	// String getWTName() {
	// 	return wt_name;
	// }

	@Override
	char getWTCode() {
		return wt_code;
	}
}
