package fr.cretedindane.esipe.action;
import java.util.Collections;
import java.util.List;

public class DropCardAction implements Action {
    private int cardIndex;

    public ActionType getActionType(){
        return ActionType.DROP;
    }

    public List<Integer> getImpactedCards(){
        return Collections.singletonList(cardIndex);
    }
}