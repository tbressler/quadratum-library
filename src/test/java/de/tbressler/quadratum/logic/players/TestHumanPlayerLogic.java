package de.tbressler.quadratum.logic.players;

import de.tbressler.quadratum.logic.ILogicCallback;
import de.tbressler.quadratum.model.IReadOnlyGameBoard;
import de.tbressler.quadratum.model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Tests for class HumanPlayerLogic.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestHumanPlayerLogic {

    // Class under test:
    private HumanPlayerLogic humanPlayerLogic;


    // Mocks:
    private Player player = mock(Player.class, "player");

    private ILogicCallback logicCallback = mock(ILogicCallback.class, "logicCallback");

    private IReadOnlyGameBoard gameBoard = mock(IReadOnlyGameBoard.class, "gameBoard");



    @Before
    public void setUp() {
        humanPlayerLogic = new HumanPlayerLogic(player);
    }


    /**
     * Checks if an exception is thrown if the player is null.
     */
    @Test(expected = NullPointerException.class)
    public void new_withNullPlayer_throwsException() {
        new HumanPlayerLogic(null);
    }


    /**
     * Checks if isPlayerActive() returns false after construction.
     */
    @Test
    public void isPlayerActive_afterNew_returnsFalse() {
        assertFalse(humanPlayerLogic.isPlayerActive());
    }


    /**
     * Checks if isPlayerActive() returns true after a move was requested at the player logic.
     */
    @Test
    public void isPlayerActive_afterRequestMove_returnsTrue() {
        humanPlayerLogic.requestMove(gameBoard, logicCallback);
        assertTrue(humanPlayerLogic.isPlayerActive());
    }

    @Test
    public void isPlayerActive_afterRequestMoveAndPlacePiece_returnsFalse() {
        humanPlayerLogic.requestMove(gameBoard, logicCallback);
        humanPlayerLogic.placePiece(1);
        assertFalse(humanPlayerLogic.isPlayerActive());
    }

    @Test
    public void placePiece_whenPlayerNotActive_returnsFalse() {
        assertFalse(humanPlayerLogic.placePiece(1));
        verify(logicCallback, never()).makeMove(1, player);
    }

    @Test
    public void placePiece_whenPlayerIsActiveAndCallbackReturnsTrue_returnsTrue() {
        humanPlayerLogic.requestMove(gameBoard, logicCallback);
        when(logicCallback.makeMove(1, player)).thenReturn(true);

        assertTrue(humanPlayerLogic.placePiece(1));
        verify(logicCallback, times(1)).makeMove(1, player);
    }

    @Test
    public void placePiece_whenPlayerIsActiveAndCallbackReturnsFalse_returnsFalse() {
        humanPlayerLogic.requestMove(gameBoard, logicCallback);
        when(logicCallback.makeMove(1, player)).thenReturn(false);

        assertFalse(humanPlayerLogic.placePiece(1));
        verify(logicCallback, times(1)).makeMove(1, player);
    }

    @Test(expected = AssertionError.class)
    public void placePiece_withIndexLowerThan0_throwsException() {
        humanPlayerLogic.requestMove(gameBoard, logicCallback);
        humanPlayerLogic.placePiece(-1);
    }


    @Test(expected = AssertionError.class)
    public void placePiece_withIndexGreaterThan63_throwsException() {
        humanPlayerLogic.requestMove(gameBoard, logicCallback);
        humanPlayerLogic.placePiece(64);
    }


    @Test(expected = NullPointerException.class)
    public void requestMove_withNullLogicCallback_throwsException() {
        humanPlayerLogic.requestMove(gameBoard, null);
    }

}
