import pieces.*;

class ChessBoard {
	//static enum side_enum {WHITE, BLACK};

	private Piece[][] board;
	/*
		board layout:

						 black side
index: 0  1  2  3  4  5  6  7
			 a  b  c  d  e  f  g  h			index
			[w, b, w, b, w, b, w, b] 8 	board[0]
			[b, w, b, w, b, w, b, w] 7 	board[1]
			[w, b, w, b, w, b, w, b] 6 	board[2]
			[b, w, b, w, b, w, b, w] 5  board[3]
			[w, b, w, b, w, b, w, b] 4  board[4]
			[b, w, b, w, b, w, b, w] 3  board[5]
			[w, b, w, b, w, b, w, b] 2  board[6]
			[b, w, b, w, b, w, b, w] 1  board[7]
						 white side

		w:white tile,  b:black tile
	*/

	//constructor
	public ChessBoard() {
		board = new Piece[8][8];
	}

	//set pieces to initial positions on board
	public void initPieces() {
		board[0][0] = (Piece)new Rook(Piece.side_enum.BLACK, (byte)0, (byte)0);
		board[0][1] = (Piece)new Knight(Piece.side_enum.BLACK, (byte)0, (byte)1);
		board[0][2] = (Piece)new Bishop(Piece.side_enum.BLACK, (byte)0, (byte)2);
		board[0][3] = (Piece)new Queen(Piece.side_enum.BLACK, (byte)0, (byte)3);
		board[0][4] = (Piece)new King(Piece.side_enum.BLACK, (byte)0, (byte)4);
		board[0][5] = (Piece)new Bishop(Piece.side_enum.BLACK, (byte)0, (byte)5);
		board[0][6] = (Piece)new Knight(Piece.side_enum.BLACK, (byte)0, (byte)6);
		board[0][7] = (Piece)new Rook(Piece.side_enum.BLACK, (byte)0, (byte)7);

		board[7][0] = (Piece)new Rook(Piece.side_enum.WHITE, (byte)7, (byte)0);
		board[7][1] = (Piece)new Knight(Piece.side_enum.WHITE, (byte)7, (byte)1);
		board[7][2] = (Piece)new Bishop(Piece.side_enum.WHITE, (byte)7, (byte)2);
		board[7][3] = (Piece)new Queen(Piece.side_enum.WHITE, (byte)7, (byte)3);
		board[7][4] = (Piece)new King(Piece.side_enum.WHITE, (byte)7, (byte)4);
		board[7][5] = (Piece)new Bishop(Piece.side_enum.WHITE, (byte)7, (byte)5);
		board[7][6] = (Piece)new Knight(Piece.side_enum.WHITE, (byte)7, (byte)6);
		board[7][7] = (Piece)new Rook(Piece.side_enum.WHITE, (byte)7, (byte)7);

		for (byte file = 0; file != 8; file++) {
			board[1][file] = (Piece)new Pawn(Piece.side_enum.BLACK, (byte)1, file);
			board[6][file] = (Piece)new Pawn(Piece.side_enum.WHITE, (byte)6, file);
		}
	}
}