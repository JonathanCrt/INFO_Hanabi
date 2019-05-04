package fr.cretedindane.esipe.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import fr.cretedindane.esipe.controllers.*;

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
	private int play = 0;
	private Queue<Redtokens> redtokens;
	private Queue<Bluetokens> bluetokens;
	protected static int tips = 8;
	private static Deck deck;
	private static LinkedList<Player> players;
	private static Map<Colors, Stack<Card>> fireworks;
	private static boolean lastAction = false;
	private static boolean gameOver = false;
	private static int numberOfPlayers;

	protected static void creatingPlayers(int numberOfPlayers, int handCards){
		
		Scanner nameP = new Scanner(System.in);
		
		/** Creating the players */
		for (int i = 0; i < numberOfPlayers; i++) {
			String name = null;
			ArrayList<Card> hand = new ArrayList<Card>();
			
			for (int j = 0; j < handCards; j++) {
				hand.add(deck.getTopCard());
				deck.getDeck().remove(j);
			}			
			
			System.out.println("Quel est le nom du " + (i+1) + "ème joueur? ");
			name = nameP.nextLine();
						
			players.add(new Player(name, hand));
		}
	}

	public static Card removeCardFromHand(Player player, int cardIndex) {
		return player.getHand().remove(cardIndex);
	}

	public static boolean canPlayCard(Card playedCard) {
		Stack<Card> s = fireworks.get(playedCard.getCardColor());

		if (s.isEmpty()) {
			return playedCard.getCardValue() == 1;
		}

		return s.peek().getCardValue() == playedCard.getCardValue() - 1;
	}

	public static int score() {
		int score = 0;
		for (Colors s : fireworks.keySet()) {
			Stack<Card> cards = fireworks.get(s);
			if (!cards.isEmpty()) {
				score += cards.peek().getCardValue();
			}
		}

		if (score == 25) {
			System.out.println("Score = 25! Legendary, everyone left speechless, stars in their eyes!");
		} else if (score >= 21 && score < 25) {
			System.out.println("Score = " + score + "! Amazing, they will be talking about it for weeks!");
		} else if (score >= 16 && score < 21) {
			System.out.println("Score = " + score + "! Excellent. Crowd pleasing.");
		} else if (score >= 11 && score < 16) {
			System.out.println("Score = " + score + "! Honorable attempt, but quickly forgotten...");
		} else if (score >= 6 && score < 11) {
			System.out.println("Score = " + score + "! Mediocre, just a hint of scattered applause...");
		} else {
			System.out.println("Score = " + score + "! Horrible, booed by the crowd...");
		}

		return score;
	}

	public static boolean endGame(Queue<Redtokens> redtokens) {
		if (redtokens.isEmpty()) {
			System.out.println("Game over - all red tokens have been played! Players lose!");
			return true;
		}

		boolean fireworksDone = true;
		for (Colors c : Colors.values()) {
			Stack<Card> cards = fireworks.get(c);
			fireworksDone &= (cards.size() == 5 && cards.peek().getCardValue() == 5);
		}

		if (fireworksDone) {
			System.out.println("Congratulation! You completed all the five fireworks!");
			System.out.println("Your score is...");
			System.out.println(score());
			return true;
		}

		if (lastAction == true) {
			System.out.println("Game over - deck is empty! Players lose!");
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		int handCards = 0;
		deck = new Deck();
		players = new LinkedList<Player>();
		Queue<Redtokens> redtokens = new LinkedList<>();
		Queue<Bluetokens> bluetokens = new LinkedList<>();

		for (int i=0; i<8; i++) {
			bluetokens.add(new Bluetokens());
		}

		for (int i=0; i<3; i++) {
			redtokens.add(new Redtokens());
		}


		System.out.println("How many players are you? ");
		Scanner s = new Scanner(System.in);
		while (handCards == 0) {
			numberOfPlayers = s.nextInt();
			s.nextLine();
			
			if (numberOfPlayers == 2 || numberOfPlayers == 3) {
				handCards = 5;
			} else if (numberOfPlayers == 4 || numberOfPlayers == 5) {
				handCards = 4;
			} else {
				System.out.println("The game needs 2, 3, 4 or 5 players.");
				handCards = 0;
			}
			
		}
		
		creatingPlayers(numberOfPlayers, handCards);
		/** Now that the players are ready with they hand,
		 *  the first player plays: */
		for(int i=0; i<numberOfPlayers; i++){
			Player actualPlayer = players.get(i);
			System.out.println(actualPlayer.getName() + " , it's your turn to play!\n");

			/** Action type: 1-Tip, 2-Play or 3-Drop */
			int opt = 0;

			/** While the opt is diffrent of 1, 2 or 3*/
			while(opt == 0) {
				/**(1) is disbled in phase 1*/
				System.out.println("You can tape \n(1)-To give a tip (disabled in phase 1); \n(2)-To play a card; \n(3)-To drop a card. ");
				Scanner sc = new Scanner(System.in);
				opt = sc.nextInt();
				sc.close();

				if (opt > 3 || opt < 1) {
					System.out.println("Unfit option.");
					opt = 0;
				} else if (opt ==1) {
					System.out.println("In phase 1, the first option is disabled. Please enter an other choise.");
					opt = 0;
				} else {
					switch (opt) {
						case 1:
							/** Give a tip */
							// disabled for phase 1
							break;

						case 2:
							/** Play a card */
							System.out.println("Wich card would you like to discard?");
							Scanner scan = new Scanner(System.in);
							int index = scan.nextInt();
							Card playedCard = removeCardFromHand(actualPlayer, index);
							System.out.println(actualPlayer.getName() + " played a " + playedCard);

							if (canPlayCard(playedCard)) {
								// put the card on the table
								fireworks.get(playedCard.getCardColor()).add(playedCard);

								/** Check if the firewaork has been completed */
								if (playedCard.getCardValue() == 5 && bluetokens.size() < tips) {
									System.out.println("Awesome! The " + playedCard.getCardColor() + " firework has been completed!");
									bluetokens.add(new Bluetokens());
								}

							} else {
								/** if the player put the wrong card on the table */
								redtokens.poll();
								System.out.println("Wrong card " + actualPlayer.getName() + "! (" + playedCard + " cannot be played)!");
							}

							/** Give a new card to the player */
							Card newCard = deck.getTopCard();
							actualPlayer.getHand().add(newCard);
							break;

						case 3:
							/** Discard a card */
							Card discardedCard = removeCardFromHand(actualPlayer, i);

							/** Give a new card to the player */
							Card newOtherCard = deck.getTopCard();
							actualPlayer.getHand().add(newOtherCard);

							/** Add a new blue token
							if (bluetokens.size() < tips) {
								bluetokens.add(new Bluetokens());
							}
							*/

							System.out.println(actualPlayer.getName() + " discarded a " + discardedCard);
							break;
					}

					if (deck.size() == 1) {
						lastAction = true;
						System.out.println("Last round!");
					}

					gameOver = endGame(redtokens);
					if(gameOver == true){
						System.out.println("Game end.");
						return;
					}
				}
			}
		}


		

		
	}

}
