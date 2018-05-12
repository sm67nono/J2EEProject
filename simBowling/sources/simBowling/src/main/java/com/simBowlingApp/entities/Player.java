package com.simBowlingApp.entities;

import org.springframework.stereotype.Component;

@Component
public class Player {
    private int id;
    private String name;

    public Player() {
        this.id = 1;
        this.name = "Player One";
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
}
