package tp_jeu.games;

import tp_jeu.interfaces.EvaluationFunction;
import tp_jeu.interfaces.Game;

/**
 * A generic evaluation function, that only rate final states
 * +oo if the current player has won
 * -oo if the current player has lost
 * 0 otherwise (game not finished, or equality)
 */
public class EvalWinLose extends EvaluationFunction {
	@Override
	public double evalForPlayer(Game game, Game.PlayerId player) {
		Game.PlayerId winner = game.winner();
		// If the game is finished and there is no equality, evaluate the player victory
		if (winner != null && winner != Game.PlayerId.NONE) {
			return (game.winner() == player) ? +oo : -oo;
		}
		// Otherwise, evaluation is zero.
		return 0.0;
	}

}
