/**
 * This class is a part of the blackjack project.
 * It is a simple, text based blackjack game.
 *
 * This class is the blueprint for a player.
 * A player has a deck of cards, a name, money & can make bets.
 *
 */
public class Player{
    // Each player has a "hand" i.e a deck object which is empty when first initialized.
    private Deck hand;
    private String name;
    private int cash;
    private int bet;
    private int commandCount;
    private boolean hasDoubled;

    public Player(String name){
        hand = new Deck();
        this.name = name;
        cash = 100;
    }

    /**
     *  Method for placing bets.
     *  This method simply decreases your cash by the same amount as the bet.
     *
     * @param howMuch integer value for how much money the player wants to bet
     */
    public void placeBet(int howMuch){
        if (howMuch <= cash){
            bet = howMuch;
            decreaseCash(bet);
        }
    }

    // Trivial helper methods and setters & getters.
    public void decreaseCash(int howMuch){ cash -= howMuch; }
    public void increaseCash(int howMuch){ cash += howMuch; }
    public int getBet() { return bet; }
    public int getCash() { return cash; }
    public Deck getHand() { return hand; }
    public String getName() { return name; }
    public void increaseCommandCount(){ commandCount++; }
    public void decreaseCommandCount(){ commandCount--; }
    public void resetCommandCount(){ commandCount = 0; }
    public int getCommandCount(){ return commandCount; }
    public void increaseBet(int howMuch){ bet += howMuch; }
    public boolean isHasDoubled() { return hasDoubled; }
    public void resetDoubled(){ hasDoubled = false; }
    public void doDouble(){ hasDoubled = true; }
}