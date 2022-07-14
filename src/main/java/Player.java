package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Player implements Comparable<Player> {
    private int id;
    private String name;
    private int strength;

    @Override
    public int compareTo(Player p) {
        return strength - p.strength;
    }
}