//package server.src.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Vector;

/**
 * Network I/O thread
 */
class ClientHandler extends Thread {
	private static final int MAX_MSG_SIZE = 31; //consider room name
	private WorkThread current_wt; //current bound WorkThread
	private InetAddress ip_client;
	private int port_client;
	private Socket connection_socket;
	private BufferedReader in;
	private BufferedWriter out;

	private boolean server_full;
	private boolean disconnect = false;

	private char[] msg_client;
	private char[] msg_client_cache;

	//constructor
	ClientHandler(Socket connection_socket, boolean server_full) {
		this.connection_socket = connection_socket;
		this.server_full = server_full;
		
		try {
			ip_client = connection_socket.getInetAddress();
			port_client = connection_socket.getPort();

			in = new BufferedReader(new InputStreamReader(connection_socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(connection_socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		msg_client = new char[MAX_MSG_SIZE];
		msg_client_cache = new char[MAX_MSG_SIZE];
	}

	@Override
	public void run() {
		System.out.println("======================================================");
		System.out.println("connected from: " + ip_client + ": " + port_client);
		System.out.println("listening port: " + port_client);
		System.out.println("======================================================");
		
		try {
			if (!server_full) {
				send(MsgCodes.Server.CONNECTION_SUCCESS); //connection successful
				int read_ch; //number of characters read from input stream
				while(!disconnect) {
					read_ch = in.read(msg_client); //blocked until arrival of message

					System.out.print("[" + ip_client + ":" + port_client + "]");
					System.out.println("(" + read_ch + "bytes): [" + String.valueOf(msg_client) + "]");
					
					handleInput(msg_client);
					emptyMsgBuffer();
				}
				//remove client handler itself from connected_clients
				ConsoleChessServer.disconnect(this);
			}
			//if server is full
			else {
				send(MsgCodes.Server.SERVER_FULL); //server is full
			}

			connection_socket.close();
			System.out.println("closed connection from " + ip_client + ":" + port_client + '\n');

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	char[] getMsgClient() {
		return msg_client_cache;
	}

	InetAddress getIP() {
		return ip_client;
	}

	int getPort() {
		return port_client;
	}

	//used?
	// void setCurrentwt(WorkThread wt) {
	// 	current_wt = wt;
	// }

	void send(CharSequence msg_server) {
		try {
			out.write(msg_server.toString());
			out.flush();
			System.out.println("sent msg(" + ip_client + ":" + port_client + "): [" + msg_server + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void handleInput(char[] input) {
		//client requests disconnection
		if (input[0] == MsgCodes.Client.CLOSE_CONNECTION) {
			disconnect = true;
			return;
		}
		
		// client binds to specified work thread
		if (input[0] == MsgCodes.Client.BIND_WT) {
			String work_thread = String.valueOf(input, 1, input.length - 1);
			current_wt = WorkThreadManager.bind(work_thread, this);
			System.out.println("(" + ip_client + ":" + port_client + ")entered " + work_thread);
			send(MsgCodes.Server.BIND_SUCCESS + current_wt.getWTCode());
		}
		// client exits current work thread
		// else if (msg_client[0] == 'x') {
		// //if current work thread is lobby
		// current_wt.exit(this);
		// if (current_wt.getWTName().contentEquals("lobby")) {
		// current_wt = null;
		// }
		// //if current work thread is room
		// else if (current_wt.getWTName().contentEquals("room")) {
		// current_wt = WorkThreadManager.bind("lobby", this);
		// }
		// System.out.println("(" + ip_client + ":" + port_client + ")exited " +
		// current_wt.getWTName());
		// }
		else {
			System.arraycopy(msg_client, 0, msg_client_cache, 0, 6); // cache client's message
			current_wt.relay_msg(this);
		}
	}

	//clears client msg buffer
	void emptyMsgBuffer() {
		Arrays.fill(msg_client, (char) 0);
	}
}