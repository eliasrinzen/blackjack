public class PlayingCard {

    private int number;
    private Suit suit;

    public PlayingCard(int number, Suit suit){
        this.number = number;
        this.suit = suit;
    }

    public String cardToString(){
        String cardNumber = "";
        switch(getNumber()){

            case 1:
                cardNumber = "Ace";
                break;
            case 11:
                cardNumber = "Jack";
                break;
            case 12:
                cardNumber = "Queen";
                break;
            case 13:
                cardNumber = "King";
                break;
            default:
                cardNumber += number;
        }
        String cardString = cardNumber +" of " + suit.toString() + "s";
        return cardString;
    }

    public int getNumber() {
        return number;
    }

    public Suit getSuit() {
        return suit;
    }

}
