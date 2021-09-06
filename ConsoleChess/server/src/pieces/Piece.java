package server.src.pieces;
abstract public class Piece {

	public static enum side_enum {WHITE, BLACK};

	protected side_enum side;
	protected Square current_square;

	side_enum getSide() {
		return side;
	}

	Square getCurrentSquare() {
		return current_square;
	}

	abstract protected void move(Square destination);
		//update squares' status within attack range

/*
	protected void moveSouth() {
		System.out.println("moved south");
	}

	protected void moveEast() {
		System.out.println("moved east");
	}

	protected void moveWest() {
		System.out.println("moved west");
	}
*/
}