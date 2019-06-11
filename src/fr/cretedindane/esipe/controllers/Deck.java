package fr.cretedindane.esipe.controllers;

import java.util.*;
import java.util.Collections;

public class Deck {
	private final Queue<Card> deck;

	/** Constructor: Build a deck with 50 cards */
	public Deck(){
		LinkedList<Card> deck = new LinkedList<Card>();

		for(Colors c: Colors.values()) {
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
		return deck.poll();
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Deck's size: ").append(deck.size()).append("\n");

		return sb.toString();
	}

}




































