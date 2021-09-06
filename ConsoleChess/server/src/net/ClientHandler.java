//package server.src.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Vector;

/**
 * Network I/O thread
 */
class ClientHandler extends Thread {
	//Vector<ClientHandler> connected_clients; //shared buffer
	Lobby lobby_workthread;

	Socket connection_socket;
	BufferedReader in;
	BufferedWriter out;

	char[] msg_client;
	//char[] msg_server;

	//constructor
	//called when server capacity is full
	public ClientHandler(Socket connection_socket) {
		this.connection_socket = connection_socket;
		try {
			in = new BufferedReader(new InputStreamReader(connection_socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(connection_socket.getOutputStream()));

			//out.write("server is full");
			send("server is full");

			connection_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//constructor
	public ClientHandler(Socket connection_socket, Lobby lobby_workthread) {
		//this.connected_clients = connected_clients;
		this.connection_socket = connection_socket;
		this.lobby_workthread = lobby_workthread;
		try {
			in = new BufferedReader(new InputStreamReader(connection_socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(connection_socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		msg_client = new char[6];
		//msg_server = new char[6];
		
	}

	public void run() {
		System.out.println("======================================================");
		System.out.println("connected from: " + connection_socket.getInetAddress() + ": " + connection_socket.getPort());
		System.out.println("listening port: " + connection_socket.getLocalPort());
		System.out.println("======================================================");
		try {
			//out.write("connection successful"); //send initial state message to client
			//out.flush();
			send("connection successful");
			int read_ch;
			while(true) {
				read_ch = in.read(msg_client); //blocked until arrival of message
				
				System.out.print("[" + connection_socket.getInetAddress() + ":" + connection_socket.getPort() + "]");
				System.out.println("(" + read_ch + "bytes): [" + String.valueOf(msg_client) + "]");
				if (msg_client[0] == 'c') {
					break;
				}
				Arrays.fill(msg_client, (char) 0); // empty buffer

				//=========================test===========================
				//if client requests lobby
				if (msg_client[0] == 'l') {
					lobby_workthread.bind(this);
					System.out.println("(" + connection_socket.getInetAddress() + ":" + connection_socket.getPort() + ")entered lobby");
				}
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
			System.out.println("closed connection from " + connection_socket.getInetAddress() + ":" + connection_socket.getPort() + '\n');

			//remove client handler itself from connected_clients
			//connected_clients.remove(this);
			ConsoleChessServer.disconnect(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void send(CharSequence msg_server) {
		try {
			out.write(msg_server.toString());
			out.flush();
			System.out.println("sent msg(" + connection_socket.getInetAddress() + ":" + connection_socket.getPort() + "): [" + msg_server + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}