package fr.cretedindane.esipe.controllers;


public class Card {
	private final  int value;
	private final Colors color;
	
	/** Constructor: Init card with a color and a value */
	public Card(int value, Colors c) {
		this.color = c;
		this.value = value;
	}
		
	public Colors getCardColor() {
		return this.color;
	}
	
	public int getCardValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(")
			.append(this.value)
			.append(", ")
			.append(this.color)
			.append(" )  ");

		return sb.toString();
	}

}
