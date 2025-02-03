package tp_jeu.games.draughts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import tp_jeu.interfaces.Game;

/**
 * Implementation of the English Draughts game.
 * @author vdrevell
 *
 */
public class EnglishDraughts extends Game {
	CheckerBoard board;
	PlayerId playerId;
	int nbTurn, nbKingTurnsWithoutCapture;
	
	/**
	 * Class representing a move in the English draughts game
	 * A move is an ArrayList of Integers, corresponding to the successive tile numbers (Manouri notation)
	 * toString is overrided to provide Manouri notation output.
	 * @author vdrevell
	 *
	 */
	public class DraughtsMove extends ArrayList<Integer> implements Game.Move {

		private static final long serialVersionUID = -8215846964873293714L;

		@Override
		public String toString() {
			Iterator<Integer> it = this.iterator();
			Integer from = it.next();
			StringBuffer sb = new StringBuffer();
			sb.append(from);
			while (it.hasNext()) {
				Integer to = it.next();
				if (board.neighborDownLeft(from)==to || board.neighborUpLeft(from)==to
						|| board.neighborDownRight(from)==to || board.neighborUpRight(from)==to) {
					sb.append('-');
				}
				else {
					sb.append('x');
				}
				sb.append(to);
				from = to;
			}
			return sb.toString();
		}
	}
	
	/**
	 * Default constructor: initializes a game on the standard 8x8 board.
	 */
	public EnglishDraughts() {
		this(8);
	}
	
	/**
	 * Constructor with custom boardSize (to play on a boardSize x boardSize checkerBoard).
	 * @param boardSize See {@link CheckerBoard#CheckerBoard(int)} for valid board sizes. 
	 */
	public EnglishDraughts(int boardSize) {
		this.board = new CheckerBoard(boardSize);
		this.playerId = PlayerId.ONE;
		this.nbTurn = 1;
		this.nbKingTurnsWithoutCapture = 0;
	}
	
	/**
	 * Copy constructor
	 * @param d The game to copy
	 */
	EnglishDraughts(EnglishDraughts d) {
		this.board = d.board.clone();
		this.playerId = d.playerId;
		this.nbTurn = d.nbTurn;
		this.nbKingTurnsWithoutCapture = d.nbKingTurnsWithoutCapture;
	}
	
	@Override
	public EnglishDraughts clone() {
		return new EnglishDraughts(this);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(nbTurn);
		sb.append(". ");
		sb.append(this.playerId==PlayerId.ONE?"W":"B");
		sb.append(":");
		sb.append(board.toString());
		return sb.toString();
	}
	
	@Override
	public String playerName(PlayerId playerId) {
		switch (playerId) {
		case ONE:
			return "Player with the whites";
		case TWO:
			return "Player with the blacks";
		case NONE:
		default:
			return "Nobody";
		}
	}
	
	@Override
	public String view() {
		return board.boardView() + "Turn #" + nbTurn + ". " + playerName(playerId) + " plays.\n";
	}
	
	/**
	 * Check if a tile is empty
	 * @param square
	 * @return
	 */
	boolean isEmpty(int square) {
		return board.get(square) == CheckerBoard.EMPTY;
	}
	
	boolean isAdversary(int square) {
		switch (playerId) {
		case ONE: return board.isBlack(square);
		case TWO: return board.isWhite(square);
		default: return false;
		}
	}
	
	boolean isMine(int square) {
		// return myMen().contains(square) || myKings().contains(square);
		switch (playerId) {
		case ONE: return board.isWhite(square);
		case TWO: return board.isBlack(square);
		default: return false;
		}
	}
	
	ArrayList<Function<Integer,Integer>> getMoveFuncs(boolean goUp, boolean goDown) {
		ArrayList<Function<Integer,Integer>> moveFuncs = new ArrayList<Function<Integer,Integer>>();
		
		if (goUp) {
			moveFuncs.add(board::neighborUpLeft);
			moveFuncs.add(board::neighborUpRight);
		}
		if (goDown) {
			moveFuncs.add(board::neighborDownLeft);
			moveFuncs.add(board::neighborDownRight);
		}
		
		return moveFuncs;
	}
	
	Set<DraughtsMove> jumpMovesFrom(int from, boolean goUp, boolean goDown) {
		return jumpMovesFrom(from, goUp, goDown, new HashSet<Integer>());
	}
	
