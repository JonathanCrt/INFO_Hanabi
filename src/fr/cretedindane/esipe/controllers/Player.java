package fr.cretedindane.esipe.controllers;

import fr.cretedindane.esipe.action.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/** Stay abstract
 *  Get rid of hand and create a new class to map every player with his hand
 *  Add known cards as Vectors*/
public class Player {
	private static final AtomicInteger count = new AtomicInteger(1);
	private int playerId;
	private String name;
	private List<Integer> knownNumbers;
	private List<Colors> knownColors;

	public Player(String name) {
		this.name = name;
		this.playerId = count.incrementAndGet();
		this.knownColors = new Vector<>(Arrays.asList(null, null, null, null, null));
		this.knownNumbers = new Vector<>(Arrays.asList(null, null, null, null, null));
	}

	public String getName() {
		return this.name;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public void receiveNumberTip(int number, List<Integer> indices) {
		for (Integer i : indices) {
			this.knownNumbers.remove(i.intValue());
			this.knownNumbers.add(i, number);
		}
	}

	public void receiveColorTip(Colors suit, List<Integer> indices) {
		for (Integer i : indices) {
			this.knownColors.remove(i.intValue());
			this.knownColors.add(i, suit);
		}
	}

	public void cardHasBeenUsed(int indices) {
		for(int i=indices; i < this.knownNumbers.size()-1; i++) {
			this.knownNumbers.set(i, this.knownNumbers.get(i+1));
			this.knownColors.set(i, this.knownColors.get(i+1));
		}
		this.knownNumbers.set(4, null);
		this.knownColors.set(4, null);
	}

	public static int selectedIndex(Integer type, Integer actualPlayerId, int handCards, int numberOfPlayers, LinkedList<Player> players) {
		int index = -1;
		while (index == -1) {
			if (type == null && actualPlayerId == null) {
				System.out.println("Give a (1)NUMBER tip, or (2)COLOR tip?");
				Scanner scan = new Scanner(System.in);
				index = scan.nextInt();
				if (index!= 1 && index !=2) {
					System.out.println("\n(1) for Number and (2) for Color.\n");
					index = -1;
				}
			} else {
				System.out.println("Which card would you like to discard?");
				Scanner scan = new Scanner(System.in);
				index = scan.nextInt();
				if (index > handCards - 1 || index < 0) {
					System.out.println("\nOut of rang index! You have " + handCards + " cards in your hand.\n");
					index = -1;
				}
			}
		}
		return index;
	}

	public Action takeAction(ActionType actionType, Map<Colors, Stack<Card>> fireworks, List<PlayerHand> playerHand,
							 int remainingTips, int remainingFuses, int handCards, int numberOfPlayers, LinkedList<Player> players) {

		if (actionType == ActionType.DROP) {
			return new DropCardAction();
		} else if (actionType == ActionType.PLAY) {
			return new PlayCardAction();
		} else {
			Player p = playerHand.get(0).getPlayer();
			List<Card> hand = playerHand.get(0).getCards();

			int index = selectedIndex(0, 0, handCards, numberOfPlayers, players);
			int tipType = selectedIndex(null, null, handCards, numberOfPlayers, players);
			Card card = hand.get(index);
			List<Integer> tips = new ArrayList<>();
			TipType tip;
			if(tipType == 1){
				tip = TipType.NUMBER;
			} else {
				tip = TipType.COLOR;
			}

			if(tip == TipType.NUMBER){
				for(int i=0; i<hand.size(); i++){
					Card c = hand.get(i);
					if(c.getCardValue() == card.getCardValue()){
						tips.add(i);
					}
				}
				return new TipAction(p, card.getCardValue(), tips);
			} else {
				for(int i=0; i<hand.size(); i++){
					Card c = hand.get(i);
					if(c.getCardColor() == card.getCardColor()){
						tips.add(i);
					}
				}
				return new TipAction(p, card.getCardColor(), tips);
			}

		}
	}



}
