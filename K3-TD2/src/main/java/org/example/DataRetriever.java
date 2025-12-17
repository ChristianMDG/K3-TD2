package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    DBConnection dbConnection =  new DBConnection();
    public Team findTeamById(Integer id) throws SQLException {

        Team team = null;
        List<Player> players = new ArrayList<>();

        String query = """
        select team.id as team_id, team.name as team_name,team.continent as continent,player.id as player_id, player.name as player_name,player.age as age,player.position as position  from team
        left join player on team.id = player.id_team
        where team.id = ?;
        """;

        try (Connection connection = dbConnection.getDBConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                if (team == null) {
                    team = new Team();
                    team.setId(rs.getInt("team_id"));
                    team.setName(rs.getString("team_name"));
                    team.setContinent(
                    ContinentEnum.valueOf(rs.getString("continent"))
                    );
                    team.setPlayers(players);
                }
                if (rs.getInt("player_id") != 0) {
                    Player player = new Player();
                    player.setId(rs.getInt("player_id"));
                    player.setName(rs.getString("player_name"));
                    player.setAge(rs.getInt("age"));
                    player.setPosition(PlayerPositionEnum.valueOf(rs.getString("position")));
                    player.setTeam(team);
                    players.add(player);
                }
            }
        }
        return team;
    }

    List<Player> findPlayers(int page, int size) throws SQLException {
        List<Player> players = new ArrayList<>();
        Team team = new Team();
        int offset = (page - 1) * size;
        String query = """
            select player.id as player_id, player.name as player_name, player.age as age, player.position as position, team.name as team from player
            left join team on player.id_team = team.id
            limit ? offset ?
""";
        try (Connection connection = dbConnection.getDBConnection();
        PreparedStatement statement = connection.prepareStatement(query))
            {
            statement.setInt(1, size);
            statement.setInt(2, offset);
            ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    if (rs.getInt("player_id") != 0) {
                        Player player = new Player();
                        player.setId(rs.getInt("player_id"));
                        player.setName(rs.getString("player_name"));
                        player.setAge(rs.getInt("age"));
                        player.setPosition(PlayerPositionEnum.valueOf(rs.getString("position")));
                        team.setName(rs.getString("team"));
                        player.setTeam(team);
                        players.add(player);
                    }
                }

            }
        return players;
    }
    List<Player> createPlayers(List<Player> newPlayers)  throws SQLException {
        throw new RuntimeException();
    }
    Team saveTeam(Team teamToSave) throws SQLException {
        throw new RuntimeException();
    }

    List<Team> findTeamsByPlayerName(String playerName)  throws SQLException {
        throw new RuntimeException();
    }

    List<Player> findPlayersByCriteria(String playerName,
                                       PlayerPositionEnum position, String teamName, ContinentEnum
                                               continent, int page, int size) throws SQLException {
        throw new RuntimeException();

    }
}
