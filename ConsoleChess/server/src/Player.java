package server.src;
//client
class Player {
	//invoker object
	Executecmd execmd;
	//command objects
	MovePiece move_piece;
	//UpdateTile update_tile;

	Piece piece_in_control;

	Player() {
		execmd = new Executecmd();
		move_piece = new MovePiece();
	}

	void setPieceInControl(Piece piece) {
		piece_in_control = piece;
	}

	void handleInput(String dir) {
		//인풋에 따라 명령호출
		// if (dir == "north") {
		// 	movePieceNorth.setReceiverPiece(piece_in_control);
		// 	execmd.setCmd(movePieceNorth);
		// }
		
		execmd.execute();
	}

	boolean isCheckMate() {
		//check if king is checked
		//if king's tile is in opponent's attack range

		//check if moving to available spaces is check-in
		//get surrounding tiles and check if tile is in opponent's attack range
	}
}