package fr.cretedindane.esipe.action;

import fr.cretedindane.esipe.controllers.Colors;
import fr.cretedindane.esipe.controllers.Player;

import java.util.List;

/**
 * Create an TipType enum with Colors(enum from controllers) and Number;
 * Create two constructors in TipAction, one with Colors and the other with Number;
 * Also override Action() methods.
 */

public class TipAction implements Action {
    private List<Integer> tippedCard;

    private Player tippedPlayer;
    private TipType type;
    private Integer tipNumber; /**When we used 'int we couldn't put it equals to null at line 32*/
    private Colors tipColor;

    /** Giving a tip by number */
    public TipAction(Player tippedPlayer, int tipNumber, List<Integer> tippedCard){
        this.tippedPlayer = tippedPlayer;
        this.type = TipType.NUMBER;
        this.tipNumber = tipNumber;
        this.tipColor = null;
        this.tippedCard = tippedCard;
    }

    /** Giving a tip by color */
    public TipAction(Player tippedPlayer, Colors tipColor, List<Integer> tippedCard){
        this.tippedPlayer = tippedPlayer;
        this.type = TipType.COLOR;
        this.tippedCard = tippedCard;
        this.tipColor = tipColor;
        this.tipNumber = null;
    }

    public Player getTippedPlayer() {
        return tippedPlayer;
    }

    public Integer getTipNumber(){
        return this.tipNumber;
    }

    public Colors getTipColor(){
        return this.tipColor;
    }

    public String displayImpactedCard(){
        StringBuilder sb = new StringBuilder();
        if(tippedCard != null){
            sb.append("[");
            for(int i=0; i<tippedCard.size()-1; i++){
                sb.append(tippedCard.get(i)+1).append(" ");
            }
            sb.append(tippedCard.get(tippedCard.size()-1)+1);
            sb.append("]");
        }
        return sb.toString();
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