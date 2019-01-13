import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is a part of the blackjack project.
 * It is a simple, text based blackjack game.
 *
 * This is the class where everything comes together.
 * The blackjack engine initializes the game and plays hands until the game is over.
 */
public class BlackJackEngine {
    private Scanner scanner;
    private DeckProcessor deckProcessor;
    private Party gameParty;
    private Deck houseDeck;

    public BlackJackEngine(){
        scanner = new Scanner(System.in);
        deckProcessor = new DeckProcessor();
        gameParty = new Party();
        houseDeck = new Deck();
        houseDeck.initializeOG();
    }

    /**
     * The play method is used to start a new game.
     * It is the main functionality of this class.
     */
    public void play(){
        System.out.print("\033[H\033[2J");
        System.out.println("Welcome to BlackJack!");
        initializeParty();
        // Enter the main game loop.
        // Here we repeatedly play hands until the players loose
        boolean playing = true;
        while(playing){
            if(gameParty.getParty().size() > 0){
                System.out.println("\nA new round has begun.");
                betPhase();
                gamePhase();
                determineOutcome();
                resetAndReturnCards();
                removeCashlessPlayers();
            }
            else{
                System.out.println("\nGame Over. No players left.");
                playing = false;
            }
        }
    }

    /**
     * A method to remove all the players without any money in their wallet from the party.
     */
    public void removeCashlessPlayers(){
        List<Player> party = gameParty.getParty();
        List<Player> toRemove = new ArrayList<>();
        for (Player player : party) {
            if(player.getCash() == 0){
                toRemove.add(player);
            }
        }
        for (Player player : toRemove) {
            gameParty.removePlayer(player);
        }
    }

    /**
     * A method that returns all the cards from the players and the dealer back to the house-deck.
     * Also resets player hand-specific booleans and counter fields.
     */
    public void resetAndReturnCards(){
        for (Player player : gameParty.getParty()){
            returnCards(player);
            player.resetCommandCount();
            player.resetDoubled();
        }
        returnCards(gameParty.getDealer());
    }

    /**
     * A method used for initializing the games party.
     * Here the player/players get to choose the amount of players playing and their names.
     */
    public void initializeParty(){
        System.out.println("How many players are playing?" + "\n" + "1, 2, 3 etc...");
        int players = getNextInt();
        if(players < 1){
            System.out.println("Don't be silly...");
            initializeParty();
        }
        if(players == 1){
            System.out.println("What is your name?");
            String name = getNextString();
            Player player = new Player(name);
            gameParty.addPlayer(player);
        }
        else if(players > 1){
            System.out.println("What are their names?");
            for (int i = 1; i <= players; i++) {
                String name = getNextString();
                Player player = new Player(name);
                gameParty.addPlayer(player);
            }
        }
    }

    /**
     * A method for taking the bets from all players in the game.
     * This is the first thing that happens in the beginning of a new round.
     */
    public void betPhase() {
        for (Player player : gameParty.getParty()) {
            System.out.println(player.getName() + " you have " + player.getCash() + "$" + " Place your bet:");
            takePlayersBet(player);
        }
    }

    /**
     * Helper method used to take a players bet and then place that bet.
     * @param player    the player of which bet we are taking
     */
    public void takePlayersBet(Player player){
        int bet = getNextInt();
        if (player.getCash() >= bet && bet >= 0) {
            player.placeBet(bet);
        }
        else{
            while (!(player.getCash() >= bet) || !(bet >= 0)){
                bet = getNewBet(player,bet);
            }
            player.placeBet(bet);
        }
    }

    /**
     * An additional helper method for taking bets.
     * This method allows a player to attempt a new bet if the previous one was illegal for some reason.
     * @param player    the player to place a new bet
     * @param bet       the bet that was made, which was illegal
     * @return  integer value of the sum of the new bet
     */
    public int getNewBet(Player player, int bet){
        int betAttempt = bet;
        if (player.getCash() < bet) {
            while (betAttempt > player.getCash()) {
                System.out.println("You can't bet more than you have, try again...");
                betAttempt = getNextInt();
            }
        }
        if (bet < 0) {
            while (betAttempt < 0) {
                System.out.println("You can't bet negative values, try again...");
                betAttempt = getNextInt();
            }
        }
        return betAttempt;
    }

    /**
     * This method shuffles the deck, deals the flop and then proceeds to enter the 'hand playing' loop.
     */
    public void gamePhase(){
        deckProcessor.shuffleDeck(houseDeck);
        dealFlop();
        System.out.println("\nThe flop has been dealt.");
        for (Player player : gameParty.getParty()){
            System.out.println("\nThe house has: " + gameParty.getDealer().getHand().getCard(0).cardToString() + " + [HIDDEN]");
            playHand(player);
        }
    }

