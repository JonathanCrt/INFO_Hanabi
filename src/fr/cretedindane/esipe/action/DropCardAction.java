
public class DropcardAction implements Actions {
    private int cardIndex;

    public ActionType getActionType(){
        return ActionType.DISCARD;
    }

    public List<Integer> getImpactedCards(){
        return Collections.singletonList(cardIndexToDiscard);
    }
}