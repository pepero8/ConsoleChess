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
	//Vector<ClientHandler> connected_clients; //shared buffer
	//Lobby lobby_workthread;
	WorkThread current_wt; //current bound WorkThread
	InetAddress ip_client;
	int port_client;

	Socket connection_socket;
	BufferedReader in;
	BufferedWriter out;

	private char[] msg_client;
	//char[] msg_server;

	// //constructor
	// //called when server capacity is full
	// public ClientHandler(Socket connection_socket) {
	// 	this.connection_socket = connection_socket;
	// 	try {
	// 		in = new BufferedReader(new InputStreamReader(connection_socket.getInputStream()));
	// 		out = new BufferedWriter(new OutputStreamWriter(connection_socket.getOutputStream()));

	// 		//out.write("server is full");
	// 		send("server is full");

	// 		connection_socket.close();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// }

	//constructor
	public ClientHandler(Socket connection_socket, boolean server_full) {
		//this.connected_clients = connected_clients;
		this.connection_socket = connection_socket;
		ip_client = connection_socket.getInetAddress();
		port_client = connection_socket.getPort();
		try {
			in = new BufferedReader(new InputStreamReader(connection_socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(connection_socket.getOutputStream()));

			if (server_full) {
				send("server is full");
				connection_socket.close();
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		msg_client = new char[6];
		//msg_server = new char[6];
		
	}

	public void run() {
		System.out.println("======================================================");
		System.out.println("connected from: " + ip_client + ": " + port_client);
		System.out.println("listening port: " + port_client);
		System.out.println("======================================================");
		try {
			//out.write("connection successful"); //send initial state message to client
			//out.flush();
			send("connection successful");
			int read_ch;
			while(true) {
				read_ch = in.read(msg_client); //blocked until arrival of message
				
				System.out.print("[" + ip_client + ":" + port_client + "]");
				System.out.println("(" + read_ch + "bytes): [" + String.valueOf(msg_client) + "]");
				if (msg_client[0] == 'c') {
					break;
				}
				//Arrays.fill(msg_client, (char) 0); // empty buffer

				//=========================test===========================
				//if client requests lobby
				else if (msg_client[0] == 'l') {
					//Lobby.bind(this);
					current_wt = WorkThreadManager.bind("Lobby", this);
					System.out.println("(" + ip_client + ":" + port_client + ")entered lobby");
				}
				else {
					current_wt.relay_msg(this);
				}
				Arrays.fill(msg_client, (char) 0); // empty buffer
				//=========================test===========================

				send("Ke8+");
				
				// msg_server[0] = 'K';
				// msg_server[1] = 'e';
				// msg_server[2] = '8';
				// msg_server[3] = '+';
				// msg_server[4] = '_';
				// msg_server[5] = '_';

				// out.write(msg_server);
				// out.flush();
			}

			connection_socket.close();
			System.out.println("closed connection from " + ip_client + ":" + port_client + '\n');

			//remove client handler itself from connected_clients
			//connected_clients.remove(this);
			ConsoleChessServer.disconnect(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	char[] getMsgClient() {
		return msg_client;
	}

	InetAddress getIP() {
		return ip_client;
	}

	int getPort() {
		return port_client;
	}

	void send(CharSequence msg_server) {
		try {
			out.write(msg_server.toString());
			out.flush();
			System.out.println("sent msg(" + ip_client + ":" + port_client + "): [" + msg_server + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}