package tp_jeu.factories;

import tp_jeu.interfaces.Player;
import tp_jeu.interfaces.Game;

import tp_jeu.players.*;

/**
 * A {@link Player} factory, asking the user to chose the object to create from a list
 */
public class PlayerFactory extends MenuBasedFactory<Player> {
	protected String playerName;
	
	public PlayerFactory(String playerName, Game game) {
		this.playerName = playerName;
		// Human players
		addGenerator("Human",           () -> new PlayerHuman(false) );
		addGenerator("Human with list", () -> new PlayerHuman(true)  );
		// Random players
		addGenerator("Random",        () -> new PlayerRandom()      );
		addGenerator("Win or Random", () -> new PlayerWinOrRandom() );
		// AI-based players
		addGenerator("MiniMax", () -> new PlayerMinMax(chooseNumber(1,20,"Depth"), new EvaluationFunctionFactory(game).get()) );
		addGenerator("AlphaBeta", () -> new PlayerAlphaBeta(chooseNumber(1,25,"Depth"), new EvaluationFunctionFactory(game).get()) );
		// TODO: Décommenter la ligne suivante pour activer l'algorithme de jeu MCTS
		//addGenerator("MCTS",  () -> new PlayerMCTS(1000 * chooseNumber(1,30,"Time allowed in seconds"))  );
	}
	
	@Override
	protected String prompt() {
		return "Select player type for " + playerName + ":";
	}
}
