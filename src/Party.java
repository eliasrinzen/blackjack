import java.util.ArrayList;
import java.util.List;

public class Party {
    private List<Player> party = new ArrayList<Player>();
    private Player dealer;

    public Party(){
        dealer = new Player("Dealer");
    }

    public String partyString(){
        String partyString = "";
        for (Player player : party) {
            partyString += player.getName() + " ";
        }
        return partyString;
    }

    public Player getPlayer(String name){
        for (Player player : this.getParty()){
            if(player.getName().equalsIgnoreCase(name)){
                return player;
            }
        }
        return null;
    }

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