    /**
     * This method plays the dealers hand and determines the outcome for each player in the party.
     */
    public void determineOutcome(){
        playDealerHand();
        System.out.print("\033[H\033[2J");
        System.out.println("Dealers hand value: " + calculateHandValue(gameParty.getDealer().getHand()) + "               " + gameParty.getDealer().getHand().deckToStringSingleRow());
        System.out.println("\n");
        for (Player player : gameParty.getParty()) {
            System.out.println(player.getName() + "'s hand value: " + calculateHandValue(player.getHand()) + "                " + player.getHand().deckToStringSingleRow());
        }
        // The outcome is determined for the players
        List<Player> partyList = gameParty.getParty();
        for (Player player : partyList) {
            if(checkBust(player)){
                System.out.println(player.getName() + " you LOST since you bust. You now have: " + player.getCash() + "$");
            }
            else if(checkTie(player)){
                player.increaseCash(player.getBet());
                System.out.println(player.getName() + " you TIED.  You now have: " + player.getCash() + "$");
            }
            else if(checkDealerBust(player)){
                player.increaseCash(player.getBet()*2);
                System.out.println(player.getName() + " you WON since the dealer bust. You now have: " + player.getCash() +"$");
            }
            else if(checkCloserTo21(player)){
                player.increaseCash(player.getBet()*2);
                System.out.println(player.getName() + " you WON since you were closer than the dealer to 21. You now have: " + player.getCash() +"$");
            }
            else if(checkFartherFrom21(player)){
                System.out.println(player.getName() + " you LOST since you were further away than the dealer from 21. You now have: " + player.getCash() + "$");
            }
            else{
                System.out.println(">>>SOMETHING HAS GONE WRONG<<<");
            }
        }
    }

    /**
     * Helper method to check if a players hand has gone over 21.
     * @param player    The player of which we want to check for
     * @return          True or false
     */
    public boolean checkBust(Player player){
        if(calculateHandValue(player.getHand()) > 21){
            return true;
        }
        return false;
    }

    /**
     * Helper method to check if a player and the dealer have the same hand value.
     * @param player    The player of which we want to check for
     * @return          True or false
     */
    public boolean checkTie(Player player){
    int playerHand = calculateHandValue(player.getHand());
    int dealerHand = calculateHandValue(gameParty.getDealer().getHand());
    if(playerHand == dealerHand && playerHand < 22){
        return true;
        }
    return false;
    }

    /**
     * Helper method to check if a player wins by the dealer going over 21 while they did not.
     * @param player    The player of which we want to check for
     * @return          True or false
     */
    public boolean checkDealerBust(Player player){
        int playerHand = calculateHandValue(player.getHand());
        int dealerHand = calculateHandValue(gameParty.getDealer().getHand());
        if(playerHand < 22 && dealerHand > 21){
            return true;
        }
        return false;
    }

