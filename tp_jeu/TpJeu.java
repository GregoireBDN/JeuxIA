package tp_jeu;

import tp_jeu.factories.GameFactory;
import tp_jeu.factories.PlayerFactory;
import tp_jeu.interfaces.Game;
import tp_jeu.interfaces.Player;
import tp_jeu.interfaces.Game.Move;
import tp_jeu.interfaces.Game.PlayerId;

/**
 * Main class for two-player gameplay.
 * Can be used to play any game with the {@link Game} interface, with players classes 
 * implementing the {@link Player} interface.
 * 
 * The class provides menu building facilities to instanciate the game and players.
 * 
 * @author vdrevell
 *
 */

public class TpJeu {
	/**
	 * Two-player gameplay program entry point (main)
	 *
	 * - The program first instanciates a Game from user input, and then two Players based on user choice.
	 * - Then the game loop starts, letting both players play until the end of the game
	 * - The winner (or equality) is finally displayed before the program quits.
	 * @param args
	 */
	@SuppressWarnings("incomplete-switch")
	public static void main(String[] args) {
		System.out.println("TP Jeux - M1 IL/CR ISTIC, option IA (v25.02)");
		
		Game game = new GameFactory().get();
		
		Player player1 = new PlayerFactory(game.playerName(PlayerId.ONE), game).get();
		Player player2 = new PlayerFactory(game.playerName(PlayerId.TWO), game).get();
		
		for (int k=0; k<400; ++k) {		
			System.out.print(game.view());
			
			Move move = null;

			switch (game.player()) {
			case ONE:
				move = player1.play(game);
				break;
			case TWO:
				move = player2.play(game);
				break;
			}
			
			System.out.println("Played " + move);
			System.out.println();
			
			game.play(move);
			
			if (game.winner() != null) {
				System.out.println(game.view());
				System.out.println(game.playerName(game.winner()) + " wins!");
				break;
			}
		}
	}
}
