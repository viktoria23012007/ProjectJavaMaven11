package ru.netology.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.domain.Player;
import ru.netology.exception.AlreadyRegisteredException;
import ru.netology.exception.NotRegisteredException;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Game {
    private HashMap<String, Player> players = new HashMap<>();

    public HashMap<String, Player> getAll() {
        return players;
    }

    public Player findBy(String name) {
        return players.get(name);
    }

    public Player findBy(int id) {
        for (Player player : players.values()) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public void register(Player player) {
        String playerName = player.getName();
        if (players.containsKey(playerName.toLowerCase())) {
            throw new AlreadyRegisteredException("Player «" + player.getName() + "» is already registered");
        }
        if (this.findBy(player.getId()) != null) {
            throw new AlreadyRegisteredException("Player with ID " + player.getId() + " is already registered");
        }
        players.put(playerName, player);
    }

    public int round(String playerName1, String playerName2) {
        Player player1 = this.findBy(playerName1);
        Player player2 = this.findBy(playerName2);
        if (player1 == null && player2 == null) {
            throw new NotRegisteredException("Players «" + playerName1 + "» and «" + playerName2 + "» are not registered");
        } else if (player1 == null) {
            throw new NotRegisteredException("Player «" + playerName1 + "» is not registered");
        } else if (player2 == null) {
            throw new NotRegisteredException("Player «" + playerName2 + "» is not registered");
        }
        int result = player1.compareTo(player2);
        if (result < 0) {
            result = 2;
        }
        return result;
    }
}