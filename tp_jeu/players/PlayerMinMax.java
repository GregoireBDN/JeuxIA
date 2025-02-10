package tp_jeu.players;

import tp_jeu.games.EvalWinLose;
import tp_jeu.interfaces.EvaluationFunction;
import tp_jeu.interfaces.Game;
import tp_jeu.interfaces.Player;
import tp_jeu.interfaces.Game.Move;

/**
 * An implementation of {@link Player} that uses the MiniMax algorithm
 * 
 * @author vdrevell
 *
 */
public class PlayerMinMax implements Player {
	/**
	 * Infinity: +oo and -oo
	 */
	final static double oo = EvaluationFunction.oo;

	/**
	 * A class for (move, value) pairs
	 */
	static class ValuedMove {
		Move move;
		double value;

		public ValuedMove(Move move, double value) {
			this.move = move;
			this.value = value;
		}

		public String toString() {
			return move.toString() + " (value = " + value + ")";
		};
	}

	/**
	 * The maximum depth of the game tree to develop
	 */
	protected int maxDepth;

	/**
	 * The evaluation function use to evaluate the game states
	 */
	protected EvaluationFunction evalFunc;

	/**
	 * Default constructor, use the generic evaluation function.
	 * 
	 * @param maxDepth: maximum depth of game tree exploration.
	 */
	public PlayerMinMax(int maxDepth) {
		this(maxDepth, new EvalWinLose());
	}

	/**
	 * Constructor with ability to set the maximum allowed computation time
	 * 
	 * @param maxDepth: maximum depth of game tree exploration.
	 * @param evalFunc: the EvaluationFunction to use with the game
	 */
	public PlayerMinMax(int maxDepth, EvaluationFunction evalFunc) {
		this.maxDepth = maxDepth;
		this.evalFunc = evalFunc;
	}

	/**
	 * Play a move using the MinMax algorithm
	 * 
	 * @param game the game to play
	 * @return the best move
	 */
	@Override
	public Move play(Game game) {
		evalFunc.setPlayer(game.player());
		ValuedMove bestMove = minmax(game, 0);

		System.out.println("MinMax choose " + bestMove);
		return bestMove.move;
	}

	private ValuedMove minmax(Game game, int depth) {
		if (depth == maxDepth || game.possibleMoves().isEmpty()) {
			return new ValuedMove(null, evalFunc.eval(game));
		}

		if (game.player() == Game.PlayerId.ONE) {
			double maxValue = -oo;
			Move bestMove = null;
			for (Move move : game.possibleMoves()) {
				Game gameCopy = game.clone();
				gameCopy.play(move);
				double value = minmax(gameCopy, depth + 1).value;
				if (value > maxValue) {
					maxValue = value;
					bestMove = move;
				}
			}
			return new ValuedMove(bestMove, maxValue);
		} else {
			double minValue = oo;
			Move bestMove = null;
			for (Move move : game.possibleMoves()) {
				Game gameCopy = game.clone();
				gameCopy.play(move);
				double value = minmax(gameCopy, depth + 1).value;
				if (value < minValue) {
					minValue = value;
					bestMove = move;
				}
			}
			return new ValuedMove(bestMove, minValue);
		}

	}
}
