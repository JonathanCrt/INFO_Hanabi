package fr.cretedindane.esipe.bot;

import fr.cretedindane.esipe.action.Action;
import fr.cretedindane.esipe.controllers.Card;
import fr.cretedindane.esipe.controllers.Colors;
import fr.cretedindane.esipe.controllers.Player;
import fr.cretedindane.esipe.controllers.PlayerHand;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public abstract class Bot extends Player {

    public Bot(String name){
        super(name);
    }

    public abstract Action takenAction(Map<Colors, Stack<Card>> fireworks, List<PlayerHand> playerHands);
}
