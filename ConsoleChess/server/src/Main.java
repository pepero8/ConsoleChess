package server.src;
class Main {
	public static void main(String[] args) {
		Player player = new Player();

		player.setPieceInControl(new King());
		player.handleInput("north");

		
	}
}