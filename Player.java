//client
class Player {
	//invoker object
	Executecmd execmd;
	//command objects
	MovePieceNorth movePieceNorth;

	Piece pieceInControl;

	Player() {
		execmd = new Executecmd();
		movePieceNorth = new MovePieceNorth();
	}

	void setPieceInControl(Piece piece) {
		pieceInControl = piece;
	}

	void handleInput(String dir) {
		//인풋에 따라 명령호출
		if (dir == "north") {
			movePieceNorth.setReceiverPiece(pieceInControl);
			execmd.setCmd(movePieceNorth);
		}
		
		execmd.execute();
	}

	boolean isCheckMate() {
		//check if king is checked
		//if king's tile is in opponent's attack range

		//check if moving to available spaces is check-in
		//get surrounding tiles and check if tile is in opponent's attack range
	}
}