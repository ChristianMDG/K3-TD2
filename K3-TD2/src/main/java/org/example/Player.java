package org.example;

public class Player {
private  int id;
private String name;
private int age;
private Position positionEnum;
private Team team;

    public Player(int id, String name, int age, Position positionEnum, Team team) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.positionEnum = positionEnum;
        this.team = team;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Position getPositionEnum() {
        return positionEnum;
    }

    public Team getTeam() {
        return team;
    }
}
