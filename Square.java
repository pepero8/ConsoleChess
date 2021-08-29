class Square {
	byte rank;
	byte file;

	boolean under_atk;

	public Square(byte rank, byte file) {
		this.rank = rank;
		this.file = file;

		under_atk = false;
	}
}