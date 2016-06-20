package svin.beerbowl.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AbstraktenPC on 16-06-2016.
 */
public class League {

    private List<Player> participants;
    private List<Player> admins;
    private List<Match> games;
    private String name;

    public League(Player creator, String leagueName){
        name = leagueName;
        admins = new LinkedList<>(admins);
        participants = new LinkedList<>();
        games = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Match> getGames() {
        return games;
    }

    public List<Player> getParticipants() {
        return participants;
    }

    public List<Player> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Player> newAdmins, Player admin) {
        if (admins.contains(admin)) {
            this.admins = newAdmins;
        }
    }

    public void addGame(Match game, Player admin){
        if (admins.contains(admin)){
            games.add(game);
        }
        else{
            throw new UnsupportedOperationException("Not Implemented Yet"); //TODO add rules for non-admin games
        }
    }
}