    /**
     * Helper method to check if a player is closer to 21 than the dealer.
     * @param player    The player of which we want to check for
     * @return          True or false
     */
    public boolean checkCloserTo21(Player player){
        int playerHand = calculateHandValue(player.getHand());
        int dealerHand = calculateHandValue(gameParty.getDealer().getHand());
        if(playerHand < 22 && dealerHand < 22){
            int playerDifference = 21-playerHand;
            int dealerDifference = 21-dealerHand;
            if( playerDifference < dealerDifference){
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if a player is further from 21 than the dealer.
     * @param player    The player of which we want to check for
     * @return          True or false
     */
    public boolean checkFartherFrom21(Player player){
        int playerHand = calculateHandValue(player.getHand());
        int dealerHand = calculateHandValue(gameParty.getDealer().getHand());
        if(playerHand < 22 && dealerHand < 22){
            int playerDifference = 21-playerHand;
            int dealerDifference = 21-dealerHand;
            if( playerDifference > dealerDifference){
                return true;
            }
        }
        return false;
    }

    /**
     * A method that plays the dealers hand.
     * The dealer will hit on anything under 17.
     * However the dealer will also hit on a soft 17.
     */
    public void playDealerHand(){
        // Calculate and save the dealers hand value
        int handValue = calculateHandValue(gameParty.getDealer().getHand());
        // Update the dealers aceCount
        gameParty.getDealer().getHand().calculateAceCount();
        // The dealer hits on a soft 17
        if(handValue == 17 && gameParty.getDealer().getHand().getAceCount() >= 1){
            hit(gameParty.getDealer());
            playDealerHand();
        }
        // The dealer hits with a hand value under 17
        if(handValue < 17){
            hit(gameParty.getDealer());
            playDealerHand();
        }
    }

    /**
     * A method to draw two cards to each player in the party aswell as the dealer.
     */
    public void dealFlop(){
        deckProcessor.drawCards(2,houseDeck,gameParty.getDealer().getHand());
        for(Player player : gameParty.getParty()){
            deckProcessor.drawCards(2,houseDeck,player.getHand());
        }
    }

    /**
     * A method for playing a players hand.
     * The player is prompted to make a choice of what to do.
     * Then the player may enter their command and if it is legal the command is executed.
     * @param player the player of which is to play their hand
     */
    public void playHand(Player player){
        printHandCommand(player);
        executeCommand(player);
    }

    /**
     * A method to display information to the player regarding their hands value and what their legal commands are.
     * @param player    the player of which to display information for
     */
    public void printHandCommand(Player player){
        System.out.println(player.getName() + "'s hand value: "  + calculateHandValue(player.getHand()) + "\n" + player.getHand().deckToStringRows());
        System.out.println("What do you want to do?" + " You can: " + getLegalCommands(player));
    }

    /**
     * This method prompts the player to enter a command and then executes that command if it is legal.
     * @param player
     */
    public void executeCommand(Player player){
        String command = getNextString();
        if(command.equalsIgnoreCase("hit") && calculateHandValue(player.getHand()) < 21){
            hit(player);
            player.increaseCommandCount();
            playHand(player);
        }
        else if(command.equalsIgnoreCase("double") && player.getCommandCount() == 0 && player.getCash() >= player.getBet()){
            player.increaseCommandCount();
            player.doDouble();
            player.decreaseCash(player.getBet());
            player.increaseBet(player.getBet());
            hit(player);
            playHand(player);

        }
        else if(command.equalsIgnoreCase("split") && player.getCommandCount() == 0 && player.getHand().getCard(0).getNumber() == player.getHand().getCard(1).getNumber()){
            player.increaseCommandCount();
        }
        else if(command.equalsIgnoreCase("stand")){

        }
        else{
            System.out.println("Please enter a legal command...");
            executeCommand(player);
        }
    }

    /**
     * A method that generates a string with all the legal commands the player can execute
     * @param player the player of which to generate a string for
     * @return the string containing the legal commands
     */
    public String getLegalCommands(Player player){
        String commandString = "Stand";
        if(calculateHandValue(player.getHand()) < 21 && !player.isHasDoubled()){
            commandString += ", Hit";
        }
        if(player.getCommandCount() == 0 && calculateHandValue(player.getHand()) < 21 && player.getCash() >= player.getBet()){
            commandString += ", Double";
        }
        if(player.getCommandCount() == 0 && player.getHand().getCard(0).getNumber() == player.getHand().getCard(1).getNumber()){
            commandString += ", Split";
        }
        return commandString;
    }

    /**
     * A method for calculating a hands value.
     * The method works by adding all the individual pip-values of each card in the deck.
     * It then takes in consideration the aces (changing their value from 11 to 1) if the hands value is greater than 21.
     * @param hand The hand to calculate for
     * @return  the hands value
     */
    public int calculateHandValue(Deck hand){
        int handValue = 0;
        for (PlayingCard card : hand.getDeck()){
            handValue += calculateCardValue(card);
        }
        hand.calculateAceCount();
        if(handValue > 21 && hand.getAceCount() > 0){
            while(handValue > 21 && hand.getAceCount() > 0){
                hand.reduceAceCount();
                handValue -= 10;
            }
        }
        return handValue;
    }

    /**
     * A helper method used for calculating a cards pip-value.
     * @param card the card to calculate for
     * @return the cards pip-value
     */
    public int calculateCardValue(PlayingCard card){
        int cardValue;
        if (card.getNumber() == 11 || card.getNumber() == 12 || card.getNumber() == 13){
            cardValue = 10;
            return cardValue;
        }
        else if (card.getNumber() == 1){
            cardValue = 11;
            return cardValue;
        }
        else{
            cardValue = card.getNumber();
            return cardValue;
        }
    }

    /**
     * A method that prints a prompt and takes and returns a players input from the terminal.
     * The input in this method is any set of characters that is converted into a string.
     * @return  the players input
     */
    public String getNextString(){
        System.out.print("> ");     // print prompt
        return scanner.next();
    }

    /**
     * A method that prints a prompt and takes and returns a players input from the terminal.
     * The input for this method can only be an integer value, as guaranteed by the do-while loop in this method.
     * @return the players input
     */
    public int getNextInt(){
        boolean inputIsLegal = false;
        int integer = 0;
        do{
            System.out.print("> ");     // print prompt
            if (scanner.hasNextInt()){
                integer = scanner.nextInt();
                inputIsLegal = true;
            }
            else{
                scanner.next();
                System.out.println("Please enter a whole number, try again...");
            }
        }while(!inputIsLegal);

        return integer;
    }

    /**
     * A method for 'hitting' a player i.e drawing one additional card while playing their hand.
     * @param player the player of which to hit.
     */
    public void hit(Player player){
        deckProcessor.drawCards(1,houseDeck,player.getHand());
    }

    /**
     * Returns all of the cards from a players hand back to the house-deck
     * @param player the player of which to return the cards from
     */
    public void returnCards(Player player){
        int nrOfCards = player.getHand().getDeckSize();
            deckProcessor.drawCards(nrOfCards,player.getHand(),houseDeck);
    }

}