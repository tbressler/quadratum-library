package de.tbressler.quadratum.logic.players;

import de.tbressler.quadratum.logic.ILogicCallback;
import de.tbressler.quadratum.model.IReadOnlyGameBoard;
import de.tbressler.quadratum.model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for class AbstractPlayerLogic.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestAbstractPlayerLogic {

    // Class under test:
    private AbstractPlayerLogic abstractPlayerLogic;


    // Mocks:
    private Player player = mock(Player.class, "player");



    @Before
    public void setUp() {
        abstractPlayerLogic = new AbstractPlayerLogic(player) {
            @Override
            public void requestMove(IReadOnlyGameBoard gameBoard, ILogicCallback callback) {
                // Not needed in this test.
            }
        };
    }


    /**
     * Checks if an exception is thrown if the player is null.
     */
    @Test(expected = NullPointerException.class)
    public void new_withNullPlayer_throwsException() {
        new AbstractPlayerLogic(null) {
            @Override
            public void requestMove(IReadOnlyGameBoard gameBoard, ILogicCallback callback) {
                // Not needed in this test.
            }
        };
    }


    /**
     * Checks if getPlayer() returns the player from construction.
     */
    @Test
    public void getPlayer_returnsPlayer() {
        assertEquals(player, abstractPlayerLogic.getPlayer());
    }

}
