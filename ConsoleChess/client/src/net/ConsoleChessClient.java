package client.src.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


class ConsoleChessClient {
	static private final String SERVER_IP = "3.38.115.16";
	static private final int SERVER_PORT = 8014;

	static private final int MAX_ROOMS = 5; //Lobby
	static private final int MAX_MSG_SIZE = 35 * MAX_ROOMS;
	private Socket client_socket;
	private BufferedReader in;
	private BufferedWriter out;
	private volatile String in_synchronous; //synchronous response of client's message(filled by MsgReceiver)
	private Scanner scan_user;
	private MsgReceiver msg_receiver;
	private String input_user;

	//constructor
	ConsoleChessClient() {
		try {
			client_socket = new Socket(SERVER_IP, SERVER_PORT); //connect to the server
			in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(client_socket.getOutputStream()));
			msg_receiver = new MsgReceiver(this, client_socket, in, new char[MAX_MSG_SIZE]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void connect() {
		msg_receiver.start();

		//=================test==================
		while (in_synchronous == null); //loop until message is received
		System.out.println(in_synchronous);
		//=================test==================

		if (!in_synchronous.contentEquals(MsgCodes.Server.SERVER_FULL)) {
			scan_user = new Scanner(System.in);
			
			while (true) {
				in_synchronous = null;
				System.out.print("Enter input: ");
				input_user = scan_user.next();
				System.out.println("input received: [" + input_user + "]");

				send(input_user);
				if (input_user.charAt(0) == MsgCodes.Client.CLOSE_CONNECTION) {
					break;
				}
				
				while (in_synchronous == null);
				System.out.println(in_synchronous);
			}
		}
		else {
			System.out.println("The Server is full. Please try again later");
		}

		dispose();
	}

	void send(String input_user) {
		System.out.println("sending(" + input_user.length() + "chars): [" + input_user + "]");
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
		//=================test==================
	}

	void putMsg(char[] msg_server) {
		//in_synchronous = String.valueOf(msg_server, 0, msg_server.length - 1);
		in_synchronous = String.valueOf(msg_server);
	}

	void dispose() {
		scan_user.close();
		System.out.println("socket closed");
	}
}
