package fr.cretedindane.esipe.controllers;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void correctNumberBlueTokens() {
		try {
			assertEquals(-1, Game.getRound());
		}
		catch(Exception e) {
			fail("Error : round is incorrect");
		
		}
	}
	
	@Test
	public void correctNbOflayers() throws NullPointerException {
		
		assertNotNull(Game.getNumberOfPlayers());
		
	}
	
	@Test
	public void testLastRound() {
		assertFalse(Game.isLastRound());
	}
	
	
	@Test
	public void testTotalRound() {
		if(Game.isLastRound() == true) {
			assertNotEquals(0, Game.getTotalRounds() );
		}
	}
	
	@Test 
	public void correctNumberOfTokens() {
		if(Game.isLastRound()) {
			assertEquals(8, Game.sizeBlueTokens());
		}
	}
	
	@Test
	public void correctNumberOfPlayers(){
		if(Game.getTotalRounds() != 0 && Game.getNumberOfPlayers() <=1 || Game.getNumberOfPlayers() > 5) {
			fail("Error : number of players is incorrect ! ");
		}
	}
	
	
	

}
