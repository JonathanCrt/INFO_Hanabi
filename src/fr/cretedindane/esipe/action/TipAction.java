package fr.cretedindane.esipe.action;

import fr.cretedindane.esipe.controllers.Player;

import java.awt.*;
import java.util.List;

/**
 * Create an TipType enum with Colors(enum from controllers) and Number;
 * Create two constructors in TipAction, one with Colors and the other with Number;
 * Also override Action() methods.
 */

public class TipAction implements Action {
    private List<Integer> tippedCard;

    private Player tipedPlayer;
    private TipType type;
    private Integer tipNumber; /**When we used 'int we couldn't put it equals to null at line 32*/
    private Color tipColor;

    /** Giving a tip by number */
    public TipAction(Player tipedPlayer, int tipNumber, List<Integer> tippedCard){
        this.tipedPlayer = tipedPlayer;
        this.type = TipType.NUMBER;
        this.tipNumber = tipNumber;
        this.tipColor = null;
        this.tippedCard = tippedCard;
    }

    /** Giving a tip by color */
    public TipAction(Player tipedPlayer, Color tipColor, List<Integer> tippedCard){
        this.tipedPlayer = tipedPlayer;
        this.type = TipType.COLOR;
        this.tippedCard = tippedCard;
        this.tipColor = tipColor;
        this.tipNumber = null;
    }

    public Player getTipedPlayer() {
        return tipedPlayer;
    }

    public TipType getType(){ return this.type; }

    @Override
    public ActionType getActionType(){
        return ActionType.TIP;
    }

    @Override
    public List<Integer> getImpactedCards(){
        return tippedCard;
    }
}