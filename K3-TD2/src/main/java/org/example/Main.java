package org.example;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {

        DataRetriever dataRetriever = new DataRetriever();
        System.out.println(dataRetriever.findTeamById(1));
        System.out.println(dataRetriever.findPlayers(1,2));
    }
}