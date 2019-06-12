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

public class RandomTip extends Bot {

    public RandomTip(String name){
        super(name);
    }

    @Override
    public Action takenAction(Map<Colors, Stack<Card>> fireworks,
                             List<PlayerHand> playerHands){
        Player p = playerHands.get(0).getPlayer();
        List<Card> hand = playerHands.get(0).getCards();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, hand.size());
        Card card = hand.get(randomIndex);

        List<Integer> tips = new ArrayList<>();
        int tipType = (int) (Math.random() * 1);

        if (tipType == 1) {
            // find all indices matching card number
            for (int i=0; i<hand.size(); i++) {
                Card c = hand.get(i);
                if (c.getCardValue() == card.getCardValue()) {
                    tips.add(i);
                }
            }
            return new TipAction(p, card.getCardValue(), tips);
        } else {
            // find all indices matching card suit
            for (int i=0; i<hand.size(); i++) {
                Card c = hand.get(i);
                if (c.getCardColor() == card.getCardColor()) {
                    tips.add(i);
                }
            }

            return new TipAction(p, card.getCardColor(), tips);
        }
    }
}
