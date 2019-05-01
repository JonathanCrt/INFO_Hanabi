package fr.cretedindane.esipe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**CHECK LIST
 * Creation of the main deck
 * Creation of the players
 * Creation of the hand of the players
 * Starting the game by going around starting by the first player
 * Players choises 1, 2 et 3
 * End of the game: empty deck -> last round
 * End of the game: redTokens = 0
 * End of the game: colored Fireworks have a size of 5 */


public class Game {
	private int score = 0;
	private static int redTokens = 3;
	private static int blueTokens = 8;
	/** Initialization of players, fireworkds cards, the deck, the droppedCards */
	private static Deck deck;
	private static LinkedList<Player> players;
	private static ArrayList<Card> redFirework;
	private static ArrayList<Card> blueFirework;
	private static ArrayList<Card> greenFirework;
	private static ArrayList<Card> yellowFirework;
	private static ArrayList<Card> whiteFirework;
	private static ArrayList<Card> droppedCards;
	/** Last round?  End of the game when the last card of the deck is picked.*/
	private boolean finalRound = false;

	
	public static void main(String[] args) {
		deck = new Deck();
		players = new LinkedList<Player>();
		redFirework = new ArrayList<Card>();
		blueFirework = new ArrayList<Card>();
		greenFirework = new ArrayList<Card>();
		yellowFirework = new ArrayList<Card>();
		whiteFirework = new ArrayList<Card>();

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
				/**(1) is disbled in phase 1*/
				System.out.println("You can tape \n(1)-To give a tip (disabled in phase 1); \n(2)-To play a card; \n(3)-To drop a card. ");
				Scanner s = new Scanner(System.in);
				opt = s.nextInt();
				s.close();

				if (finalRound == true){
					System.out.println("End of the game.\n Your score = " + score);
					return;
				} else if (opt > 3 || opt < 1) {
					System.out.println("Unfit option.");
					opt = 0;
				} else if (opt ==1) {
					System.out.println("In phase 1, the first option is disabled. Please enter an other choise.");
					opt = 0;
				} else {
					/** opt == 1, 2 or 3 */
					Action act = new Action(opt);
					System.out.println(act);
					if(act == 1){
						/** Give a tip */
						// To add for phase 2
					} else if(act ==2){
						/** Play a card */
						if(deck.size() == 1){
							// no more cards lefts -> last round
							System.out.println("Last round!");
							finalRound = true;
						}

						/** player select a card, then remove it from his hand
						 * the player can select where to put the card: red, green, blue, white or yellow emplacement.
						 * If he thinks it's a card with number 1, he can put it in the other list
						 * if he puts it in the other list:
						 * 		we check the number and the color of the card then verify if it's an error or not.*/

						// In this section there's a lot of duplicated code
						// Using an enum to manage the actions is better than an 'int' value

						/**
						if (numberOfPlayers == 2 || numberOfPlayers == 3) {
							System.out.println("Wich card you want to play? [0-1-2-3-4]");
						} else if (numberOfPlayers == 4 || numberOfPlayers == 5) {
							System.out.println("Wich card you want to play? [0-1-2-3]");
						}

						Scanner s = new Scanner(System.in);
						nbCard = s.nextInt();
						if (numberOfPlayers == 2 || numberOfPlayers == 3) {
							if(nbCard > 4 || nbCard < 0  ){
								System.out.println();

							}
						} else if (numberOfPlayers == 4 || numberOfPlayers == 5) {
							System.out.println("Wich card you want to play? [0-1-2-3]");
						}

						s.close();

						*/

					} else {
						/** Drop a card */

					}
					/** NEXT CODE HERE*/
				}
			}
		}


		

		
	}

}
