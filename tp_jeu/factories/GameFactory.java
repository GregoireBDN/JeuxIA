package tp_jeu.factories;

import tp_jeu.interfaces.Game;
import tp_jeu.games.tictactoe.TicTacToe;
import tp_jeu.games.draughts.EnglishDraughts;

/**
 * A {@link Game} factory, asking the user to chose the object to create from a list
 */
public class GameFactory extends MenuBasedFactory<Game> {
	public GameFactory() {
		addGenerator("English Draughts (8x8)",    () -> new EnglishDraughts()  );
		addGenerator("English Draughts on 10x10", () -> new EnglishDraughts(10));
		addGenerator("English Draughts on 6x6",   () -> new EnglishDraughts(6) );
		addGenerator("Tic Tac Toe",   () -> new TicTacToe()  );
	}
	
	@Override
	protected String prompt() {
		return "Select the game to play:";
	}
}
