package fr.cretedindane.esipe.controllers;

import java.util.*;

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
	private static Queue<Redtokens> redtokens;
	private static Queue<Bluetokens> bluetokens;
	private static LinkedList<Player> players;
	private static Map<Colors, Stack<Card>> fireworks;
	private static Deck deck;
	private static boolean lastAction = false;
	private static boolean gameOver = false;
	private static int numberOfPlayers;
	private static int tips = 8;

	protected static void setPlayers(int numberOfPlayers, int handCards){
		Scanner nameP = new Scanner(System.in);

		/** Creating the players */
		for (int i = 0; i < numberOfPlayers; i++) {
			String name = null;
			ArrayList<Card> hand = new ArrayList<Card>();

			for (int j = 0; j < handCards; j++) {
				hand.add(deck.getTopCard());
				deck.getDeck().remove(j);
			}

			System.out.println("What's the name or the " + (i+1) + "th player? ");
			name = nameP.nextLine();

			players.add(new Player(name, hand));
		}
	}

	public static Card removeCardFromHand(Player player, int cardIndex) {
		return player.getHand().remove(cardIndex);
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

	public static boolean canPlayCard(Card playedCard) {
		Stack<Card> stck = fireworks.get(playedCard.getCardColor());

		if (stck.isEmpty()) {
			return playedCard.getCardValue() == 1;
		}

		return stck.peek().getCardValue() == playedCard.getCardValue() - 1;
	}

	public static boolean endGame() {
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
			System.out.print("Game over - deck is empty! Players lose!");
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		int handCards = 0;
		deck = new Deck();
		players = new LinkedList<Player>();
		fireworks = new HashMap<>();
		redtokens = new LinkedList<>();
		bluetokens = new LinkedList<>();

		for (Colors c : Colors.values()) {
			fireworks.put(c, new Stack<>());
		}

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

		setPlayers(numberOfPlayers, handCards);
		for(Player p: players){
			System.out.println(p);
		}

		/** While the game's still on... */
            while(!(endGame())) {
			/** Action type: 1-Tip, 2-Play or 3-Drop */
			int opt = 0;

			/** Let's make the players play! */
            for(Player actualPlayer: players){
				/**(1) is disabled in phase 1*/
				System.out.println(actualPlayer.getName()+ "'s turn. You can tape \n(1)-To give a tip (disabled in phase 1); \n(2)-To play a card; \n(3)-To drop a card. ");
				Scanner sc = new Scanner(System.in);
				opt = sc.nextInt();

				if (opt > 3 || opt < 1) {
					System.out.println("Unfit option.");
				} else if (opt ==1) {
					System.out.println("\nIn phase 1, the first option is disabled. Please enter an other choice.\n");
				} else {
					switch (opt) {
						case 1:
							/** Give a tip */
							// disabled for phase 1
							break;

						case 2:
							/** Play a card */
							System.out.println("Wich card would you like to play?");
							Scanner scan = new Scanner(System.in);
							int index = scan.nextInt() - 1;
							Card playedCard = removeCardFromHand(actualPlayer, index);
							System.out.println(actualPlayer.getName() + " played a " + playedCard);

							if (canPlayCard(playedCard)) {
								// put the card on the table
								fireworks.get(playedCard.getCardColor()).add(playedCard);
								System.out.println(playedCard);

								/** Check if the firework has been completed */
								if (playedCard.getCardValue() == 5 && bluetokens.size() < tips) {
									System.out.println("Awesome! The " + playedCard.getCardColor() + " firework has been completed!");
									if(bluetokens.size() < 8) bluetokens.add(new Bluetokens());
								}
							} else {
								// if the player put the wrong card on the table
								redtokens.poll();
								System.out.println("Wrong card " + actualPlayer.getName() + "! (" + playedCard + " cannot be played)!");
								System.out.println("Red tokens left: " + redtokens.size());
							}

							/** Give a new card to the player */
							Card newCard = deck.getTopCard();
							actualPlayer.getHand().add(newCard);
							System.out.println(actualPlayer);
							System.out.println("\nActual fireworks: " + fireworks);
							break;

						case 3:
							/** Discard a card */
							System.out.println("Wich card would you like to play?");
							Scanner scanned = new Scanner(System.in);
							int inde = scanned.nextInt() - 1;
							Card discardedCard = removeCardFromHand(actualPlayer, inde);

							System.out.println(actualPlayer.getName() + " discarded a " + discardedCard);

							/** Give a new card to the player */
							Card newOtherCard = deck.getTopCard();
							actualPlayer.getHand().add(newOtherCard);
							System.out.println("New card added to " + actualPlayer.getName() + ".\nNew hand: " + actualPlayer.getHand());

							/** Add a new blue token
							if (bluetokens.size() < tips) {
								bluetokens.add(new Bluetokens());
							}
							*/

							System.out.println("Actual fireworks == " + fireworks);
							break;
					}

					if (deck.size() == 1) {
						lastAction = true;
						System.out.println("Last round!");
					}
				}
			}

		}
	}

}
