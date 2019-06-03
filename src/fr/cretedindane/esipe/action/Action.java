package fr.cretedindane.esipe.action;
import java.util.List;

public interface Action {
    ActionType getActionType();

    /** Function that return the impacted card by index */
    List<Integer> getImpactedCards();
}