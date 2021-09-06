package client.src.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;


class ConsoleChessClient {
	Socket client_socket;
	BufferedReader in;
	BufferedWriter out;

	private char[] msg_client;
	private char[] msg_server;

	public ConsoleChessClient() {
		try {
			client_socket = new Socket("3.38.115.16", 8014); //connect to the server
			in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(client_socket.getOutputStream()));

			msg_client = new char[6];
			msg_server = new char[21]; //모든 원소는 정수값 0으로 초기화됨

			//msg_client = new char[] {'h', 'e', 'l', 'l', 'o', ' ', 's', 'e', 'r', 'v', 'e', 'r'};
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	void connect() {
		try {
			int read_ch = 0;
			//in.read(msg_server);
			read_ch = in.read(msg_server); // initial state message -> "connection successful" / "server is full"
			System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
			Arrays.fill(msg_server, (char) 0); // empty buffer
			if (!msg_server.toString().contentEquals("server is full")) {
				msg_client[0] = 'N';
				msg_client[1] = 'g';
				msg_client[2] = 'x';
				msg_client[3] = 'e';
				msg_client[4] = '7';
				msg_client[5] = '+';
				out.write(msg_client);
				out.flush();

				read_ch = in.read(msg_server); //blocked until arrival of message
				System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
				Arrays.fill(msg_server, (char) 0);
				
				msg_client[0] = 'a';
				msg_client[1] = 'l';
				msg_client[2] = 'o';
				msg_client[3] = 's';
				msg_client[4] = 'e';
				msg_client[5] = '!';
				out.write(msg_client);
				out.flush();

				read_ch = in.read(msg_server); // blocked until arrival of message
				System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
				 Arrays.fill(msg_server, (char) 0);

				msg_client[0] = 'b';
				msg_client[1] = 'l';
				msg_client[2] = 'o';
				msg_client[3] = 's';
				msg_client[4] = 'e';
				msg_client[5] = '!';
				out.write(msg_client);
				out.flush();

				read_ch = in.read(msg_server); // blocked until arrival of message
				System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
				 Arrays.fill(msg_server, (char) 0);
				
				msg_client[0] = 'c';
				msg_client[1] = 'l';
				msg_client[2] = 'o';
				msg_client[3] = 's';
				msg_client[4] = 'e';
				msg_client[5] = '!';
				out.write(msg_client);
				out.flush();
			}

			client_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
