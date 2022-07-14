package ru.netology.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.domain.Player;
import ru.netology.exception.AlreadyRegisteredException;
import ru.netology.exception.NotRegisteredException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private final Player player1 = new Player(1, "Player 1", 1);
    private final Player player2 = new Player(2, "Player 2", 2);
    private final Player player3 = new Player(3, "Player 3", 3);
    private final Player newPlayer = new Player(4, "Player 4", 2);
    private final Player newPlayerWithSameId = new Player(1, "Player 5", 1);
    private final Map<String, Player> map = Map.of(player1.getName(), player1, player2.getName(), player2, player3.getName(), player3);
    private final HashMap<String, Player> players = new HashMap<>(map);
    private final Game game = new Game(players);

    @Test
    public void getAll() {
        assertTrue(game.getAll().equals(players));
    }

    @Test
    public void shouldFindByName() {
        Player expected = player1;
        Player actual = game.findBy("Player 1");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldNotFindByName() {
        assertNull(game.findBy("Player"));
    }

    @Test
    public void shouldFindById() {
        Player expected = player1;
        Player actual = game.findBy(1);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldNotFindById() {
        assertNull(game.findBy(5));
    }

    @Test
    public void shouldRegister() {
        game.register(newPlayer);
        assertTrue(game.getAll().containsValue(newPlayer));
    }

    @Test
    public void shouldTrowAlreadyRegisteredException() {
        Assertions.assertThrows(AlreadyRegisteredException.class, () -> game.register(player1));
    }

    @Test
    public void shouldTrowAlreadyRegisteredExceptionWithSameId() {
        Assertions.assertThrows(AlreadyRegisteredException.class, () -> game.register(newPlayerWithSameId));
    }

    @Test
    public void shouldTrowNotRegisteredExceptionIfFirstPlayerIsNotRegistered() {
        game.getAll().remove("Player 1");
        Assertions.assertThrows(NotRegisteredException.class, () -> game.round("Player 1", "Player 2"));
    }

    @Test
    public void shouldTrowNotRegisteredExceptionIfSecondPlayerIsNotRegistered() {
        game.getAll().remove("Player 2");
        Assertions.assertThrows(NotRegisteredException.class, () -> game.round("Player 1", "Player 2"));
    }

    @Test
    public void shouldTrowNotRegisteredExceptionIfBothPlayersAreNotRegistered() {
        game.getAll().remove("Player 1");
        game.getAll().remove("Player 2");
        Assertions.assertThrows(NotRegisteredException.class, () -> game.round("Player 1", "Player 2"));
    }

    @ParameterizedTest
    @CsvSource({
            "First Player Wins, Player 2, Player 1, 1",
            "Second Player Wins, Player 2, Player 3, 2",
            "Draw, Player 2, Player 4, 0",
    })
    public void shouldRound(String testName, String playerName1, String playerName2, int expected) {
        game.register(newPlayer);
        int actual = game.round(playerName1, playerName2);
        assertEquals(expected, actual);
    }
}