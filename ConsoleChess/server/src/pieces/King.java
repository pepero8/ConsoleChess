package server.src.pieces;
public class King extends Piece {
	//constructor
	public King(side_enum side, Square square) {
		this.side = side;

		current_square = square;
	}

	@Override
	protected void move(Square destination) {
		// TODO Auto-generated method stub
		
	}
}