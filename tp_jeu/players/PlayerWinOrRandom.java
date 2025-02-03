package tp_jeu.players;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import tp_jeu.interfaces.Game;
import tp_jeu.interfaces.Player;
import tp_jeu.interfaces.Game.Move;

/**
 * An implementation of {@link Player} that plays an winning move, or randomly plays a valid move.
 * @author vdrevell
 *
 */
public class PlayerWinOrRandom implements Player {

	@Override
	public Move play(Game game) {
		List<Move> moves = game.possibleMoves();
		
		if (moves.isEmpty())
			return null;
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, moves.size());
		Move selectedMove = null;
		Iterator<Move> it = moves.iterator();
		while (it.hasNext() ) {
			Move move = it.next();
			// Select the first winning move if any
			Game nextGame = game.clone();
			nextGame.play(move);
			if (nextGame.winner() == game.player()) {
				selectedMove = move;
				break;
			}
			// Randomly select a move otherwise
			if (randomNum == 0) {
				selectedMove = move;
			}
			randomNum--;
		}
		return selectedMove;
	}
}
