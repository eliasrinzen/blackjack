import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<PlayingCard> deck = new ArrayList<PlayingCard>();
    private int aceCount;

    public Deck() {
    }

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

    public String deckToStringRows(){
        String deckString = "";
        for (int i = 0; i < this.getDeckSize() ; i++) {
            deckString = deckString + deck.get(i).cardToString() + "\n";
        }
        return deckString;
    }

    public String deckToStringSingleRow(){
        String deckString = deck.get(0).cardToString();
        for (int i = 1; i < this.getDeckSize(); i++){
            deckString = deckString + " + " + deck.get(i).cardToString();
        }
        return deckString;
    }

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