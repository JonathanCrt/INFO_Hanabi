package fr.cretedindane.esipe;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> hand;
	
	public Player(String name, ArrayList<Card> hand) {
		this.name = name;
		this.hand = hand;
	}

	public String getName(){
		return this.name;
	}

	public int getHandSize(){
		return this.hand.size.();
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name)
			.append(" has a hand of ")
			.append(getHandSize());
			.append(" cards.\n");

		return sb.toString():

	}

}
