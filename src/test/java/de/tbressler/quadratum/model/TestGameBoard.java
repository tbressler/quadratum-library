package de.tbressler.quadratum.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for class GameBoard.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestGameBoard {

    // Class under test:
    private GameBoard gameBoard;

    // Mocks:
    private Player player1;
    private Player player2;
    private IGameBoardListener listener;


    @Before
    public void setUp() {
        player1 = mock(Player.class, "player1");
        player2 = mock(Player.class, "player2");

        listener = mock(IGameBoardListener.class, "listener");

        gameBoard = new GameBoard(player1, player2);
        gameBoard.addGameBoardListener(listener);
    }


    /**
     * Checks if an exception is thrown if player one is null.
     */
    @Test(expected = NullPointerException.class)
    public void new_withNullPlayerOne_throwsException() {
        new GameBoard(null, player2);
    }

    /**
     * Checks if an exception is thrown if player two is null.
     */
    @Test(expected = NullPointerException.class)
    public void new_withNullPlayerTwo_throwsException() {
        new GameBoard(player1, null);
    }

    /**
     * Checks if an exception is thrown if player one and two are equal.
     */
    @Test(expected = AssertionError.class)
    public void new_withEqualPlayers_throwsException() {
        new GameBoard(player1, player1);
    }


    /**
     * getPlayer1() must always return player one.
     */
    @Test
    public void getPlayer1_returnsPlayerOne() {
        assertEquals(player1, gameBoard.getPlayer1());
    }


    /**
     * getPlayer2() must always return player two.
     */
    @Test
    public void getPlayer2_returnsPlayerTwo() {
        assertEquals(player2, gameBoard.getPlayer2());
    }


    /**
     * Checks if an exception is thrown if the player is null.
     */
    @Test(expected = NullPointerException.class)
    public void placePiece_withNullPlayer_throwsException() {
        gameBoard.placePiece(0, null);
    }

    /**
     * Checks if an exception is thrown if the given player is not known by the game board.
     */
    @Test(expected = AssertionError.class)
    public void placePiece_withUnknownPlayer_throwsException() {
        gameBoard.placePiece(0, mock(Player.class, "unknown-player"));
    }

    /**
     * Checks if an exception is thrown if the given field index is lower than 0. Only an index
     * between 0 and 63 is allowed.
     */
    @Test(expected = AssertionError.class)
    public void placePiece_withLowerThan0_throwsException() {
        gameBoard.placePiece(-1, player1);
    }

    /**
     * Checks if an exception is thrown if the given field index is greater than 63. Only an index
     * between 0 and 63 is allowed.
     */
    @Test(expected = AssertionError.class)
    public void placePiece_withGreaterThan63_throwsException() {
        gameBoard.placePiece(64, player1);
    }

    /**
     * Checks if an exception is thrown if the given field index is not empty.
     */
    @Test(expected = AssertionError.class)
    public void placePiece_ifFieldIsNotEmpty_throwsException() {
        gameBoard.placePiece(1, player1);
        gameBoard.placePiece(1, player2);
    }

    /**
     * Checks if placePiece() notifies all game board listeners about the placed piece.
     */
    @Test
    public void placePiece_notifiesListeners_onPiecePlaced() {
        gameBoard.placePiece(10, player1);
        verify(listener, times(1)).onPiecePlaced(10, player1);
    }


    /**
     * Checks if all fields are empty after game board was initialized.
     */
    @Test
    public void isFieldEmpty_returnsTrue_afterNewForAllFields() {
        for(int i = 0; i < 64; i++)
            assertEquals(true, gameBoard.isFieldEmpty(i));
    }

    /**
     * Checks if isFieldEmpty() returns false if a piece was placed on that field before.
     */
    @Test
    public void isFieldEmpty_returnsFalse_afterPlacePieceOnIndex13() {
        gameBoard.placePiece(13, player1);
        assertEquals(false, gameBoard.isFieldEmpty(13));
    }

    /**
     * Checks if an exception is thrown if the given field index is lower than 0. Only an index
     * between 0 and 63 is allowed.
     */
    @Test(expected = AssertionError.class)
    public void isFieldEmpty_withLowerThan0_throwsException() {
        gameBoard.isFieldEmpty(-1);
    }

    /**
     * Checks if an exception is thrown if the given field index is greater than 63. Only an index
     * between 0 and 63 is allowed.
     */
    @Test(expected = AssertionError.class)
    public void isFieldEmpty_withGreaterThan63_throwsException() {
        gameBoard.isFieldEmpty(64);
    }


    /**
     * Checks if an exception is thrown if the given field index is lower than 0. Only an index
     * between 0 and 63 is allowed.
     */
    @Test(expected = AssertionError.class)
    public void getPiece_withLowerThan0_throwsException() {
        gameBoard.getPiece(-1);
    }

    /**
     * Checks if an exception is thrown if the given field index is greater than 63. Only an index
     * between 0 and 63 is allowed.
     */
    @Test(expected = AssertionError.class)
    public void getPiece_withGreaterThan63_throwsException() {
        gameBoard.getPiece(64);
    }

    /**
     * Checks if getPiece() returns player one if this player placed the piece before.
     */
    @Test
    public void getPiece_returnsPlayer1_ifPlayer1PlacedPieceBefore() {
        gameBoard.placePiece(40, player1);
        assertEquals(player1, gameBoard.getPiece(40));
    }

    /**
     * Checks if getPiece() returns player two if this player placed the piece before.
     */
    @Test
    public void getPiece_returnsPlayer2_ifPlayer2PlacedPieceBefore() {
        gameBoard.placePiece(40, player1);
        gameBoard.placePiece(20, player2);

        assertEquals(player2, gameBoard.getPiece(20));
    }

    /**
     * Checks if getPiece() returns null if the field is empty.
     */
    @Test
    public void getPiece_returnsNull_ifFieldIsEmpty() {
        assertEquals(null, gameBoard.getPiece(30));
    }


    /**
     * Checks if clear() clears the game board.
     */
    @Test
    public void clear_clearsGameBoard() {
        gameBoard.placePiece(10, player1);
        gameBoard.placePiece(15, player2);
        gameBoard.clear();
        assertEquals(null, gameBoard.getPiece(10));
        assertEquals(null, gameBoard.getPiece(15));
    }

    /**
     * Checks if clear() notifies listeners, that game board was cleared.
     */
    @Test
    public void clear_notifiesListenersOnGameBoardCleared() {
        gameBoard.clear();
        verify(listener, times(1)).onGameBoardCleared();
    }


    /**
     * Checks if an exception is thrown the given listener is null.
     */
    @Test(expected = NullPointerException.class)
    public void addGameBoardListener_withNull_throwsException() {
        gameBoard.addGameBoardListener(null);
    }

    /**
     * Checks if an exception is thrown the given listener is null.
     */
    @Test(expected = NullPointerException.class)
    public void removeGameBoardListener_withNull_throwsException() {
        gameBoard.removeGameBoardListener(null);
    }

}
