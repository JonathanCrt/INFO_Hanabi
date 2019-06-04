package fr.cretedindane.esipe.controllers;

import fr.cretedindane.esipe.action.Action;
import fr.cretedindane.esipe.action.ActionType;
import fr.cretedindane.esipe.action.TipAction;
import fr.cretedindane.esipe.action.TipType;

import java.util.*;

/** TODO:
 * getType() problem in line 200 and 202
 * check TipAction constructor*/

public class Game {
    private static Map<Player, List<Card>> playerHands;
    private static Queue<Redtokens> redtokens;
    private static Queue<Bluetokens> bluetokens;
    private static LinkedList<Player> players;
    private static Map<Colors, Stack<Card>> fireworks;
    private static Deck deck;
    private static int numberOfPlayers;
    private static int handCards = 0;
    // count till last round
    private static boolean lastRound = false;
    private static int round = -1;

    /**
     * create players, players hands and set fireworks
     * @param numberOfPlayers
     * @param handCards
     */
    static void setGame(int numberOfPlayers, int handCards) {
        for (Player p : players) {
            playerHands.put(p, new ArrayList<>());
        }
        for (int i=0; i<5; i++) {
            for (Player p : players) {
                Card c = deck.getTopCard();
                playerHands.get(p).add(c);
            }
        }
        for (Colors s : Colors.values()) {
            fireworks.put(s, new Stack<>());
        }
    }

    /**
     * returns selected card by player
     * @param player
     * @param cardIndex
     * @return
     */
    public static Card removeCardFromHand(Player player, int cardIndex) {
        return playerHands.get(player).remove(cardIndex);
    }


    /**
     * returns score of the fireworks
     * nothing
     * @return
     */
    public static int score() {
        int score = 0;
        for (Colors s : fireworks.keySet()) {
            Stack<Card> cards = fireworks.get(s);
            if (!cards.isEmpty()) {
                score += cards.peek().getCardValue();
            }
        }

        if (score == 25) {
            System.out.println("Score = 25! Legendary, everyone left speechless, stars in their eyes!");
        } else if (score >= 21 && score < 25) {
            System.out.println("Score = " + score + "! Amazing, they will be talking about it for weeks!");
        } else if (score >= 16 && score < 21) {
            System.out.println("Score = " + score + "! Excellent. Crowd pleasing.");
        } else if (score >= 11 && score < 16) {
            System.out.println("Score = " + score + "! Honorable attempt, but quickly forgotten...");
        } else if (score >= 6 && score < 11) {
            System.out.println("Score = " + score + "! Mediocre, just a hint of scattered applause...");
        } else {
            System.out.println("Score = " + score + "! Horrible, booed by the crowd...");
        }

        return score;
    }

    /**
     * which check if can we play the card, elsewhere it discarded.
     * @param playedCard
     * @return false/true
     */
    public static boolean canPlayCard(Card playedCard) {
        Stack<Card> stck = fireworks.get(playedCard.getCardColor());

        /* check if the card in handPlayer is 1 : true */
        if (stck.isEmpty()) {
            return playedCard.getCardValue() == 1;
        }

        /* peek : take the card into stack and the card in handPlayer
         * if equals, return true */
        return stck.peek().getCardValue() == playedCard.getCardValue() - 1;
    }

    /**
     * marks end of the game and notices players of the score
     * nothing
     * @return true if end of game or false
     */
    public static boolean endGame() {
        if (redtokens.isEmpty()) {
            System.out.println("Game over - all red tokens have been played! Players lose!");
            return true;
        }

        boolean fireworksDone = true;
        for (Colors c : Colors.values()) {
            Stack<Card> cards = fireworks.get(c);
            fireworksDone &= (cards.size() == 5 && cards.peek().getCardValue() == 5);
        }

        if (fireworksDone) {
            System.out.println("Congratulation! You completed all the five fireworks!");
            System.out.println("Your score is...");
            System.out.println(score());
            return true;
        }

        if (round == numberOfPlayers) {
            System.out.print("Game over - deck is empty! Players lose!");
            return true;
        }

        return false;
    }

    public static int selectedIndex(Integer type, Integer actualPlayerId){
        int index = -1;
        while(index == -1) {
            if(type==null && actualPlayerId==null) {
                System.out.println("Which card would you like to play?");
                Scanner scan = new Scanner(System.in);
                index = scan.nextInt();
                if (index > handCards - 1 || index < 0) {
                    System.out.println("\nWARNING! You have " + handCards + " cards in your hand.\n");
                    index = -1;
                }
            } else {
                System.out.println("Give a tip to?");
                for(Player p: players){
                    System.out.println("Player: " + p + "is number: " + (p.getPlayerId()));
                }
                Scanner scan = new Scanner(System.in);
                index = scan.nextInt();
                if (index > numberOfPlayers || index < 1 || actualPlayerId+1 == index) {
                    System.out.println("\nWARNING! You have to choose a player who's not you.\n");
                    index = -1;
                }
                index -= 1;
            }
        }
        return index;
    }

    private List<Card> getOtherPlayerHands(Player player) {
       return playerHands.get(player);
    }

