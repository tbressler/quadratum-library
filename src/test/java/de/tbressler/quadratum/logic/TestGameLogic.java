package de.tbressler.quadratum.logic;

import de.tbressler.quadratum.model.GameBoard;
import de.tbressler.quadratum.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static de.tbressler.quadratum.logic.GameOverVerifier.GameOverState.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

/**
 * Tests for class GameLogic.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestGameLogic {

    // Class under test:
    private GameLogic gameLogic;


    // Mocks:
    private Player player1 = mock(Player.class, "player1");
    private Player player2 = mock(Player.class, "player2");

    private IPlayerLogic playerLogic1 = mock(IPlayerLogic.class, "playerLogic1");
    private IPlayerLogic playerLogic2 = mock(IPlayerLogic.class, "playerLogic2");

    private GameBoard gameBoard = mock(GameBoard.class, "gameBoard");

    private IGameLogicListener listener = mock(IGameLogicListener.class, "listener");

    private SquareCollector squareCollector = mock(SquareCollector.class, "squareCollector");

    private GameOverVerifier gameOverVerifier = mock(GameOverVerifier.class, "gameOverVerifier");


    // Capture:
    private ArgumentCaptor<ILogicCallback> callback = forClass(ILogicCallback.class);



    @Before
    public void setUp() {
        when(gameBoard.getPlayer1()).thenReturn(player1);
        when(gameBoard.getPlayer2()).thenReturn(player2);

        when(playerLogic1.getPlayer()).thenReturn(player1);
        when(playerLogic2.getPlayer()).thenReturn(player2);

        when(gameOverVerifier.isGameOver(gameBoard, squareCollector)).thenReturn(NOT_OVER);

        gameLogic = new GameLogic(gameBoard, playerLogic1, playerLogic2);
        gameLogic.setSquareCollector(squareCollector);
        gameLogic.setGameOverVerifier(gameOverVerifier);
        gameLogic.addGameLogicListener(listener);
    }


    /**
     * Checks if an exception is thrown if the game board is null.
     */
    @Test(expected = NullPointerException.class)
    public void new_withNullGameBoard_throwsException() {
        when(playerLogic1.getPlayer()).thenReturn(player1);
        when(playerLogic2.getPlayer()).thenReturn(player2);
        new GameLogic(null, playerLogic1, playerLogic2);
    }

    /**
     * Checks if an exception is thrown if player logic 1 is null.
     */
    @Test(expected = NullPointerException.class)
    public void new_withNullPlayerLogic1_throwsException() {
        new GameLogic(gameBoard, null, playerLogic2);
    }

    /**
     * Checks if an exception is thrown if player logic 2 is null.
     */
    @Test(expected = NullPointerException.class)
    public void new_withNullPlayerLogic2_throwsException() {
        new GameLogic(gameBoard, playerLogic1, null);
    }

    /**
     * Checks if an exception is thrown if player logic 1 and 2 share the same player.
     */
    @Test(expected = AssertionError.class)
    public void new_withPlayerLogicHasSamePlayer_throwsException() {
        when(playerLogic1.getPlayer()).thenReturn(player1);
        when(playerLogic2.getPlayer()).thenReturn(player1);
        new GameLogic(gameBoard, playerLogic1, playerLogic2);
    }

    /**
     * Checks if an exception is thrown if player logic 1 returns null as player.
     */
    @Test(expected = NullPointerException.class)
    public void new_withPlayerLogic1HasNullPlayer_throwsException() {
        when(playerLogic1.getPlayer()).thenReturn(null);
        when(playerLogic2.getPlayer()).thenReturn(player2);
        new GameLogic(gameBoard, playerLogic1, playerLogic2);
    }

    /**
     * Checks if an exception is thrown if player logic 2 returns null as player.
     */
    @Test(expected = NullPointerException.class)
    public void new_withPlayerLogic2HasNullPlayer_throwsException() {
        when(playerLogic1.getPlayer()).thenReturn(player1);
        when(playerLogic2.getPlayer()).thenReturn(null);
        new GameLogic(gameBoard, playerLogic1, playerLogic2);
    }

    /**
     * Checks if an exception is thrown if player logic 1 has a player which is unknown at the
     * game board.
     */
    @Test(expected = AssertionError.class)
    public void new_withPlayerLogic1HasDifferentPlayerThanGameBoard_throwsException() {
        when(playerLogic1.getPlayer()).thenReturn(player1);
        when(gameBoard.getPlayer1()).thenReturn(mock(Player.class, "someOtherPlayer"));
        new GameLogic(gameBoard, playerLogic1, playerLogic2);
    }

    /**
     * Checks if an exception is thrown if player logic 2 has a player which is unknown at the
     * game board.
     */
    @Test(expected = AssertionError.class)
    public void new_withPlayerLogic2HasDifferentPlayerThanGameBoard_throwsException() {
        when(playerLogic2.getPlayer()).thenReturn(player2);
        when(gameBoard.getPlayer2()).thenReturn(mock(Player.class, "someOtherPlayer"));
        new GameLogic(gameBoard, playerLogic1, playerLogic2);
    }


    /**
     * Checks if an  exception is thrown if setSquareCollector() is called with null.
     */
    @Test(expected = NullPointerException.class)
    public void setSquareCollector_withNull_throwsException() {
        gameLogic.setSquareCollector(null);
    }

    /**
     * Checks if an exception is thrown if setGameOverVerifier() is called with null.
     */
    @Test(expected = NullPointerException.class)
    public void setGameOverVerifier_withNull_throwsException() {
        gameLogic.setGameOverVerifier(null);
    }


    /**
     * Checks if an exception is thrown if startGame() is called with null as player.
     */
    @Test(expected = NullPointerException.class)
    public void startGame_withNullPlayer_throwsException() {
        gameLogic.startGame(null);
    }

    /**
     * Checks if startGame() resets the square collector.
     */
    @Test
    public void startGame_resetsSquareCollector() {
        gameLogic.startGame(player1);
        verify(squareCollector, times(1)).reset();
    }


    /**
     * Checks if isStarted() returns false after the initialization of the game logic, because
     * the game has not started yet.
     */
    @Test
    public void isStarted_returnsFalse_afterNew() {
        assertEquals(false, gameLogic.isStarted());
    }

    /**
     * Checks if isStarted() returns true after the game is started.
     */
    @Test
    public void isStarted_returnsTrue_afterGameIsStarted() {
        gameLogic.startGame(player1);
        assertEquals(true, gameLogic.isStarted());
    }

    /**
     * Checks if an exception is thrown if the active player is null.
     */
    @Test(expected = NullPointerException.class)
    public void startGame_withNull_throwsException() {
        gameLogic.startGame(null);
    }

    /**
     * Checks if an exception is thrown if the active player is not player one or two.
     */
    @Test(expected = AssertionError.class)
    public void startGame_withUnknownPlayer_throwsException() {
        gameLogic.startGame(mock(Player.class, "unknown-player"));
    }

    /**
     * Checks if startGame() notifies the game logic listeners that the active player has changed.
     */
    @Test
    public void startGame__withPlayer1_notifiesListenersOnActivePlayerChanged() {
        gameLogic.startGame(player1);
        verify(listener, times(1)).onActivePlayerChanged(player1);
    }

    /**
     * Checks if startGame() notifies the game logic listeners that the game has started.
     */
    @Test
    public void startGame_withPlayer1_notifiesListenersOnGameStarted() {
        gameLogic.startGame(player1);
        verify(listener, times(1)).onGameStarted(player1);
    }

    /**
     * Checks if startGame() notifies the game logic listeners that the active player has changed.
     */
    @Test
    public void startGame__withPlayer2_notifiesListenersOnActivePlayerChanged() {
        gameLogic.startGame(player2);
        verify(listener, times(1)).onActivePlayerChanged(player2);
    }

    /**
     * Checks if startGame() notifies the game logic listeners that the game has started.
     */
    @Test
    public void startGame_withPlayer2_notifiesListenersOnGameStarted() {
        gameLogic.startGame(player2);
        verify(listener, times(1)).onGameStarted(player2);
    }

    /**
     * Checks if startGame() requests a move at player logic 1, if game was started with player 1.
     */
    @Test
    public void startGame_withPlayer1_requestsMoveAtPlayerLogic1() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), any(ILogicCallback.class));
    }

    /**
     * Checks if startGame() requests a move at player logic 2, if game was started with player 2.
     */
    @Test
    public void startGame_withPlayer2_requestsMoveAtPlayerLogic2() {
        gameLogic.startGame(player2);
        verify(playerLogic2, times(1)).requestMove(eq(gameBoard), any(ILogicCallback.class));
    }

    /**
     * Checks if startGame() places a piece on the game board when player logic makes a move.
     */
    @Test
    public void startGame_withPlayerLogic1MakesValidMove_placesPieceOnGameBoard() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        assertTrue(callback.getValue().makeMove(1, player1));
        verify(gameBoard, times(1)).placePiece(1, player1);
    }

    /**
     * Checks if startGame() doesn't place a piece on the game board if the field is not empty.
     * In this case makeMove() from the callback must return false.
     */
    @Test
    public void startGame_withPlayerLogic1MakesMoveOnNotEmptyField_callbackReturnsFalse() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameBoard.isFieldEmpty(1)).thenReturn(false);

        assertFalse(callback.getValue().makeMove(1, player1));
        verify(gameBoard, never()).placePiece(1, player1);
    }

    /**
     * Checks if callback throws an exception if makeMove() is called with a not active player.
     */
    @Test(expected = AssertionError.class)
    public void startGame_withPlayerLogic1MakesInvalidMoveWithNotActivePlayer_throwsException() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());

        callback.getValue().makeMove(1, player2);
    }

    /**
     * Checks if callback throws an exception if makeMove() is called with an index < 0.
     */
    @Test(expected = AssertionError.class)
    public void startGame_withPlayerLogic1MakesInvalidMoveWithIndexLowerThan0_throwsException() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());

        callback.getValue().makeMove(-1, player1);
    }

    /**
     * Checks if callback throws an exception if makeMove() is called with an index > 63.
     */
    @Test(expected = AssertionError.class)
    public void startGame_withPlayerLogic1MakesInvalidMoveWithIndexGreaterThan63_throwsException() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());

        callback.getValue().makeMove(64, player1);
    }

    /**
     * Checks if startGame() places a piece on the game board when player logic makes a move.
     */
    @Test
    public void startGame_withPlayerLogic2MakesValidMove_placesPieceOnGameBoard() {
        gameLogic.startGame(player2);
        verify(playerLogic2, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        assertTrue(callback.getValue().makeMove(1, player2));
        verify(gameBoard, times(1)).placePiece(1, player2);
    }

    /**
     * Checks if startGame() doesn't place a piece on the game board if the field is not empty.
     * In this case makeMove() from the callback must return false.
     */
    @Test(expected = AssertionError.class)
    public void startGame_withPlayerLogic2MakesInvalidMoveWithInvalidPlayer_throwsException() {
        gameLogic.startGame(player2);
        verify(playerLogic2, times(1)).requestMove(eq(gameBoard), callback.capture());

        callback.getValue().makeMove(1, player1);
    }

    /**
     * Checks if active player gets switched after a valid move with player 1.
     */
    @Test
    public void startGame_withPlayerLogic1MakesValidMove_switchesActivePlayer() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        callback.getValue().makeMove(1, player1);
        assertEquals(player2, gameLogic.getActivePlayer());
        verify(listener, times(1)).onActivePlayerChanged(player2);
        verify(playerLogic2, times(1)).requestMove(eq(gameBoard), callback.capture());
    }

    /**
     * Checks if active player gets switched after a valid move with player 2.
     */
    @Test
    public void startGame_withPlayerLogic2MakesValidMove_switchesActivePlayer() {
        gameLogic.startGame(player2);
        verify(playerLogic2, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        callback.getValue().makeMove(1, player2);
        assertEquals(player1, gameLogic.getActivePlayer());
        verify(listener, times(1)).onActivePlayerChanged(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
    }

    /**
     * Checks if active player gets switched after multiple moves.
     */
    @Test
    public void startGame_withValidMovesAndPlayerSwitches() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        callback.getValue().makeMove(1, player1);
        assertEquals(player2, gameLogic.getActivePlayer());
        verify(listener, times(1)).onActivePlayerChanged(player2);
        verify(playerLogic2, times(1)).requestMove(eq(gameBoard), callback.capture());
        verify(gameBoard, times(1)).placePiece(1, player1);

        when(gameBoard.isFieldEmpty(2)).thenReturn(true);
        callback.getValue().makeMove(2, player2);
        assertEquals(player1, gameLogic.getActivePlayer());
        verify(gameBoard, times(1)).placePiece(2, player2);
    }

    /**
     * Checks if listeners get notified when player 1 won after his move.
     */
    @Test
    public void startGame_withPlayer1MakesMoveAndPlayer1Won_notifiesListeners() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameOverVerifier.isGameOver(gameBoard, squareCollector)).thenReturn(PLAYER1_WON);
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        callback.getValue().makeMove(1, player1);

        verify(listener, times(1)).onGameOver(player1);
        verify(listener, never()).onActivePlayerChanged(player2);
        verify(playerLogic2, never()).requestMove(eq(gameBoard), callback.capture());
    }

    /**
     * Checks if listeners get notified when player 2 won after his move.
     */
    @Test
    public void startGame_withPlayer1MakesMoveAndPlayer2Won_notifiesListeners() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameOverVerifier.isGameOver(gameBoard, squareCollector)).thenReturn(PLAYER2_WON);
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        callback.getValue().makeMove(1, player1);

        verify(listener, times(1)).onGameOver(player2);
        verify(listener, never()).onActivePlayerChanged(player2);
        verify(playerLogic2, never()).requestMove(eq(gameBoard), callback.capture());
    }

    /**
     * Checks if listeners get notified when game is a draw after a move.
     */
    @Test
    public void startGame_withPlayer1MakesMoveAndGameDraw_notifiesListeners() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameOverVerifier.isGameOver(gameBoard, squareCollector)).thenReturn(GAME_DRAW);
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        callback.getValue().makeMove(1, player1);

        verify(listener, times(1)).onGameOver(null);
        verify(listener, never()).onActivePlayerChanged(player2);
        verify(playerLogic2, never()).requestMove(eq(gameBoard), callback.capture());
    }

    /**
     * Checks if listeners doesn't get notified when game is not over after a move.
     */
    @Test
    public void startGame_withPlayer1MakesMoveAndGameNotOver_doesNotNotifyListeners() {
        gameLogic.startGame(player1);
        verify(playerLogic1, times(1)).requestMove(eq(gameBoard), callback.capture());
        when(gameOverVerifier.isGameOver(gameBoard, squareCollector)).thenReturn(NOT_OVER);
        when(gameBoard.isFieldEmpty(1)).thenReturn(true);

        callback.getValue().makeMove(1, player1);

        verify(listener, never()).onGameOver(null);
        verify(listener, times(1)).onActivePlayerChanged(player2);
        verify(playerLogic2, times(1)).requestMove(eq(gameBoard), callback.capture());
    }


    /**
     * Checks if the active player is null after the initialization of the game logic, because
     * the game has not started yet.
     */
    @Test
    public void getActivePlayer_returnsNull_afterNew() {
        assertEquals(null, gameLogic.getActivePlayer());
    }

    /**
     * Checks if getActivePlayer() returns player one after the game was started with player one
     * as active player.
     */
    @Test
    public void getActivePlayer_returnsPlayer1_ifGameIsStartedWithPlayer1() {
        gameLogic.startGame(player1);
        assertEquals(player1, gameLogic.getActivePlayer());
    }

    /**
     * Checks if getActivePlayer() returns player two after the game was started with player two
     * as active player.
     */
    @Test
    public void getActivePlayer_returnsPlayer2_ifGameIsStartedWithPlayer2() {
        gameLogic.startGame(player2);
        assertEquals(player2, gameLogic.getActivePlayer());
    }

    /**
     * Checks if the active player logic is null after the initialization of the game logic, because
     * the game has not started yet.
     */
    @Test
    public void getActivePlayerLogic_returnsNull_afterNew() {
        assertEquals(null, gameLogic.getActivePlayerLogic());
    }

    /**
     * Checks if getActivePlayerLogic() returns player logic one after the game was started with player one
     * as active player.
     */
    @Test
    public void getActivePlayerLogic_returnsPlayerLogic1_ifGameIsStartedWithPlayer1() {
        gameLogic.startGame(player1);
        assertEquals(playerLogic1, gameLogic.getActivePlayerLogic());
    }

    /**
     * Checks if getActivePlayerLogic() returns player logic two after the game was started with player two
     * as active player.
     */
    @Test
    public void getActivePlayerLogic_returnsPlayerLogic2_ifGameIsStartedWithPlayer2() {
        gameLogic.startGame(player2);
        assertEquals(playerLogic2, gameLogic.getActivePlayerLogic());
    }


    /**
     * Checks if getGameBoard() returns the game board.
     */
    @Test
    public void getGameBoard_returnsGameBoard() {
        assertEquals(gameBoard, gameLogic.getGameBoard());
    }


    /**
     * Checks if getSquares() returns an empty set after construction.
     */
    @Test
    public void getSquares_afterNew_returnsEmptySet() {
        assertEquals(0, gameLogic.getSquares().size());
    }


    /**
     * Checks if getScore() returns the score count from the square collector for player 1.
     */
    @Test
    public void getScore_withPlayer1_returnsScoreFromSquareCollector() {
        when(squareCollector.getScore(player1)).thenReturn(100);
        assertEquals(100, gameLogic.getScore(player1));
    }

    /**
     * Checks if getScore() returns the score count from the square collector for player 2.
     */
    @Test
    public void getScore_withPlayer2_returnsScoreFromSquareCollector() {
        when(squareCollector.getScore(player2)).thenReturn(150);
        assertEquals(150, gameLogic.getScore(player2));
    }


    /**
     * Checks if getSquareCount() returns the number of squares from the square collector for player 1.
     */
    @Test
    public void getSquareCount_withPlayer1_returnsScoreFromSquareCollector() {
        when(squareCollector.getSquareCount(player1)).thenReturn(10);
        assertEquals(10, gameLogic.getSquareCount(player1));
    }

    /**
     * Checks if getSquareCount() returns the number of squares from the square collector for player 2.
     */
    @Test
    public void getSquareCount_withPlayer2_returnsScoreFromSquareCollector() {
        when(squareCollector.getSquareCount(player2)).thenReturn(15);
        assertEquals(15, gameLogic.getSquareCount(player2));
    }


    /**
     * Checks if an exception is thrown if listener is null.
     */
    @Test(expected = NullPointerException.class)
    public void addGameLogicListener_withNull_throwsException() {
        gameLogic.addGameLogicListener(null);
    }

    /**
     * Checks if an exception is thrown if listener is null.
     */
    @Test(expected = NullPointerException.class)
    public void removeGameLogicListener_withNull_throwsException() {
        gameLogic.removeGameLogicListener(null);
    }

}
