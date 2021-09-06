package server.src.pieces;
public class Bishop extends Piece {
	//constructor
	public Bishop(side_enum side, Square square) {
		this.side = side;

		current_square = square;
	}

	@Override
	protected void move(Square destination) {
		
		
	}
}