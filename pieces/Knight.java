public class Knight extends Piece {
	//constructor
	public Knight(side_enum side, byte pos_rank, byte pos_file) {
		this.side = side;

		position = new byte[2];
		position[0] = pos_rank;
		position[1] = pos_file;
	}
}