package server.src.pieces;
public class Pawn extends Piece {
	//constructor
	public Pawn(side_enum side, Square square) {
		this.side = side;

		current_square = square;
	}

	@Override
	protected void move(Square destination) {
		current_square = destination;
		
		if ()
		System.out.println("Pawn moved to " + destination);
	}
}