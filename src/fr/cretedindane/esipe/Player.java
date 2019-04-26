package fr.cretedindane.esipe;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
	private String name;
	private ArrayList<Card> hand;
	
	public Player(String name, ArrayList<Card> hand) {
		this.name = name;
		this.hand = hand;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name)
			.append(" has a hand of: ")
			.append(this.hand)
			.append(".\n");

		return sb:

	}

}
