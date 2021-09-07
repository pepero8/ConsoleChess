import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class ConsoleChessServer {
	static final int MAX_CLIENTS = 5; //limit client numbers
	ServerSocket welcomeSocket;
	private static Vector<ClientHandler> connected_clients;
	Lobby lobby;

	public ConsoleChessServer() {
		connected_clients = new Vector<ClientHandler>();
		lobby = new Lobby();
		try {
			welcomeSocket = new ServerSocket(8014);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void start() {
		System.out.println("server started at port:" + welcomeSocket.getLocalPort());
		lobby.start();
		try {
			while (true) {
				System.out.println("current num of connected clients: " + connected_clients.size());
				ClientHandler client_handler;
				// limit max-size of vector to MAX_CLIENTS
				if (connected_clients.size() == MAX_CLIENTS) {
					System.out.println("client pool full");
					client_handler = new ClientHandler(welcomeSocket.accept(), true);
				}
				else {
					client_handler = new ClientHandler(welcomeSocket.accept(), false);
					connected_clients.add(client_handler);
					client_handler.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * delete ClientHandler from connected_clients
	 * called by ClientHandler instances
	 * @param ch
	 */
	static void disconnect(ClientHandler ch) {
		connected_clients.remove(ch);
	}
}
