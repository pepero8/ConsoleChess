package server.src;
class ChessBoard {
	//static enum side_enum {WHITE, BLACK};

	private King king_white; //white side king
	private King king_black; //black side king

	private Square[][] board;
	/*
		board layout:

					black side
	  index: 0  1  2  3  4  5  6  7
			 a  b  c  d  e  f  g  h			index
			[w, b, w, b, w, b, w, b] 8 	   board[0]
			[b, w, b, w, b, w, b, w] 7 	   board[1]
			[w, b, w, b, w, b, w, b] 6 	   board[2]
			[b, w, b, w, b, w, b, w] 5     board[3]
			[w, b, w, b, w, b, w, b] 4     board[4]
			[b, w, b, w, b, w, b, w] 3     board[5]
			[w, b, w, b, w, b, w, b] 2     board[6]
			[b, w, b, w, b, w, b, w] 1     board[7]
					white side

		w:white tile,  b:black tile
	*/

	//constructor
	public ChessBoard() {
		board = new Square[8][8];
	}

	public Square getSquare(int rank, int file) {
		return board[rank][file];
	}

	//set pieces to initial positions on board
	public void initPieces() {
		for (int rank = 0; rank != 8; rank++) {
			for (int file = 0; file != 8; file++) {
				board[rank][file] = new Square(rank, file);
			}
		}

		king_black = new King(Piece.side_enum.BLACK, board[0][4]);
		king_white = new King(Piece.side_enum.WHITE, board[7][4]);

		board[0][0].setCurrentPiece(new Rook(Piece.side_enum.BLACK, board[0][0]));
		board[0][1].setCurrentPiece(new Knight(Piece.side_enum.BLACK, board[0][1]));
		board[0][2].setCurrentPiece(new Bishop(Piece.side_enum.BLACK, board[0][2]));
		board[0][3].setCurrentPiece(new Queen(Piece.side_enum.BLACK, board[0][3]));
		board[0][4].setCurrentPiece(king_black);
		board[0][5].setCurrentPiece(new Bishop(Piece.side_enum.BLACK, board[0][5]));
		board[0][6].setCurrentPiece(new Knight(Piece.side_enum.BLACK, board[0][6]));
		board[0][7].setCurrentPiece(new Rook(Piece.side_enum.BLACK, board[0][7]));

		board[7][0].setCurrentPiece(new Rook(Piece.side_enum.WHITE, board[7][0]));
		board[7][1].setCurrentPiece(new Knight(Piece.side_enum.WHITE, board[7][1]));
		board[7][2].setCurrentPiece(new Bishop(Piece.side_enum.WHITE, board[7][2]));
		board[7][3].setCurrentPiece(new Queen(Piece.side_enum.WHITE, board[7][3]));
		board[7][4].setCurrentPiece(king_white);
		board[7][5].setCurrentPiece(new Bishop(Piece.side_enum.WHITE, board[7][5]));
		board[7][6].setCurrentPiece(new Knight(Piece.side_enum.WHITE, board[7][6]));
		board[7][7].setCurrentPiece(new Rook(Piece.side_enum.WHITE, board[7][7]));

		for (byte file = 0; file != 8; file++) {
			board[1][file].setCurrentPiece(new Pawn(Piece.side_enum.BLACK, board[1][file]));
			board[6][file].setCurrentPiece(new Pawn(Piece.side_enum.WHITE, board[6][file]));
		}
	}

