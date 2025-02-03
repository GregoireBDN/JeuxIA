package tp_jeu.players;

import tp_jeu.games.EvalWinLose;
import tp_jeu.interfaces.EvaluationFunction;
import tp_jeu.interfaces.Game;
import tp_jeu.interfaces.Player;
import tp_jeu.interfaces.Game.Move;
import tp_jeu.players.PlayerMinMax.ValuedMove;


/**
 * An implementation of {@link Player} that uses the AlphaBeta algorithm
 * @author vdrevell
 *
 */
public class PlayerAlphaBeta extends PlayerMinMax {	
	/**
	 * Default constructor, use the generic evaluation function.
	 * @param maxDepth: maximum depth of game tree exploration.
	 */
	public PlayerAlphaBeta(int maxDepth) {
		this(maxDepth, new EvalWinLose());
	}
	
	/**
	 * Constructor with ability to set the maximum allowed computation time
	 * @param maxDepth: maximum depth of game tree exploration.
	 * @param evalFunc: the EvaluationFunction to use with the game
	 */
	public PlayerAlphaBeta(int maxDepth, EvaluationFunction evalFunc) {
		super(maxDepth, evalFunc);
	}
	
	@Override
	public Move play(Game game) {
		// TODO set the player of the evalFunction if needed
		
		// TODO call the alphabeta algorithm to get the best move and its value
		ValuedMove bestMove = new ValuedMove(null, 0.0);
		
		System.out.println("AlphaBeta choose " + bestMove);
		return bestMove.move;
	}
	
	
	private ValuedMove alphabeta(Game game, double alpha, double beta, int depth) { // alpha < beta
		// TODO implement alphabeta (add parameters if needed)
		return new ValuedMove(null, 0.0);
	}
}
