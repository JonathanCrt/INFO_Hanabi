
public class DropcardAction implements Action {
    private int cardIndex;

    public ActionType getActionType(){
        return ActionType.DROP;
    }

    public List<Integer> getImpactedCards(){
        return Collections.singletonList(cardIndexToDiscard);
    }
}