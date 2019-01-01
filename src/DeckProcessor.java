import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is a part of the blackjack project.
 * It is a simple, text based blackjack game.
 *
 * The deckprocessor class is used to manage cards and decks.
 */
public class DeckProcessor {
    private Random ran = new Random();

    public DeckProcessor(){

    }

    /**
     * Sort an unsorted deck of cards by number.
     * If your argument is a standard deck it will be sorted into: Aces, dueces, threes, fours etc all the way up to kings.
     * @param deck  the deck to be shuffled.
     */
    public void sortDeckByNumber(Deck deck){
        List<PlayingCard> deckList = deck.getDeck();
        List<PlayingCard> aces = new ArrayList<PlayingCard>();
        List<PlayingCard> twos = new ArrayList<PlayingCard>();
        List<PlayingCard> threes = new ArrayList<PlayingCard>();
        List<PlayingCard> fours = new ArrayList<PlayingCard>();
        List<PlayingCard> fives = new ArrayList<PlayingCard>();
        List<PlayingCard> sixes = new ArrayList<PlayingCard>();
        List<PlayingCard> sevens = new ArrayList<PlayingCard>();
        List<PlayingCard> eights = new ArrayList<PlayingCard>();
        List<PlayingCard> nines = new ArrayList<PlayingCard>();
        List<PlayingCard> tens = new ArrayList<PlayingCard>();
        List<PlayingCard> jacks = new ArrayList<PlayingCard>();
        List<PlayingCard> queens = new ArrayList<PlayingCard>();
        List<PlayingCard> kings = new ArrayList<PlayingCard>();

        for(PlayingCard card : deckList){
            switch(card.getNumber()){
                case 1:
                    aces.add(card);
                    break;
                case 2:
                    twos.add(card);
                    break;
                case 3:
                    threes.add(card);
                    break;
                case 4:
                    fours.add(card);
                    break;
                case 5:
                    fives.add(card);
                    break;
                case 6:
                    sixes.add(card);
                    break;
                case 7:
                    sevens.add(card);
                    break;
                case 8:
                    eights.add(card);
                    break;
                case 9:
                    nines.add(card);
                    break;
                case 10:
                    tens.add(card);
                    break;
                case 11:
                    jacks.add(card);
                    break;
                case 12:
                    queens.add(card);
                    break;
                case 13:
                    kings.add(card);
                    break;
            }
        }
        deckList.clear();
        deckList.addAll(aces);
        deckList.addAll(twos);
        deckList.addAll(threes);
        deckList.addAll(fours);
        deckList.addAll(fives);
        deckList.addAll(sixes);
        deckList.addAll(sevens);
        deckList.addAll(eights);
        deckList.addAll(nines);
        deckList.addAll(tens);
        deckList.addAll(jacks);
        deckList.addAll(queens);
        deckList.addAll(kings);
    }

    /**
     * A method for sorting a deck of cards by suit.
     * If your argument is a standard deck of cards the result will be each suit with each corresponding card from Ace to King.
     * @param deck The deck you want to be sorted.
     */
    public void sortDeckBySuit(Deck deck){
        List<PlayingCard> deckList = deck.getDeck();
        List<PlayingCard> heartSection = new ArrayList<PlayingCard>();
        List<PlayingCard> diamondSection = new ArrayList<PlayingCard>();
        List<PlayingCard> clubSection = new ArrayList<PlayingCard>();
        List<PlayingCard> spadeSection = new ArrayList<PlayingCard>();

        for(PlayingCard card : deckList) {
            switch(card.getSuit()){
                case Heart:
                    heartSection.add(card);
                    break;
                case Diamond:
                    diamondSection.add(card);
                    break;
                case Club:
                    clubSection.add(card);
                    break;
                case Spade:
                    spadeSection.add(card);
                    break;
            }

        }
        sortSectionByNumer(heartSection);
        sortSectionByNumer(diamondSection);
        sortSectionByNumer(clubSection);
        sortSectionByNumer(spadeSection);

        deckList.clear();
        deckList.addAll(heartSection);
        deckList.addAll(spadeSection);
        deckList.addAll(diamondSection);
        deckList.addAll(clubSection);
    }

    /**
     * Helper method to sort a section of cards by their numerical value.
     * @param sectionList
     */
    public void sortSectionByNumer(List<PlayingCard> sectionList){
            for (int i = 1; i < sectionList.size(); i++) {
                int j = i;
                while (j > 0 && (sectionList.get(j - 1).getNumber() > (sectionList.get(j).getNumber()))){
                    PlayingCard temp = sectionList.get(j);
                    sectionList.set(j, sectionList.get(j - 1));
                    sectionList.set(j - 1, temp);
                    j--;
                }
            }
    }

    /**
     * A method used for randomly shuffling a deck of cards.
     * @param deck the deck you want to shuffle.
     */
    public void shuffleDeck(Deck deck){
    int deckSize = deck.getDeckSize();
    List<PlayingCard> deckClone = new ArrayList<PlayingCard>(deck.getDeck());
    deck.getDeck().clear();
        for(int i = 0; i < deckSize ; i++){
            int index = ran.nextInt(deckClone.size());
            PlayingCard card = deckClone.get(index);
            deck.addCard(card);
            deckClone.remove(card);
        }
    }

    /**
     * A method used to take any number of cards from a certain deck and place it into a different deck.
     * @param howMany The number of cards you wish to draw
     * @param deckFrom  The giving deck
     * @param deckTo    The receiving deck
     */
    public void drawCards(int howMany, Deck deckFrom, Deck deckTo){
        if(deckFrom.getDeckSize() >= howMany) {
            List<PlayingCard> cardsToDraw = new ArrayList<>();
            for (int i = 0; i < howMany; i++) {
                cardsToDraw.add(deckFrom.getCard(0));
                deckFrom.removeCard(0); // Since we are drawing from the top we always want index 0, the card at the top of the deck.
            }
            for (PlayingCard card : cardsToDraw) {
                deckTo.addCard(card);           // Adding the cards to the receiving deck.
            }
        }
        else{
            System.out.println("\n" + "You tried to draw more cards than there are in the deck");
        }
    }
}