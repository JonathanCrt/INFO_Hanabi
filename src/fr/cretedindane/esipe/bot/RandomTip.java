package fr.cretedindane.esipe.bot;

import fr.cretedindane.esipe.action.Action;
import fr.cretedindane.esipe.action.TipAction;
import fr.cretedindane.esipe.action.TipType;
import fr.cretedindane.esipe.controllers.Card;
import fr.cretedindane.esipe.controllers.Colors;
import fr.cretedindane.esipe.controllers.Player;
import fr.cretedindane.esipe.controllers.PlayerHand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTip extends Player {

    public RandomTip(String name){
        super(name);
    }

    @Override
    public Action takeAction(Map<Colors, Stack<Card>> fireworks,
                             List<PlayerHand> playerHands,
                             int remainingTips,
                             int remainingFuses){
        Player p = playerHands.get(0).getPlayer();
        List<Card> hand = playerHands.get(0).getCards();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, hand.size());
        Card card = hand.get(randomIndex);

        List<Integer> indices = new ArrayList<>();
        TipType tipType = ThreadLocalRandom.current().nextInt() % 2 == 0 ? TipType.NUMBER : TipType.COLOR;

        if (tipType == TipType.NUMBER) {
            // find all indices matching card number
            for (int i=0; i<hand.size(); i++) {
                Card c = hand.get(i);
                if (c.getCardValue() == card.getCardValue()) {
                    indices.add(i);
                }
            }
            return new TipAction(p, card.getCardValue(), indices);
        } else {
            // find all indices matching card suit
            for (int i=0; i<hand.size(); i++) {
                Card c = hand.get(i);
                if (c.getCardColor() == card.getCardColor()) {
                    indices.add(i);
                }
            }

            return new TipAction(p, card.getCardColor(), indices);
        }
    }
}
