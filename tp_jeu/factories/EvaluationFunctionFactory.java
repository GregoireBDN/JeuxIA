package tp_jeu.factories;

import java.util.function.Supplier;

import tp_jeu.interfaces.EvaluationFunction;
import tp_jeu.interfaces.Game;

import tp_jeu.games.EvalWinLose;
import tp_jeu.games.draughts.EnglishDraughts;
import tp_jeu.games.tictactoe.TicTacToe;

/**
 * A {@link EvaluationFunction} factory, asking the user to chose the object to create from a list.
 * Checks for compatibility with the game class based on a compatibility list.
 */
public class EvaluationFunctionFactory extends MenuBasedFactory<EvaluationFunction> {
	protected Game game;
	
	public EvaluationFunctionFactory(Game game) {
		this.game = game;
		// Déclare la fonction d'évaluation par défaut (victoire ou défaite), compatible avec tous les jeux
		addGenerator(Game.class, "default (win/lose)",  () -> new EvalWinLose());
		// TODO: Ajouter vos propres fonctions d'évaluation, spécifiques à chaque jeu
		//addGenerator(TicTacToe.class, "l'eval à toto",  () -> new EvalMorpion());
		//addGenerator(EnglishDraughts.class, "ma fonction d'eval",  () -> new EvalDamesStylee());
		//addGenerator(EnglishDraughts.class, "ma fonction d'eval 2",  () -> new EvalDamesTropStylee());
	}
	
	public void addGenerator(Class<? extends Game> cls, String name, Supplier<EvaluationFunction> generator) {
		if (cls.isInstance(game)) {
			addGenerator(cls.getSimpleName() + " " + name, generator);
		}
	}
	
	@Override
	protected String prompt() {
		return "Select the evaluation function:";
	}
}
