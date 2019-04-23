package fr.cretedindane.esipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {
	private final int redTokens = 3;
	private static Deck deck;
	private static LinkedList<Player> players;
	
	public static void main(String[] args) {
		deck = new Deck();
		players = new LinkedList<Player>();
		
		System.out.println("How many players are you? ");
		Scanner s = new Scanner(System.in);
		int numberOfPlayers = s.nextInt();
		int handCards;
		
		if(numberOfPlayers==2 || numberOfPlayers==3) {
			handCards = 5;
		} else if(numberOfPlayers==4 || numberOfPlayers==5) {
			handCards = 4;
		} else {
			System.out.println("Le nombre de joueurs doit être compris entre 2 et 5.");
			return;
		}
		
		for(int i=0; i<numberOfPlayers; i++) {
			ArrayList<Card> hand = new ArrayList<Card>();
			
			for(int j=0; j<handCards; j++) {
				hand.add(deck.getDeck().get(i));
				deck.getDeck().remove(i);
			}
			players.add(new Player("Joueur n'" + i +" .", hand));
		}
		

		System.out.println("deck: " + deck.getLeftCards());
		System.out.println("players: " + players.size());
		
		
		
		s.close();
		
	}

}
