/**
 * This class is a part of the blackjack project.
 * It is a simple, text based blackjack game.
 *
 * This class provides the blueprint for standard playingcards.
 * A card has a suit: (Heart, Spade, Diamond, Club)
 * and a numerical value: (Ace, two, three [...] Jack, Queen, King)
 * Note that an Ace is considered to have a value of 1 and King to have a value of 13.
 */
public class PlayingCard {

    private int number;
    private Suit suit;

    /**
     * A cards "number"/value and suit is decided when it is initialized.
     * @param number    The cards number
     * @param suit      The cards suit
     */
    public PlayingCard(int number, Suit suit){
        this.number = number;
        this.suit = suit;
    }

    /**
     * A helper method to create a string of the cards suit and value.
     * e.g "Ace of Heats", "King of Spades" etc...
     * @return String containing the cards suit and value.
     */
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

    // Getter methods.

    public int getNumber() {
        return number;
    }

    public Suit getSuit() {
        return suit;
    }

}
