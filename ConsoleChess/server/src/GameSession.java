package server.src;
/**
*		이 파일은 서버에 올릴 것
*/
class GameSession {
	static enum end_game_condition {NONE, CHECKMATE_WHITE, CHECKMATE_BLACK};

	ChessBoard chessboard; //체스판
	Player player_white;
	Player player_black;
	//turn; //수, 두 플레이어가 한번씩 기물을 움직이면 한 수가 끝난다.

	//constructor
	public GameSession(Player player_white, Player player_black) {
		this.player_white = player_white;
		this.player_black = player_black;

		chessboard = new ChessBoard();
	}

	void start() {
		chessboard.initPieces();

		while(checkEndGameCondition() == end_game_condition.NONE) {
			player_white.fetchInput();
			player_white.handleInput();

			player_black.fetchInput();
			player_black.handleInput();
		}
		
	}

	end_game_condition checkEndGameCondition() {
		if (player_white.isCheckMate())
			return end_game_condition.CHECKMATE_WHITE;
		if (player_black.isCheckMate())
			return end_game_condition.CHECKMATE_BLACK;
		else
			return end_game_condition.NONE;
	}
}