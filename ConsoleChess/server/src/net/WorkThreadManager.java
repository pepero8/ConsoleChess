//package server.src.net;

class WorkThreadManager {
	private static WorkThread[] work_threads;

	//constructor
	WorkThreadManager() {
		work_threads = new WorkThread[3]; //0: Lobby
		work_threads[0] = new Lobby();
	}

	void startWorkThreads() {
		work_threads[0].start();
		//for (WorkThread wt : work_threads) {
		//	wt.start();
		//}
		System.out.println("workthread manager: all threads started");
	}

	static WorkThread bind(String wt, ClientHandler ch) {
		switch (wt) {
			case "lobby":
				work_threads[0].bind(ch);
				return work_threads[0];
			default:
				return null;
				//throw exception?	
		}
	}
}
