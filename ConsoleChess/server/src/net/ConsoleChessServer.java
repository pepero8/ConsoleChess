import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class ConsoleChessServer {
	private static final int MAX_CLIENTS = 5; //limit client numbers
	private ServerSocket welcomeSocket;
	private static Vector<ClientHandler> connected_clients;
	private WorkThreadManager wt_manager;

	//constructor
	ConsoleChessServer() {
		connected_clients = new Vector<ClientHandler>();
		wt_manager = new WorkThreadManager();
		try {
			welcomeSocket = new ServerSocket(8014);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void start() {
		System.out.println("server started at port:" + welcomeSocket.getLocalPort());
		wt_manager.startWorkThreads();
		try {
			while (true) {
				System.out.println("(server)current num of connected clients: " + connected_clients.size());
				ClientHandler client_handler;
				// limit max-size of vector to MAX_CLIENTS
				if (connected_clients.size() == MAX_CLIENTS) {
					System.out.println("client pool full");
					client_handler = new ClientHandler(welcomeSocket.accept(), true);
				}
				else {
					client_handler = new ClientHandler(welcomeSocket.accept(), false);
					connected_clients.add(client_handler);
				}
				client_handler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * delete ClientHandler from connected_clients
	 * called by ClientHandler instance
	 * @param ch
	 */
	static void disconnect(ClientHandler ch) {
		connected_clients.remove(ch);
	}
}
