package client.src.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

class MsgReceiver extends Thread {
	ConsoleChessClient client;
	BufferedReader in;
	Socket client_socket;
	char[] msg_server;

	public MsgReceiver(ConsoleChessClient client, Socket client_socket, BufferedReader in, char[] msg_server) {
		this.client = client;
		this.in = in;
		this.client_socket = client_socket;
		this.msg_server = msg_server;
	}

	@Override
	public void run() {
		int read_ch = 0;
		try {
			read_ch = in.read(msg_server);
			System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
			client.putMsg(msg_server);
			Arrays.fill(msg_server, (char) 0); // empty buffer
			while (!client_socket.isClosed()) {
				read_ch = in.read(msg_server);
				System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
				//if the message is synchronous response
				if (msg_server[0] == 's') {
					client.putMsg(msg_server);
				}
				else
					client.handleMsg(msg_server);
				Arrays.fill(msg_server, (char) 0); // empty buffer
			}

			System.out.println("MsgReceiver stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
