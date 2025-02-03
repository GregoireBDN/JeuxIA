package tp_jeu.interfaces;

import java.util.List;

/**
 * An abstract class representing a two-player game.
 * 
 * @author vdrevell
 *
 */

public abstract class Game implements Cloneable {
	/**
	 * An interface for game moves.
	 * @author vdrevell
	 *
	 */
	static public interface Move { }
	
	/**
	 * An enum to represent the current player and the winner in a two-player game.
	 * Also provides the {@link PlayerId#NONE} value to represent for instance equality.
	 * @author vdrevell
	 *
	 */
	static public enum PlayerId {
		/** Nobody (e.g equality) */
		NONE,
		/** Player 1 */
		ONE,
		/** Player 2 */
		TWO;
		
		/**
		 * @return the opponent of this player
		 */
		public PlayerId other() {
			switch (this) {
			case ONE:
				return TWO;
			case TWO:
				return ONE;
			case NONE:
				return NONE;
			default:
				return null;
			}
		}
	}
	
	/**
	 * 
	 * @return the list of all possible moves from the current state. The order has to be deterministic.
	 */
	public abstract List<Move> possibleMoves();
	
	/**
	 * Play the provided move on the current game state. 
	 * The game state is updated and the game switches to next player.  
	 * @param move The move to play, should be a valid move (in {@link Game#possibleMoves()}).
	 */
	public abstract void play(Move move);
	
	/**
	 * 
	 * @return The PlayerId of the current player
	 */
	public abstract PlayerId player();
	
	/**
	 * Function to detect the end of a game and get the winner.
	 * @return If the game has ended, the PlayerID of the winner or {@link PlayerId#NONE} if equality.
	 *         Otherwise, <code>null</code> if the game is still running.
	 */
	public abstract PlayerId winner();
	
	@Override
	public abstract Game clone();
	
	/**
	 * ASCII-art representation of the game state
	 * @return a string containing an ASCII art view of the the game
	 */
	public String view() {
		return toString();
	}
	
	/**
	 * Human readable name of the players. Can be overridden when players have specific names,
	 * (e.g. blacks and whites in checkers or chess).
	 * @param playerId 
	 * @return the name of the player represented by playerId
	 */
	public String playerName(PlayerId playerId) {
		switch (playerId) {
		case ONE:
			return "Player 1";
		case TWO:
			return "Player 2";
		case NONE:
		default:
			return "Nobody";
		}
	}
}
