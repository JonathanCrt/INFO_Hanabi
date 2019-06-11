package fr.cretedindane.esipe.bot;

import fr.cretedindane.esipe.action.Action;
import fr.cretedindane.esipe.controllers.Card;
import fr.cretedindane.esipe.controllers.Colors;
import fr.cretedindane.esipe.controllers.Player;
import fr.cretedindane.esipe.controllers.PlayerHand;

import java.util.*;

public abstract class Bot extends Player {
    public Bot(String name) {
        super(name);
    }

    public abstract Action takeAction(
            Map<Colors, Stack<Card>> fireworks,
            List<PlayerHand> playerHands,
            int remainingTips,
            int remainingFuses);
}