	Set<DraughtsMove> jumpMovesFrom(int from, boolean goUp, boolean goDown, HashSet<Integer> taken) {
		Set<DraughtsMove> moves = new HashSet<DraughtsMove>();
		
		for (Function<Integer,Integer> moveFunc : getMoveFuncs(goUp, goDown) ) {
			int jump = moveFunc.apply(from);
			int dest = moveFunc.apply(jump);
			
			if (jump != 0 && dest != 0 && isAdversary(jump) && isEmpty(dest) &&!taken.contains(jump)) {
				DraughtsMove move = new DraughtsMove();
				move.add(from);
				move.add(dest);
				HashSet<Integer> newTaken = new HashSet<Integer>(taken);
				newTaken.add(jump);
				Set<DraughtsMove> nextMoves = jumpMovesFrom(dest, goUp, goDown, newTaken);
				if (nextMoves.isEmpty()) {
					moves.add(move);
				}
				else {
					for (DraughtsMove sequel : nextMoves) {
						sequel.add(0, from);
						moves.add(sequel);
					}
				}
			}
		}
		
		return moves;
	}
	
	Set<DraughtsMove> standardMovesFrom(int from, boolean goUp, boolean goDown) {
		Set<DraughtsMove> moves = new HashSet<DraughtsMove>();
		
		for (Function<Integer,Integer> moveFunc : getMoveFuncs(goUp, goDown) ) {
			int dest = moveFunc.apply(from);
			
			if (dest != 0 && isEmpty(dest)) {
				DraughtsMove move = new DraughtsMove();
				move.add(from);
				move.add(dest);
				
				moves.add(move);
			}
		}
		
		return moves;
	}
	
	
	boolean equalityCondition() {
		return nbKingTurnsWithoutCapture >= 25;
	}
	

	@Override
	public List<Move> possibleMoves() {
		// TODO Auto-generated method stub
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if (equalityCondition()) {
			return moves;
		}
		
		boolean goDown = playerId == PlayerId.TWO;
		boolean goUp = playerId == PlayerId.ONE;
		
		ArrayList<Integer> myPawns = new ArrayList<Integer>();
		myPawns.ensureCapacity(12);
		for (int pawn = 1; pawn <= board.size * board.size / 2; ++pawn) {
			if ( isMine(pawn) ) {
				myPawns.add( pawn );
			}
		}
		
		for (Integer from : myPawns) {
			boolean isKing = board.isKing(from);
			moves.addAll( jumpMovesFrom(from, goUp || isKing, goDown || isKing) );
		}
		if (moves.isEmpty()) {
			for (Integer from : myPawns) {
				boolean isKing = board.isKing(from);
				moves.addAll( standardMovesFrom(from, goUp || isKing, goDown || isKing) );
			}
		}
		return moves;
	}

	@Override
	public void play(Move move) {
		// TODO Auto-generated method stub
		if (!(move instanceof DraughtsMove))
			return;
		
		if (playerId == PlayerId.NONE)
			return;
		
		// Cast and apply the move
		DraughtsMove m = (DraughtsMove) move;
		boolean captureOccured = false;
		int from = m.get(0);
		boolean playedKing = board.isKing(from);
		for (int k=1; k<m.size(); ++k) {
			int to = m.get(k);
			int jump = board.squareBetween(from,  to);
			if (jump != 0) {
				board.removePawn(jump);
				captureOccured = true;
			}
			board.movePawn(from, to);
			from = to;
		}
		
		// Promote to king if the pawn ends on the opposite of the board
		if ((playerId == PlayerId.TWO && board.inBottomRow(from))
				|| (playerId == PlayerId.ONE && board.inTopRow(from))) {
			board.crownPawn(from);
		}
		
		// Next player
		playerId = playerId.other();
		
		if (playerId == PlayerId.ONE) {
			nbTurn++;
		}
		
		if (captureOccured || !playedKing) {
			nbKingTurnsWithoutCapture = 0;
		}
		else {
			nbKingTurnsWithoutCapture++;
		}
	}

	@Override
	public PlayerId player() {
		return playerId;
	}

	@Override
	public PlayerId winner() {
		if (board.isEmpty())
			return PlayerId.NONE;
		
		if (equalityCondition())
			return PlayerId.NONE;
		
		if (possibleMoves().isEmpty())
			return playerId.other();
		
		return null;
	}
}
