public class Player{
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


    public String handToString(){
        String returnString = name + " you have: " +  hand.deckToStringSingleRow() + "\n";
        return returnString;
    }

    public void placeBet(int howMuch){
        if (howMuch <= cash){
            bet = howMuch;
            decreaseCash(bet);
        }
    }

    public void decreaseCash(int howMuch){
        cash -= howMuch;
    }
    public void increaseCash(int howMuch){
        cash += howMuch;
    }
    public int getBet() {
        return bet;
    }
    public int getCash() {
        return cash;
    }
    public Deck getHand() {
        return hand;
    }
    public String getName() {
        return name;
    }
    public void increaseCommandCount(){
        commandCount++;
    }
    public void decreaseCommandCount(){
        commandCount--;
    }
    public void resetCommandCount(){
        commandCount = 0;
    }
    public int getCommandCount(){
        return commandCount;
    }
    public void increaseBet(int howMuch){
        bet += howMuch;
    }
    public boolean isHasDoubled() {
        return hasDoubled;
    }
    public void resetDoubled(){
        hasDoubled = false;
    }
    public void doDouble(){
        hasDoubled = true;
    }

}