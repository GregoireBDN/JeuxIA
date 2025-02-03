package tp_jeu.factories;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * A generic factory that allows the user to choose the object generator from a list on screen
 * @param <T> The type of objects to create
 */
public class MenuBasedFactory<T> implements Supplier<T> {
	/**
	 * A named Supplier of type T
	 * @author vdrevell
	 *
	 * @param <T>
	 */
	static class NamedSupplier<T> implements Supplier<T> {
		String name;
		Supplier<T> supplier;
		public NamedSupplier(String name, Supplier<T> generator) {
			this.name = name;
			this.supplier = generator;
		}
		@Override
		public String toString() { return name; };
		@Override
		public T get() { return supplier.get(); }
	}

	/**
	 * The list of named constructor functions
	 */
	protected ArrayList<NamedSupplier<T>> list = new ArrayList<NamedSupplier<T>>();

	/**
	 * Asks the user for a number between parameters min and max
	 * @param min
	 * @param max
	 * @return the number chosen by the user
	 */
	static protected int choseNumber(int min, int max) {
		return chooseNumber(min, max, "Please enter your choice");
	}
	
	/**
	 * Asks the user for a number between parameters min and max, with custom prompt
	 * @param min
	 * @param max
	 * @param prompt
	 * @return the number chosen by the user
	 */
	static protected int chooseNumber(int min, int max, String prompt) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int chosenNum = -1;
		
		while (chosenNum < min || chosenNum > max) {
			System.out.print(prompt + " [" + min + "-" + max + "]: ");
			if (sc.hasNextInt()) {
				chosenNum = sc.nextInt();
			}
			else {
				if (sc.hasNext()) sc.next();
			}
		}
		return chosenNum;
	}
	
	/**
	 * Display a menu and let the user chose an item from the list.
	 * @return The list element chosen by the user
	 */
	protected NamedSupplier<T> chooseInList() {
		if (list.isEmpty()) {
			System.out.println("No choice.");
			return null;
		}
		
		if (list.size() == 1) {
			System.out.println("> " + list.get(0));
			return list.get(0);
		}
		
		for (int i=0; i<list.size(); ++i) {
			System.out.println(" " + i + ". " + list.get(i));
		}
		return list.get(choseNumber(0, list.size()-1));
	}
	
	/**
	 * The prompt text for choosing in the list of constructors
	 */
	protected String prompt() {
		return "Enter your choice:";
	}
	
	/**
	 * Adds a new named constructor function in the menu
	 * @param name: the displayed name
	 * @param generator: the constructor function
	 */
	public void addGenerator(String name, Supplier<T> generator) {
		list.add(new NamedSupplier<T>(name, generator));
	}
	
	public NamedSupplier<T> getSupplier() {
		System.out.println(prompt());
		return chooseInList();
	}
	
	/**
	 * Asks the user to choose a constructor and returns a new object
	 * @return the object generated the supplier chosen by the user
	 */
	public T get() {
		return getSupplier().get();
	}
}
