package server.src.pieces;
public class Queen extends Piece {
	// constructor
	public Queen(side_enum side, Square square) {
		this.side = side;

		current_square = square;
	}

	@Override
	protected void move(Square destination) {
		// TODO Auto-generated method stub

	}
}