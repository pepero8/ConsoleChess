//package client.src.net;

class MsgCodes {
	class Server {
		static final char SYNC = 's';
		static final String CONNECTION_SUCCESS = "ss";
		static final String SERVER_FULL = "sf";
		static final String BIND_SUCCESS = "sb";
		static final String UNBIND_SUCCESS = "sx";
	}

	class Client {
		static final char CLOSE_CONNECTION = 'c';
		static final char BIND_WT = 'b'; // bind to work thread(work thread name is followed)
		static final char UNBIND_WT = 'x';
	}

	class WT {
		static final char LOBBY = 'l';
	}
}
