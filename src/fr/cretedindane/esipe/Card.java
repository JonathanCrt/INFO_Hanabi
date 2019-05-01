package fr.cretedindane.esipe;

import java.awt.*;

public class Card {
	private int value;
	private Color color;
	
	/** Constructor: Init card with a color and a value */
	public Card(int value, Color color) {
		this.color = color;
		this.value = value;
	}
		
	public Color getCardColor() {
		return this.color;
	}
	
	public int getCardValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.color)
			.append(" card, number #")
			.append(this.value)
			.append(".")
			.append("\n");

		return sb.toString();
	}

}
