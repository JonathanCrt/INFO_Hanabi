package fr.cretedindane.esipe.controllers;

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
		return this.hand.size();
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Player)){
			return false;
		}
		Player p = (Player) obj;
		return(this.getHand().equals(p.getHand())  && this.getName().equals(p.getName()));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name);
		sb.append(" has a hand of ");
		sb.append(getHandSize());
		sb.append(" cards.\n");

		return sb.toString();

	}

}
