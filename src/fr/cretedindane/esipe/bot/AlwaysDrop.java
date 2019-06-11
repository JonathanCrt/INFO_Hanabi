package fr.cretedindane.esipe.bot;

import fr.cretedindane.esipe.action.Action;
import fr.cretedindane.esipe.action.DropCardAction;
import fr.cretedindane.esipe.controllers.Card;
import fr.cretedindane.esipe.controllers.Colors;
import fr.cretedindane.esipe.controllers.PlayerHand;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class AlwaysDrop extends Bot{

    public AlwaysDrop(String name){
        super(name);
    }

    @Override
    public Action takeAction( Map<Colors, Stack<Card>> fireworks, List<PlayerHand> playerHands, int remainingTips, int remainingFuses){
        return new DropCardAction(0);

    }
}
