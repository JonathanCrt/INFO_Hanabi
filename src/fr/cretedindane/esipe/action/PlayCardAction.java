package fr.cretedindane.esipe.action;
import java.util.Collections;
import java.util.List;

public class PlayCardAction implements Action {
    private int cardIndex;

    @Override
    public ActionType getActionType(){
        return ActionType.PLAY;
    }

    @Override
    public List<Integer> getImpactedCards(){
        return Collections.singletonList(cardIndex);
    }

}