package tp_jeu.players;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import tp_jeu.interfaces.Game;
import tp_jeu.interfaces.Player;
import tp_jeu.interfaces.Game.Move;

/**
 * An implementation of {@link Player} that randomly plays a valid move.
 * @author vdrevell
 *
 */
public class PlayerRandom implements Player {

	@Override
	public Move play(Game game) {
		List<Move> moves = game.possibleMoves();
		
		if (moves.isEmpty())
			return null;
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, moves.size());
		return moves.get(randomNum);
	}
}
