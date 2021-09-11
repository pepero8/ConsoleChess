//package server.src.net;

public class Room {
	private String name;
	private int num;

	//constructor
	Room(String name, int num) {
		this.name = name;
		this.num = num;
	}

	/**
	 * @return the name
	 */
	String getName() {
		return name;
	}

	/**
	 * @return the num
	 */
	int getNum() {
		return num;
	}
}
