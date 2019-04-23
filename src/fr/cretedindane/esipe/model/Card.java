package fr.cretedindane.esipe.model;

import java.awt.Color;

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

}
