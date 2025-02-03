package tp_jeu.players;

import tp_jeu.interfaces.Game;
import tp_jeu.interfaces.Player;
import tp_jeu.interfaces.Game.Move;
import tp_jeu.players.mcts.MonteCarloTreeSearch;

/**
 * An implementation of {@link Player} that uses the MCTS algorithm
 * @author vdrevell
 *
 */
public class PlayerMCTS implements Player {
	
	private int timeAllowedMillis;
	
	/**
	 * Default constructor, sets a computation timeout of 1000 ms.
	 */
	public PlayerMCTS() {
		this(1000);
	}
	
	/**
	 * Constructor with ability to set the maximum allowed computation time
	 * @param timeAllowedMillis: allowed computation time, in milliseconds.
	 */
	public PlayerMCTS(int timeAllowedMillis) {
		this.timeAllowedMillis = timeAllowedMillis;
	}
	
	@Override
	public Move play(Game game) {
		MonteCarloTreeSearch mcts = new MonteCarloTreeSearch(game);
		mcts.evaluateTree(timeAllowedMillis);
		System.out.println(mcts.stats());
		return mcts.getBestMove();
	}
}
