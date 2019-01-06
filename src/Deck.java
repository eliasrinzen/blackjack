
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a part of the blackjack project.
 * It is a simple, text based blackjack game.
 *
 * This class is designed to create and manage collections of playingcards. i.e a deck of cards.
 * A deck of cards can contain any number of playingcards.
 * Different methods exist for initializing different kind of decks.
 */
public class Deck {
    // The collection used for storing the "deck" of cards.
    private List<PlayingCard> deck = new ArrayList<PlayingCard>();

    // A counter for "usable" aces  i.e soft aces in the deck. Used for calculating the hands value in blackjack.
    private int aceCount;

    public Deck() {
    }

    /**
     * Method that initializes a "standard" deck of 52 playing cards. Ace to King in each of the four suits.
     */
    public void initializeOG(){
        Suit heart = Suit.Heart;
        Suit diamond = Suit.Diamond;
        Suit club = Suit.Club;
        Suit spade = Suit.Spade;

        for (int i = 1; i < 14; i++) {
            PlayingCard card1 = new PlayingCard(i, heart);
            deck.add(card1);

            PlayingCard card2 = new PlayingCard(i, diamond);
            deck.add(card2);

            PlayingCard card3 = new PlayingCard(i, club);
            deck.add(card3);

            PlayingCard card4 = new PlayingCard(i, spade);
            deck.add(card4);

        }
    }

    /**
     * Method that initializes a deck used for testing.
     */
    public void createTestDeck(){
        Suit heart = Suit.Heart;
        Suit diamond = Suit.Diamond;
        Suit club = Suit.Club;
        Suit spade = Suit.Spade;

        for (int i = 1; i < 3; i++) {
            PlayingCard card1 = new PlayingCard(i, heart);
            deck.add(card1);

            PlayingCard card2 = new PlayingCard(i, diamond);
            deck.add(card2);

            PlayingCard card3 = new PlayingCard(i, club);
            deck.add(card3);

            PlayingCard card4 = new PlayingCard(i, spade);
            deck.add(card4);

        }

    }

    /**
     * Method used for generating a string of the whole deck in row form.
     * @return String containing all the cards in row form
     */
    public String deckToStringRows(){
        String deckString = "";
        for (int i = 0; i < this.getDeckSize() ; i++) {
            deckString = deckString + deck.get(i).cardToString() + "\n";
        }
        return deckString;
    }

    /**
     * Method used for generating a string of the whole deck in a single row.
     * @return String containing all the cards in a single row
     */
    public String deckToStringSingleRow(){
        String deckString = deck.get(0).cardToString(); // so the string does not start with "+".
        for (int i = 1; i < this.getDeckSize(); i++){
            deckString = deckString + " + " + deck.get(i).cardToString();
        }
        return deckString;
    }

    /**
     * Method to update the aceCount field.
     */
    public void calculateAceCount(){
        resetAceCount();
        int acesToAdd = 0;
        for (PlayingCard card : this.getDeck()) {
            if(card.getNumber() == 1){
                acesToAdd ++;
            }
        }
        aceCount += acesToAdd;
    }

    // Trivial helper methods and setters & getters.
    public List<PlayingCard> getDeck() {
        return deck;
    }

    public PlayingCard getCard(int index){
        return deck.get(index);
    }

    public int getDeckSize(){
        return deck.size();
    }

    public void removeCard(int index){
        deck.remove(index);
    }

    public void addCard(PlayingCard card){
        deck.add(0, card); // Adding the card to the top of the deck.
    }

    public int getAceCount(){
        return aceCount;
    }

    public void reduceAceCount(){
        aceCount--;
    }
    public void resetAceCount(){
        aceCount = 0;
    }

}