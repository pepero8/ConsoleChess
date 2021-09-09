package client.src.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
//import java.util.Arrays;
import java.util.Scanner;


class ConsoleChessClient {
	static final int MAX_ROOMS = 5; //Lobby
	static final int MAX_MSG_SIZE = 35 * MAX_ROOMS;
	Socket client_socket;
	BufferedReader in;
	BufferedWriter out;
	volatile String in_synchronous; //synchronous response of client's message(filled by MsgReceiver)
	Scanner scan_in_synchronous; //scanner for in_synchronous
	Scanner scan_user;
	MsgReceiver msg_receiver;

	//private char[] msg_client;
	String input_user;
	//private char[] msg_server;
	//StringBuilder msg_server;

	public ConsoleChessClient() {
		try {
			client_socket = new Socket("3.38.115.16", 8014); //connect to the server
			in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(client_socket.getOutputStream()));
			//scan_in_synchronous = new Scanner(in_synchronous);

			msg_receiver = new MsgReceiver(this, client_socket, in, new char[MAX_MSG_SIZE]);

			//msg_client = new char[6];
			//msg_server = new char[MAX_MSG_SIZE]; //모든 원소는 정수값 0으로 초기화됨
			//msg_server = new StringBuilder();

			//msg_client = new char[] {'h', 'e', 'l', 'l', 'o', ' ', 's', 'e', 'r', 'v', 'e', 'r'};
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	void connect() {
		msg_receiver.start();
		//try {
			//int read_ch = 0;
			//=================test==================
			while (in_synchronous == null); //loop until message is received
			//scan_in_synchronous = new Scanner(in_synchronous);
			//System.out.println(scan_in_synchronous.nextLine()); //blocked until message arrival? -> nextLine(): no
			//in_synchronous = null;
			System.out.println(in_synchronous);
			
			//=================test==================
			//in.read(msg_server);
			//read_ch = in.read(msg_server); // initial state message -> "connection successful" / "server is full"
			//System.out.println("server(" + read_ch + "chars): [" + String.valueOf(msg_server) + "]");
			//Arrays.fill(msg_server, (char) 0); // empty buffer
			
			if (!in_synchronous.contentEquals("server is full")) {
				// msg_client[0] = 'N';
				// msg_client[1] = 'g';
				// msg_client[2] = 'x';
				// msg_client[3] = 'e';
				// msg_client[4] = '7';
				// msg_client[5] = '+';
				// out.write(msg_client);
				// out.flush();
				
				// read_ch = in.read(msg_server); //blocked until arrival of message
				// System.out.println("server(" + read_ch + "bytes): [" + String.valueOf(msg_server) + "]");
				// Arrays.fill(msg_server, (char) 0);
				scan_user = new Scanner(System.in);
				
				//String input_user;
				while (true) {
					in_synchronous = null;
					System.out.print("Enter input: ");
					input_user = scan_user.next();
					System.out.println("input received: [" + input_user + "]");
					if (input_user.charAt(0) == 'c') {
						send(input_user);
						break;
					}
					send(input_user);
					while (in_synchronous == null);
					System.out.println(in_synchronous);
					//System.out.println(scan_in_synchronous.nextLine()); // blocked until message arrival?
				}
			}

			//client_socket.close();
			dispose();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
	}

	void send(String input_user) {
		System.out.println("sending: [" + input_user + "]");
		try {
			out.write(input_user.toCharArray());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void handleMsg(char[] msg_server) {
		//=================test==================
		System.out.println("server: [" + String.valueOf(msg_server) + "]");
		System.out.println("Enter input: ");
		// while (scan.hasNext()) {
		// 	System.out.println("room: " + scan.next("\n"));
		// }
		//=================test==================
	}

	void putMsg(char[] msg_server) {
		in_synchronous = String.valueOf(msg_server, 0, msg_server.length - 1);
	}

	void dispose() {
		scan_in_synchronous.close();
		scan_user.close();
	}
}
