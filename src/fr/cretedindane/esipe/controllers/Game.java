package fr.cretedindane.esipe.controllers;

import fr.cretedindane.esipe.action.*;
import fr.cretedindane.esipe.bot.AlwaysDrop;
import fr.cretedindane.esipe.bot.AlwaysPlay;
import fr.cretedindane.esipe.bot.Bot;
import fr.cretedindane.esipe.bot.RandomTip;

import java.util.*;

public class Game {
    private static int round = -1;
    private static int numberOfPlayers;
    private static int handCards = 0;
    private static Map<Player, List<Card>> playerHands;
    private static Queue<Bluetokens> bluetokens;
    private static Queue<Redtokens> redtokens;
    private static LinkedList<Player> players;
    private static Map<Colors, Stack<Card>> fireworks;
    private static Deck deck;
    private static boolean lastRound = false;

    /**
     * create players, players hands and set fireworks
     * @param numberOfPlayers
     * @param handCards
     */

    private static void setGame(int numberOfPlayers, int handCards) {
        Scanner nameP = new Scanner(System.in);

        for(int i = 0; i < numberOfPlayers; ++i) {
            String name = null;

            System.out.println("What's the name or the " + (i + 1) + "th player? ");
            name = nameP.nextLine();
            Player p = new Player(name);
            players.add(p);
            playerHands.put(p, new ArrayList<>());
        }

        for (int j=0; j<handCards; j++) {
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
    private static Card removeCardFromHand(Player player, int cardIndex) {
        return playerHands.get(player).remove(cardIndex);
    }


    /**
     * returns score of the fireworks
     * nothing
     * @return
     */
    private static int score() {
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
    private static boolean canPlayCard(Card playedCard) {
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
    private static boolean endGame() {
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

    private static List<PlayerHand> getOtherPlayerHand(ActionType actionType, Player actualPlayer) {
        int found = -1;
        int index = 1;
        List<PlayerHand> otherHand = new ArrayList<PlayerHand>();

        while(found == -1) {
            if(actionType == ActionType.TIP) {
                System.out.print("Which player would you like to give a tip? ");
                Scanner scan = new Scanner(System.in);
                index = scan.nextInt();
            } else if(actionType == ActionType.DROP) {
                index = 3;
            }else{
                index = 2;
            }

            if(index > 0 && index <= players.size() && actionType == ActionType.TIP) {
                for (Player p : players) {
                    if (p.getPlayerId() == index && p.getPlayerId() != actualPlayer.getPlayerId()) {
                        otherHand.add(new PlayerHand(p, playerHands.get(p)));
                        found = 0;
                    }
                }
            } else if(actionType == ActionType.DROP || actionType == ActionType.PLAY){
                otherHand.add(new PlayerHand(actualPlayer, playerHands.get(actualPlayer)));
                found = 0;
            } else  {
                System.out.println("You're only " + players.size() + " players with a hand of "+ handCards + " cards. Please select a valid index.");
                displayGameStatus();
                found = -1;
            }
        }
        return otherHand;
    }

    private static int selectedIndex(Integer type) {
        int index = -1;
        while (index == -1) {
            if (type == null) {
                System.out.println("Give a (1)NUMBER tip, or (2)COLOR tip?");
                Scanner scan = new Scanner(System.in);
                index = scan.nextInt();
                if (index!= 1 && index !=2) {
                    System.out.println("\n(1) for Number and (2) for Color.\n");
                    index = -1;
                }
            } else {
                System.out.print("Select index: ");
                Scanner scan = new Scanner(System.in);
                index = scan.nextInt()-1;
                if (index > handCards  || index < 0) {
                    System.out.println("\nOut of rang index! You have " + handCards + " cards in your hand.\n");
                    index = -1;
                }
            }
        }
        return index;
    }

    private static Action takeAction(ActionType actionType, List<PlayerHand> playerHand) {
        if (actionType == ActionType.DROP) {
            int index = selectedIndex(0);
            return new DropCardAction(index);
        } else if (actionType == ActionType.PLAY) {
            int index = selectedIndex(0);
            return new PlayCardAction(index);
        } else {
            Player p = playerHand.get(0).getPlayer();
            List<Card> hand = playerHand.get(0).getCards();

            int tipType = selectedIndex(null);
            int index = selectedIndex(0);
            Card card = hand.get(index);
            List<Integer> tips = new ArrayList<>();

            if(tipType == 1){
                for(int i=0; i<hand.size(); i++){
                    Card c = hand.get(i);
                    if(c.getCardValue() == card.getCardValue()){
                        tips.add(i);
                    }
                }
                return new TipAction(p, card.getCardValue(), tips);
            } else {
                for(int i=0; i<hand.size(); i++){
                    Card c = hand.get(i);
                    if(c.getCardColor() == card.getCardColor()){
                        tips.add(i);
                    }
                }
                return new TipAction(p, card.getCardColor(), tips);
            }

        }
    }

    private static Action getAction(Player player){
        int actionTry = 3;
        ActionType actionType = null;
        Action action = null;

        System.out.println("Would you like to:\n(1)-Give a TIP;\n(2)-PLAY a card;\n(3)-DISCARD a card.");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        if(choice == 1){
            actionType = ActionType.TIP;
        } else if(choice == 2){
            actionType = ActionType.PLAY;
        } else {
            actionType = ActionType.DROP;
        }

        while(actionTry > 0){
            action = takeAction(actionType, getOtherPlayerHand(actionType, player));
            if(actionType == ActionType.TIP && bluetokens.isEmpty()) {
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
    private static void takenAction(Player actualPlayer){
        displayGameStatus();
        System.out.println(actualPlayer.getName() + " turn:");

        /** Type action */
        Action action = getAction(actualPlayer);

        switch(action.getActionType()){
            case TIP:
                /** take off a blue token */
                bluetokens.poll();
                if(bluetokens.isEmpty()) {
                    System.out.println("Be careful! No blue token remaining!");
                }

                /** Get selected Player
                 *  Get type Value (Color/Number)
                 *  set knownValue for the card's value*/
                TipAction tip = (TipAction) action;
                Player receivingPlayer = tip.getTippedPlayer();

                if (tip.getType() == TipType.NUMBER) {
                    receivingPlayer.receiveNumberTip(tip.getTipNumber(), tip.getImpactedCards());
                } else {
                    receivingPlayer.receiveColorTip(tip.getTipColor(), tip.getImpactedCards());
                }

                System.out.println(actualPlayer.getName() + " gave a tip to '" + receivingPlayer.getName() + "' at indexed cards " + tip.displayImpactedCard()
                        + (tip.getType() == TipType.NUMBER ? (" as number " + tip.getTipNumber()) : (" as color " + tip.getTipColor()))  + ".");

                break;
            case PLAY:
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
                /** discard the card */
                Card discardedCard = removeCardFromHand(actualPlayer, action.getImpactedCards().get(0));
                System.out.println(actualPlayer.getName() + " discarded a " + discardedCard);

                /** give a new card to the player */
                if(deck.size() > 0) {
                    Card newCard = deck.getTopCard();
                    actualPlayer.cardHasBeenUsed(action.getImpactedCards().get(0));
                    playerHands.get(actualPlayer).add(newCard);
                    System.out.println(actualPlayer.getName() + " has a new card in his hand.\n");
                }

                /** new tip (blue token)*/
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
                lastRound = true;
            }
        }

        if(lastRound && round == -1){
            round = 0;
        }
    }

    private static void displayGameStatus(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n\n-----------------------------------------------------------------------------------------------\n");
        sb.append("-----------------------------------  GAME STATUS  ---------------------------------------------\n");
        sb.append("-----------------------------------------------------------------------------------------------\n");
        sb.append("Players and they hand: \n");
        for(Player p: players){
            sb.append(p).append(" ->");
            sb.append(playerHands.get(p)).append(".\n");
        }
        sb.append("\n-----------------------------------------------------------------------------------------------\n");
        sb.append("Deck size: ");
        sb.append(deck.size());
        sb.append("\n-----------------------------------------------------------------------------------------------\n");
        sb.append("Fireworks status: ");
        sb.append(fireworks);
        sb.append("\n-----------------------------------------------------------------------------------------------\n");
        sb.append("Blue tokens left (Tips): ");
        sb.append(bluetokens.size());
        sb.append("\n-----------------------------------------------------------------------------------------------\n");
        sb.append("Red tokens left (Hearts): ");
        sb.append(redtokens.size());
        sb.append("\n-----------------------------------------------------------------------------------------------\n\n");
        
        String status = sb.toString();
        System.out.println(status);
    }


    public static void main(String[] args){
        // init all
        deck = new Deck();
        players = new LinkedList<Player>();
        fireworks = new HashMap<>();
        redtokens = new LinkedList<>();
        bluetokens = new LinkedList<>();
        playerHands = new HashMap<>();

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

        while(handCards == 0) {
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

        /** Set players and they hand */
        setGame(numberOfPlayers, handCards);

        /** Let's make the players play! */
        for (Player p : players) {
            while(!endGame()) {
                takenAction(p);
            }
        }

        /** Test Bots
        List<Bot> bots = new LinkedList<>();
        Map<Bot, List<Card>> botHands = new HashMap<>();

        Bot bot1 = new AlwaysDrop("Discarded");
        Bot bot2 = new AlwaysPlay("Player");
        Bot bot3 = new RandomTip("Tipper");

        bots.add(bot1);
        bots.add(bot2);
        bots.add(bot3);

        for (Colors c : Colors.values()){
            fireworks.put(c, new Stack<>());
        }

        for(Bot b: bots) {
            botHands.put(b, new ArrayList<>());
        }

         handCards = 5;
        for (int j=0; j<handCards; j++) {
            for (Bot b : bots) {
                Card c = deck.getTopCard();
                botHands.get(b).add(c);
            }
        }

        for (Bot b : bots) {
            while (!endGame()) {
                takenAction(b);
            }
        }
         */
    }

}
