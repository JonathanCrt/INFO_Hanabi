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
	
}
