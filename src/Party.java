
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a part of the blackjack project.
 * It is a simple, text based blackjack game.
 *
 * This class creates parties of players. It also creates a special player i.e a dealer for every party.
 * Parties are used for creating and organizing a collection of players.
 */
public class Party {
    // The collection used for storing the "deck" of cards.
    private List<Player> party = new ArrayList<Player>();
    // The dealer used in blackjack.
    private Player dealer;

    public Party(){
        dealer = new Player("Dealer");
    }

    /**
     * Method used for generating a string with the names of all players in the party.
     * @return the String containing the names of the players in the party
     */
    public String partyString(){
        String partyString = "";
        for (Player player : party) {
            partyString += player.getName() + " ";
        }
        return partyString;
    }


    // Trivial helper methods
    public void addPlayer(Player player){
        party.add(player);
    }

    public void removePlayer(Player player){
        party.remove(player);
    }

    public List<Player> getParty(){
        return party;
    }

    public Player getDealer(){
        return dealer;
    }

}
