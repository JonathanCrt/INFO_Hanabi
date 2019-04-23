package fr.cretedindane.esipe.model;

import java.awt.Color;
import java.util.*;


public class Deck {
	private LinkedList<Card> deck;
	private int maxCard;

	/** Constructor: Build a deck with 50 cards */
		public Deck(){
		LinkedList<Card> deck = new LinkedList<Card>(); 
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
		colors.add(Color.BLUE);
		colors.add(Color.WHITE);
		
		for(Color c: colors) {
			deck.add(new Card(1, c));
			deck.add(new Card(1, c));
			deck.add(new Card(1, c));
			deck.add(new Card(2, c));
			deck.add(new Card(2, c));
			deck.add(new Card(3, c));
			deck.add(new Card(3, c));
			deck.add(new Card(4, c));
			deck.add(new Card(4, c));
			deck.add(new Card(5, c));
		}
		
		/** Shuffle method from java.util.Collection,
		 * Take a list, shuffle it and return an other list */ 
		Collections.shuffle(deck);
		this.deck = deck;
	}
	
	/** Method returns number of cards left in the deck.*/
	public int getLeftCards(){
		return this.deck.size();
	}


}




































