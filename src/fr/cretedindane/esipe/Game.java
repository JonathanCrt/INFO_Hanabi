package fr.cretedindane.esipe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Game {
	private final int redTokens = 3;
	private static Deck deck;
	private static LinkedList<Player> players;
	
	public static void main(String[] args) {
		deck = new Deck();
		players = new LinkedList<Player>();

		int handCards = 0;

		while (handCards == 0) {
			System.out.println("How many players are you? ");
			Scanner s = new Scanner(System.in);

			int numberOfPlayers = s.nextInt();


			if (numberOfPlayers == 2 || numberOfPlayers == 3) {
				handCards = 5;
			} else if (numberOfPlayers == 4 || numberOfPlayers == 5) {
				handCards = 4;
			} else {
				System.out.println("The game needs 2, 3, 4 or 5 players.");
				handCards = 0;
			}
		}
		s.close();

		/** Creating the players */
		for (int i = 0; i < numberOfPlayers; i++) {
			ArrayList<Card> hand = new ArrayList<Card>();

			for (int j = 0; j < handCards; j++) {
				hand.add(deck.getDeck().get(i));
				deck.getDeck().remove(i);
			}
			players.add(new Player("Player-" + i + " .", hand));
		}

		/** Now that the players are ready with they hand,
		 *  the first player plays: */
		for(int i=0; i<numberOfPlayers; i++){
			Player actualPlayer = players.get(i);
			System.out.println(actualPlayer.getName() + " , it's your turn to play!\n");

			int opt = 0;
			/** While the opt is diffrent of 1, 2 or 3*/
			while(opt == 0) {
				System.out.println("You can tape \n(1)-To give a tip; \n(2)-To play a card; \n(3)-To drop a card. ");
				Scanner s = new Scanner(System.in);
				opt = s.nextInt();

				if(opt > 3 || opt < 1){
					System.out.println("Unfit option.");
					opt = 0;
				} else {
					/** opt == 1, 2 or 3 */
					Action act = new Action(opt);
					/** NEXT CODE HERE*/
				}
			}
		}


		

		
	}

}
