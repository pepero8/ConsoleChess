//command class
class MovePiece implements Command {
	//receiver
	private Piece receiverPiece;
	private Square destination;

	void setReceiverPiece(Piece receiverPiece) {
		this.receiverPiece = receiverPiece;
	}

	void setDestination(Square des) {
		destination = des;
	}

	@Override
	public void execute() {
		receiverPiece.move(destination);
	}
}