	/**
	 *  check and update king's square and adjacent squares('o' in the below) to the king
	 *  whether the square is within moved piece's attack range
	 *  o o o
	 *  o k o
	 *  o o o
	 * @param movedPiece the piece that was moved by the player
	 */
	public void updateTiles(Piece movedPiece) {
		
		int movedPiece_rank = movedPiece.getCurrentSquare().getRank();
		int movedPiece_file = movedPiece.getCurrentSquare().getFile();

		if (movedPiece.getSide() == Piece.side_enum.WHITE) {
			int king_black_rank = king_black.getCurrentSquare().getRank();
			int king_black_file = king_black.getCurrentSquare().getFile();

			Square[] adjacent_squares_king = new Square[8];

			//retrieve all adjacent squares to black's king including king's square
			int idx = 0;
			for (int i = 0, rank = king_black_rank - 1; i != 3; rank++, i++) {
				for (int j = 0, file = king_black_file - 1; j != 3; file++, j++) {
					if (board[rank][file].getCurrentPiece() == null)
						continue;
					adjacent_squares_king[idx] = board[rank][file];
					idx++;
				}
			}

			// adjacent_squares_king[0] = board[king_black_rank - 1][king_black_file - 1];
			// adjacent_squares_king[1] = board[king_black_rank - 1][king_black_file];
			// adjacent_squares_king[2] = board[king_black_rank - 1][king_black_file + 1];
			// adjacent_squares_king[3] = board[king_black_rank][king_black_file - 1];
			// adjacent_squares_king[4] = board[king_black_rank][king_black_file + 1];
			// adjacent_squares_king[5] = board[king_black_rank + 1][king_black_file - 1];
			// adjacent_squares_king[6] = board[king_black_rank + 1][king_black_file];
			// adjacent_squares_king[7] = board[king_black_rank + 1][king_black_file + 1];

			if (movedPiece instanceof Pawn) {
				for (Square square : adjacent_squares_king) {
					//if the square is within pawn's attack range
					if (Math.abs(movedPiece_rank + 1 - king_black_rank) <= 1
						&& (Math.abs(movedPiece_file - 1 - king_black_file) <= 1
						|| Math.abs(movedPiece_file + 1 - king_black_file) <= 1))
					{
						square.setUnderAtk(true);
					}
				}
			}
			else if (movedPiece instanceof Knight) {
				for (Square square : adjacent_squares_king) {
					//if the square is within knight's attack range
					if (Math.abs(movedPiece_rank - king_black_rank) * Math.abs(movedPiece_file - king_black_file) == 2) {
						square.setUnderAtk(true);
					}
				}
			}
			else if (movedPiece instanceof Bishop) {
				checkSquares:
				for (Square square : adjacent_squares_king) {
					// if the square is within knight's attack range
					if (Math.abs(movedPiece_rank - king_black_rank) == Math.abs(movedPiece_file - king_black_file)) {
						//check if any piece(except black king) is located on the route between the square and the bishop
						int incr_rank = (king_black_rank - movedPiece_rank > 0) ? 1 : -1;
						int incr_file = (king_black_file - movedPiece_file > 0) ? 1 : -1;
						for (int rank = movedPiece_rank, file = movedPiece_file; rank != king_black_rank && file != king_black_file; rank += incr_rank, file += incr_file) {
							if (board[rank][file].getCurrentPiece() != null && board[rank][file].getCurrentPiece() != king_black) {
								continue checkSquares;
							}
						}
						square.setUnderAtk(true);
					}
				}
			}
			else if (movedPiece instanceof Rook) {
				checkSquares:
				for (Square square : adjacent_squares_king) {
					// if the square is within knight's attack range
					if (movedPiece_rank == king_black_rank || movedPiece_file == king_black_file) {
						//check if any piece(except king) is located on the route between the square and the Rook
						int incr_rank = 0, incr_file = 0;
						if (king_black_rank - movedPiece_rank != 0)
							incr_rank = (king_black_rank - movedPiece_rank > 0) ? 1 : -1;
						else
							incr_file = (king_black_file - movedPiece_file > 0) ? 1 : -1;
						//int incr_rank = (king_black_rank - movedPiece_rank > 0) ? 1 : -1;
						//int incr_file = (king_black_file - movedPiece_file > 0) ? 1 : -1;
						for (int rank = movedPiece_rank, file = movedPiece_file; rank != king_black_rank && file != king_black_file; rank += incr_rank, file += incr_file) {
							if (board[rank][file].getCurrentPiece() != null && board[rank][file].getCurrentPiece() != king_black) {
								continue checkSquares;
							}
						}
						square.setUnderAtk(true);
					}
				}
			}
			else if (movedPiece instanceof Queen) {
				checkSquares:
				for (Square square : adjacent_squares_king) {
					// if the square is within queen's attack range
					if ((movedPiece_rank == king_black_rank || movedPiece_file == king_black_file)
						|| (Math.abs(movedPiece_rank - king_black_rank) == Math.abs(movedPiece_file - king_black_file)))
					{
						// check if any piece(except king) is located on the route between the square and the Queen
						//============================================================================================
						square.setUnderAtk(true);
					}
				}
			}
		}
		else if (movedPiece.getSide() == Piece.side_enum.BLACK) {
			int king_white_rank = king_white.getCurrentSquare().getRank();
			int king_white_file = king_white.getCurrentSquare().getFile();
		}

		

		

		if (movedPiece instanceof Pawn) {
			if (movedPiece.getSide() == Piece.side_enum.WHITE) {
				if (Math.abs(movedPiece_rank + 1 - king_black_rank) <= 1 &&
				   (Math.abs(movedPiece_file - 1 - king_black_file) <= 1 ||
					Math.abs(movedPiece_file + 1 - king_black_file) <= 1))

					{

					}
			}
			
		}
	}
}