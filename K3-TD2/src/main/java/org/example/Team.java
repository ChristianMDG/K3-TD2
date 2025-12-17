package org.example;

import java.util.List;

public class Team {
    private  int id;
    private String name;
    private ContinentEnum continent;
    private List<Player> players;
    public Team() {}
    public Team(int id, String name, ContinentEnum continentEnum, List<Player> players) {
        this.id = id;
        this.name = name;
        this.continent = continentEnum;
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContinentEnum getContinent() {
        return continent;
    }

    public void setContinent(ContinentEnum continent) {
        this.continent = continent;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Integer getPlayersCount() {
        return players.size();
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", continent=" + continent +
                ", players=" + players +
                '}';
    }
}
