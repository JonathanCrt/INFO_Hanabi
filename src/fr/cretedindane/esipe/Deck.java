package fr.cretedindane.esipe;

import java.awt.*;


public class Deck {
	private ArrayList<Card> deck;

	/** Constructor: Build a deck with 50 cards */
	public Deck(){
		ArrayList<Card> deck = new ArrayList<Card>(); 
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
	public int size(){
		return this.deck.size();
	}

	/** Returns the top card of the deck. */
	public Card getTopCard() {
		return cards.poll();
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Deck's size: ").append(getLeftCards()).append("\n");

		return sb.toString();
	}

}




































