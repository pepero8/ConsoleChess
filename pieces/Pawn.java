


public class Pawn extends Piece {
	//constructor
	public Pawn(side_enum side, byte pos_rank, byte pos_file) {
		this.side = side;

		position = new byte[2];
		position[0] = pos_rank;
		position[1] = pos_file;
	}

	@Override
	protected void move(Square destination) {
		current_square = destination;
		System.out.println("Pawn moved to " + square);
	}
}