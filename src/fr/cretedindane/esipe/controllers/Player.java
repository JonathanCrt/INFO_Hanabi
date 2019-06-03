package fr.cretedindane.esipe.controllers;

import fr.cretedindane.esipe.action.Action;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/** Stay abstract
 *  Get rid of hand and create a new class to map every player with his hand
 *  Add known cards as Vectors*/
public abstract class Player {
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

	public String getName(){
		return this.name;
	}

	public int getPlayerId(){
		return this.playerId;
	}

	public abstract Action takeAction(Map<Colors, Stack<Card>> fireworks,
			List<Card> playerHand, int remainingTips, int remainingFuses);

	public void receiveNumberTip(int number, List<Integer> indices) {
		for (Integer i : indices) {
			this.knownNumbers.remove(i.intValue());
			this.knownNumbers.add(i, number);
		}
	}

	public void receiveSuitTip(Colors suit, List<Integer> indices) {
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

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Player)){
			return false;
		}
		Player p = (Player) obj;
		return(this.getName().equals(p.getName()));
	}
}
