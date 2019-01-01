/**
 *  This class is the main class of the blackjack project.
 *  It is a simple, text based blackjack game.
 *
 *  To play the game compile and run this class.
 *  This main class creates and initialises all the other classes; it creates all
 *  cards, the party consisting of all players, the dealer, the house and then starts the game.
 *  The game ends when all players run out of money.
 *
 *   @author Elias Rinzén
 *   @version 2019-01-01
 */
public class Main {
    public static void main(String[] args) {
        BlackJackEngine engine = new BlackJackEngine();
        engine.play();
    }
}
