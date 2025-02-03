package tp_jeu.players.mcts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import tp_jeu.interfaces.Game;
import tp_jeu.interfaces.Game.Move;
import tp_jeu.interfaces.Game.PlayerId;

/**
 * A class implementing a Monte-Carlo Tree Search method (MCTS) for playing two-player games ({@link Game}).
 * @author vdrevell
 *
 */
public class MonteCarloTreeSearch {

	/**
	 * A class to represent an evaluation node in the MCTS tree.
	 * @author vdrevell
	 *
	 */
	static class EvalNode {
		/** The number of simulations run through this node */
		int n;
		
		/** The number of winning runs */
		double w;
		
		/** The move that led to this node */
		Move move;
		
		/** The parent node */
		EvalNode parent;
		
		/** The children of the node: the games states accessible by playing a move from this node state */
		ArrayList<EvalNode> children;
		
		/** The list of moves that have not been explored yet */
		List<Move> unexplored_moves;
		
		/** The player that will play the next move */
		PlayerId next_player;
		
		/** 
		 * The only constructor of EvalNode.
		 * @param game The game state corresponding to this node.
		 * @param move The move that led to this node (null for root).
		 * @param parent The parent of this node in the tree (null for root).
		 */
		EvalNode(Game game, Move move, EvalNode parent) {
			this.parent = parent;
			this.move = move;
			children = new ArrayList<EvalNode>();
			w = 0.0;
			n = 0;
			
			//
			// TODO initialize unexplored_moves and next_player
			//
		}
		
		/**
		 * Compute the Upper Confidence Bound for Trees (UCT) value for the node.
		 * @return UCT value for the node
		 */
		double uct() {
			//
			// TODO implement the UCT function (Upper Confidence Bound for Trees)
			//
			return 0.0;
		}
		
		/**
		 * "Score" of the node, i.e estimated probability of winning when moving to this node
		 * @return Estimated probability of win for the node
		 */
		double score() {
			//
			// TODO implement the score function for a node
			//
			return 0.0;
		}
		
		/**
		 * Update the stats (n and w) of the node with the provided rollout results
		 * @param res Simulation (rollout) results used to update node statistics
		 */
		void updateStats(RolloutResults res) {
			//
			// TODO implement updateStats for a node
			//
		}
	}
	
	/**
	 * A class to hold the results of the rollout phase
	 * Keeps the number of wins for each player and the number of simulations.
	 * @author vdrevell
	 *
	 */
	static class RolloutResults {
		/** The number of wins for player 1 {@link PlayerId#ONE}*/
		double win1;
		
		/** The number of wins for player 2 {@link PlayerId#TWO}*/
		double win2;
		
		/** The number of playouts */
		int n;
		
		/**
		 * The constructor
		 */
		public RolloutResults() {
			reset();
		}
		
		/**
		 * Reset results
		 */
		public void reset() {
			n = 0;
			win1 = 0.0;
			win2 = 0.0;
		}
		
		/**
		 * Add other results to this
		 * @param res The results to add
		 */
		public void add(RolloutResults res) {
			win1 += res.win1;
			win2 += res.win2;
			n += res.n;
		}
		
		/**
		 * Update playout statistics with a win of the player <code>winner</code>
		 * Also handles equality if <code>winner</code>={@link PlayerId#NONE}, adding 0.5 wins to each player
		 * @param winner The id of the player who has won the game simulation
		 */
		public void update(PlayerId winner) {
			//
			// TODO update n, win1 and win2
			//
		}
		
		/**
		 * 
		 * @param playerId
		 * @return The number of wins of player <code>playerId</code>
		 */
		public double nbWins(PlayerId playerId) {
			switch (playerId) {
			case ONE: return win1;
			case TWO: return win2;
			default: return 0.0;
			}
		}
		
		/**
		 * @return The number of playouts
		 */
		public int nbSimulations() {
			return n;
		}
	}
	
	
	/**
	 * The root of the MCTS tree of moves
	 */
	EvalNode root;
	
	/**
	 * The game state at the root of the tree of moves
	 */
	Game rootState;

	
	/**
	 * The constructor
	 * @param game
	 */
	public MonteCarloTreeSearch(Game game) {
		rootState = game.clone();
		root = new EvalNode(rootState, null, null);
	}
	
	/**
	 * Perform a single random playing rollout from the given game state
	 * @param game Initial game state. {@code game} will contain an ended game state when the function returns.
	 * @return The PlayerId of the winner (or NONE if equality or timeout).
	 */
	static PlayerId playRandomlyToEnd(Game game) {
		//
		// TODO implement playRandomlyToEnd
		//
		return null;
	}
	
	/**
	 * Perform nbRuns rollouts from a game state, and returns the winning statistics for both players.
	 * @param game The initial game state to start with (not modified by the function)
	 * @param nbRuns The number of playouts to perform
	 * @return A RolloutResults object containing the number of wins for each player and the number of simulations
	 */
	static RolloutResults rollOut(final Game game, int nbRuns) {
		//
		// TODO implement rollOut
		//
		return null;
	}
	
	/**
	 * Apply the MCTS algorithm during at most <code>timeLimitMillis</code> milliseconds to compute
	 * the MCTS tree statistics.
	 * @param timeLimitMillis Computation time limit in milliseconds
	 */
	public void evaluateTree(int timeLimitMillis) {
		long startTime = System.nanoTime();
		while (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) < timeLimitMillis) {

			EvalNode node = root;
			Game state = rootState.clone();
			
			//
			// TODO: Node selection (down the tree, with UCT tree policy)
			//

			//
			// TODO: Node expansion
			//

			//
			// TODO: Simulation from the new node (rollout)
			//

			//
			// TODO: Backpropagate results (update nodes simulations and wins counts)
			//

		}
		System.out.println("Stopped search after " 
		       + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms. "
		       + "Root stats is " + root.w + "/" + root.n + String.format(" (%.2f%% loss)", 100.0*root.w/root.n));
	}
	
	/**
	 * 
	 * @return The best move to play from the current MCTS tree state.
	 */
	public Move getBestMove() {
		// 
		// TODO Implement MCTS getBestMove
		//
		return null;
	}
	
	public String stats() {
		String str = "MCTS with " + root.n + " evals\n";
		for (EvalNode node : root.children) {
			Move move = node.move;
			double score = node.score();
			str += move + " : " + String.format("%.4f", score) + " (" + node.w + "/" + node.n + ")" + String.format("  %.4f", node.uct()) + ")\n";
		}
		return str;
	}
}
