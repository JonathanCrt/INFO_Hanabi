package fr.cretedindane.esipe.controllers;

import java.util.List;

public class PlayerHand {
    private Player player;
    private List<Card> cards;

    public PlayerHand(Player player, List<Card> cards) {
        this.player = player;
        this.cards = cards;
    }
}