    Action getAction(Player player){
        int actionTry = 3;
        Action action = null;
        while(actionTry > 0) {
            action = player.takeAction(fireworks, getOtherPlayerHands(player), bluetokens.size(), redtokens.size());
            if(action.getActionType() == ActionType.TIP && bluetokens.isEmpty()) {
                actionTry--;
            } else {
                return action;
            }
        }
        System.out.println("Player " + player.getName() + " attempted 3 times to give a tip, when no tips where available.");
        System.exit(1);
        return action;
    }

    /** Define type of action took by a player */
    Action (Player actualPlayer){
        Action action = getAction(actualPlayer);
        switch(action.getActionType()){
            case TIP:
                /******* Give a tip ******/
                //take off a blue token
                bluetokens.poll();

                // Give player some information
                TipAction tip = (TipAction) action;
                Player receivingPlayer = tip.getTipedPlayer();
                if (tip.getType() == TipType.NUMBER) {
                    receivingPlayer.receiveNumberTip(tip.getType(), tip.getImpactedCards());
                } else {
                    receivingPlayer.receiveSuitTip(tip.getType(), tip.getImpactedCards());
                }

                System.out.println(actualPlayer.getName() + " gave a tip to '" + receivingPlayer.getName() + "' at card " + tip.getImpactedCards() + " are "
                        + (tip.getType() == TipType.NUMBER ? tip.getType() : tip.getType()));
                if(redtokens.isEmpty()) {
                    System.out.println("Be careful! No blue token remaining!");
                }

                break;
            case PLAY:
                /******* Play a card ********/

                /** played card */
                Card playedCard = removeCardFromHand(actualPlayer, action.getImpactedCards().get(0));
                System.out.println(actualPlayer.getName() + " played a " + playedCard);

                /** play card */
                if (canPlayCard(playedCard)) {
                    // put the card on the table
                    fireworks.get(playedCard.getCardColor()).add(playedCard);
                    System.out.println(playedCard);

                    /** Check if the firework has been completed */
                    if (playedCard.getCardValue() == 5 && bluetokens.size() < 8) {
                        System.out.println("Awesome! The " + playedCard.getCardColor() + " firework has been completed!");
                        if(bluetokens.size() < 8) bluetokens.add(new Bluetokens());
                    }
                } else {
                    // if the player put the wrong card on the table
                    redtokens.poll();
                    System.out.println("Wrong card " + actualPlayer.getName() + "! (" + playedCard + " cannot be played)!");
                    System.out.println("Red tokens left: " + redtokens.size());
                }

                /** Give a new card to the player */
                if(deck.size() != 0) {
                    Card newCard = deck.getTopCard();
                    playerHands.get(actualPlayer).add(newCard);
                    System.out.println(actualPlayer);
                } else {
                    if(numberOfPlayers-round-1 == 0){
                        System.out.println("\n -- Last round played!\n");
                    } else {
                        System.out.println("No more cards left. " + (numberOfPlayers - round - 1) + " rounds to go.");
                    }
                }
                System.out.println("\nActual fireworks: " + fireworks);

                break;
            case DROP:
                /********* Discard a card *********/

                /** discard the card */
                Card discardedCard = removeCardFromHand(actualPlayer, action.getImpactedCards().get(0));
                System.out.println(actualPlayer.getName() + " discarded a " + discardedCard);

                /** give a new card to the player */
                Card newCard = deck.getTopCard();
                actualPlayer.cardHasBeenUsed(action.getImpactedCards().get(0));
                playerHands.get(actualPlayer).add(newCard);
                System.out.println(actualPlayer.getName() + " has a new card in his hand.\n");

                /** new tip (blueTokken)*/
                if(bluetokens.size() < 8){
                    bluetokens.add(new Bluetokens());
                }

                break;
        }

        //check round
        if(lastRound && round > -1 && round < numberOfPlayers){
            round++;
        }

        if (deck.size() == 0) {
            if(!lastRound){
                System.out.println("\nLast Round!!\n");
            }
            lastRound = true;
            if(round == -1){
                round = 0;
            }
        }

        return action;
    }


    public static void main(String[] args) {
        // init all
        deck = new Deck();
        players = new LinkedList<Player>();
        fireworks = new HashMap<>();
        redtokens = new LinkedList<>();
        bluetokens = new LinkedList<>();

        for (int i=0; i<8; i++) {
            bluetokens.add(new Bluetokens());
        }

        for (int i=0; i<3; i++) {
            redtokens.add(new Redtokens());
        }

        /** Set game from number of players*/
        System.out.println("How many players are you? ");
        Scanner s = new Scanner(System.in);

        while(!(s.hasNextInt())){
            System.out.println("Are you 2, 3, 4 or 5 players?");
            s = new Scanner(System.in);
        }

        while (handCards == 0) {
            numberOfPlayers = s.nextInt();
            s.nextLine();

            if (numberOfPlayers == 2 || numberOfPlayers == 3) {
                handCards = 5;
            } else if (numberOfPlayers == 4 || numberOfPlayers == 5) {
                handCards = 4;
            } else {
                System.out.println("The game needs 2, 3, 4 or 5 players.");
                handCards = 0;
            }
        }

        setGame(numberOfPlayers, handCards);
        for(Player p: players){
            System.out.println(p);
        }

        /** Let's make the players play! */
        //action type now not an integer
        //
        //
        //
        //
        // TODO



    }

}
