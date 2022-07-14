package ru.netology.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player player = new Player(1, "Name Surname", 5);

    @ParameterizedTest
    @CsvSource({
            "Lower Strength, 4, 1",
            "Higher Strength, 6, -1",
            "Equal Strength, 5, 0"
    })
    public void shouldCompareTo(String testName, int strength, int expected) {
        Player testPlayer = new Player(0, "Test Player", strength);
        int actual = player.compareTo(testPlayer);
        assertEquals(actual, expected);
    }
}