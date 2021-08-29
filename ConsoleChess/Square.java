class Square {
	private int rank;
	private int file;

	private Piece current_piece;

	private boolean under_atk;

	public Square(int rank, int file) {
		this.rank = rank;
		this.file = file;

		under_atk = false;
	}

	void setCurrentPiece(Piece piece) {
		current_piece = piece;
	}

	Piece getCurrentPiece() {
		return current_piece;
	}

	int getRank() {
		return rank;
	}

	int getFile() {
		return file;
	}

	void setUnderAtk(boolean atk) {
		under_atk = atk;
	}
}