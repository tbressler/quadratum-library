package de.tbressler.quadratum.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for class Player.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestPlayer {

    // Class under test:
    private Player player;


    @Before
    public void setUp() {
        player = new Player("name");
    }


    @Test(expected = NullPointerException.class)
    public void new_withNullName_throwsException() {
        new Player(null);
    }


    @Test
    public void getName_returnsName() {
        assertEquals("name", player.getName());
    }

}
