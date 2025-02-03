package tp_jeu.interfaces;

/**
 * Abstract base class for an heuristic evaluation function of a {@link Game} state
 */
public abstract class EvaluationFunction {
	/**
	 * Infinity: +oo and -oo
	 */
	public static final double oo = Double.POSITIVE_INFINITY;
	
	/**
	 * The player for which the game is evaluated.
	 */
	protected Game.PlayerId selectedPlayer = Game.PlayerId.NONE;
	
	/**
	 * Get the player for which the game is evaluated when calling {@link EvaluationFunction#eval}.
	 * @return The player used for evaluation ({@link Game.PlayerId#ONE} or {@link Game.PlayerId#TWO}), 
	 *         or {@link Game.PlayerId#NONE} if evaluation is done wrt the current player of the game state.
	 */
	public Game.PlayerId getPlayer() {
		return selectedPlayer;
	}

	/**
	 * Set the player for which the game is evaluated when calling {@link EvaluationFunction#eval}.
	 * @param player The player used for evaluation. Set to {@link Game.PlayerId#NONE} to evaluate wrt the current player of the game state.
	 */
	public void setPlayer(Game.PlayerId player) {
		this.selectedPlayer = player;
	}

	/**
	 * Evaluation of a game state for the player selected with {@link EvaluationFunction#setPlayer}
	 * @param game The game state to evaluate
	 * @return evaluation: the more positive, the best for the selected player.
	 *     The more negative, the best for the other player.
	 */
	public final double eval(Game game) {
		return evalForPlayer(game, selectedPlayer!=Game.PlayerId.NONE ? selectedPlayer : game.player());
	}
	
	/**
	 * Evaluation of a game state for a given player
	 * @param game The game state to evaluate
	 * @param player The player for which the game state is evaluated
	 * @return evaluation: the more positive, the best for the selected player.
	 *     The more negative, the best for the other player.
	 */
	public abstract double evalForPlayer(Game game, Game.PlayerId player);
	
}
