package org.example;

import java.util.List;

public class Team {
    private  int id;
    private String name;
    private  Continent continentEnum;
    private List<Player> players;
    public Team(int id, String name, Continent continentEnum, List<Player> players) {
        this.id = id;
        this.name = name;
        this.continentEnum = continentEnum;
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Continent getContinentEnum() {
        return continentEnum;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
