package fr.cretedindane.esipe.controllers;

import java.awt.*;

public class Card {
	private int value;
	private Colors color;
	
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
		sb.append(this.color)
			.append(" card, number #")
			.append(this.value)
			.append(".")
			.append("\n");

		return sb.toString();
	}

}
