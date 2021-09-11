package client.src.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

class MsgReceiver extends Thread {
	private ConsoleChessClient client;
	private BufferedReader in;
	private Socket client_socket;
	private char[] msg_server;

	//constructor
	MsgReceiver(ConsoleChessClient client, Socket client_socket, BufferedReader in, char[] msg_server) {
		this.client = client;
		this.in = in;
		this.client_socket = client_socket;
		this.msg_server = msg_server;
	}

	@Override
	public void run() {
		int read_ch = 0;
		try {
			while (!client_socket.isClosed()) {
				read_ch = in.read(msg_server);
				System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
				//if the message is synchronous response
				if (msg_server[0] == MsgCodes.Server.SYNC) {
					client.putMsg(msg_server);
				}
				else
					client.handleMsg(msg_server);
				emptyMsgBuffer();
			}

			System.out.println("MsgReceiver stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void emptyMsgBuffer() {
		Arrays.fill(msg_server, (char) 0); // empty buffer
	}
}
