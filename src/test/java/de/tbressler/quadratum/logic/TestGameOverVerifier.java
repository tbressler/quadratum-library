package de.tbressler.quadratum.logic;

import de.tbressler.quadratum.model.GameBoard;
import de.tbressler.quadratum.model.Player;
import org.junit.Before;
import org.junit.Test;

import static de.tbressler.quadratum.logic.GameOverVerifier.GameOverState.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for class GameOverVerifier.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestGameOverVerifier {

    // Class under test:
    private GameOverVerifier gameOverVerifier;

    // Mocks:
    private Player player1 = mock(Player.class, "player1");
    private Player player2 = mock(Player.class, "player2");

    private GameBoard gameBoard = mock(GameBoard.class, "gameBoard");

    private SquareCollector squareCollector = mock(SquareCollector.class, "squareCollector");


    @Before
    public void setUp() {
        when(gameBoard.getPlayer1()).thenReturn(player1);
        when(gameBoard.getPlayer2()).thenReturn(player2);
        gameOverVerifier = new GameOverVerifier(150, 15);
    }


    @Test(expected = AssertionError.class)
    public void new_withNegativeMinimumScore_throwsException() {
        new GameOverVerifier(-1, 15);
    }

    @Test(expected = AssertionError.class)
    public void new_withMinimumScore0_throwsException() {
        new GameOverVerifier(0, 15);
    }

    @Test(expected = AssertionError.class)
    public void new_withNegativeMinimumDifference_throwsException() {
        new GameOverVerifier(150, -1);
    }

    @Test(expected = AssertionError.class)
    public void new_withMinimumDifference0_throwsException() {
        new GameOverVerifier(150, 0);
    }


    @Test(expected = NullPointerException.class)
    public void isGameOver_withNullGameBoard_throwsException() {
        gameOverVerifier.isGameOver(null, squareCollector);
    }

    @Test(expected = NullPointerException.class)
    public void isGameOver_withNullSquareCollector_throwsException() {
        gameOverVerifier.isGameOver(gameBoard, null);
    }

    @Test
    public void isGameOver_whenPlayer1Score150AndPlayer2Score135_returnsPLAYER1_WON() {
        when(squareCollector.getScore(player1)).thenReturn(150);
        when(squareCollector.getScore(player2)).thenReturn(135);
        assertEquals(PLAYER1_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score165AndPlayer2Score150_returnsPLAYER1_WON() {
        when(squareCollector.getScore(player1)).thenReturn(165);
        when(squareCollector.getScore(player2)).thenReturn(150);
        assertEquals(PLAYER1_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score135AndPlayer2Score150_returnsPLAYER2_WON() {
        when(squareCollector.getScore(player1)).thenReturn(135);
        when(squareCollector.getScore(player2)).thenReturn(150);
        assertEquals(PLAYER2_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score150AndPlayer2Score165_returnsPLAYER2_WON() {
        when(squareCollector.getScore(player1)).thenReturn(150);
        when(squareCollector.getScore(player2)).thenReturn(165);
        assertEquals(PLAYER2_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score0AndPlayer2Score0_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(0);
        when(squareCollector.getScore(player2)).thenReturn(0);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score0AndPlayer2Score15_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(0);
        when(squareCollector.getScore(player2)).thenReturn(15);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score15AndPlayer2Score0_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(15);
        when(squareCollector.getScore(player2)).thenReturn(0);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score150AndPlayer2Score150_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(150);
        when(squareCollector.getScore(player2)).thenReturn(150);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1Score30AndPlayer2Score60_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(30);
        when(squareCollector.getScore(player2)).thenReturn(60);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1ScoreGreaterAndNoMoreMovesPossible_returnsPLAYER1_WON() {
        when(squareCollector.getScore(player1)).thenReturn(60);
        when(squareCollector.getScore(player2)).thenReturn(30);
        when(gameBoard.getPiece(anyInt())).thenReturn(player1);
        assertEquals(PLAYER1_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer2ScoreGreaterAndNoMoreMovesPossible_returnsPLAYER2_WON() {
        when(squareCollector.getScore(player1)).thenReturn(30);
        when(squareCollector.getScore(player2)).thenReturn(60);
        when(gameBoard.getPiece(anyInt())).thenReturn(player1);
        assertEquals(PLAYER2_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenEqualScoreGreaterAndNoMoreMovesPossible_returnsGAME_DRAW() {
        when(squareCollector.getScore(player1)).thenReturn(30);
        when(squareCollector.getScore(player2)).thenReturn(30);
        when(gameBoard.getPiece(anyInt())).thenReturn(player1);
        assertEquals(GAME_DRAW, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1LeadsAndCanDoMoreSquares_returnsPLAYER1_WON() {
        when(squareCollector.getScore(player1)).thenReturn(60);
        when(squareCollector.getScore(player2)).thenReturn(30);
        when(gameBoard.getPiece(anyInt())).thenReturn(player1);
        when(gameBoard.getPiece(10)).thenReturn(null);
        assertEquals(PLAYER1_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer1LagBehindButCanDoMoreSquares_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(30);
        when(squareCollector.getScore(player2)).thenReturn(60);
        when(gameBoard.getPiece(anyInt())).thenReturn(player1);
        when(gameBoard.getPiece(10)).thenReturn(null);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer2LeadsAndCanDoMoreSquares_returnsPLAYER2_WON() {
        when(squareCollector.getScore(player1)).thenReturn(30);
        when(squareCollector.getScore(player2)).thenReturn(60);
        when(gameBoard.getPiece(anyInt())).thenReturn(player2);
        when(gameBoard.getPiece(10)).thenReturn(null);
        assertEquals(PLAYER2_WON, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenPlayer2LagBehindButCanDoMoreSquares_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(60);
        when(squareCollector.getScore(player2)).thenReturn(30);
        when(gameBoard.getPiece(anyInt())).thenReturn(player2);
        when(gameBoard.getPiece(10)).thenReturn(null);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenScoreIsDrawAndPlayer1CanDoMoreSquares_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(60);
        when(squareCollector.getScore(player2)).thenReturn(60);
        when(gameBoard.getPiece(anyInt())).thenReturn(player1);
        when(gameBoard.getPiece(10)).thenReturn(null);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

    @Test
    public void isGameOver_whenScoreIsDrawAndPlayer2CanDoMoreSquares_returnsNOT_OVER() {
        when(squareCollector.getScore(player1)).thenReturn(60);
        when(squareCollector.getScore(player2)).thenReturn(60);
        when(gameBoard.getPiece(anyInt())).thenReturn(player2);
        when(gameBoard.getPiece(10)).thenReturn(null);
        assertEquals(NOT_OVER, gameOverVerifier.isGameOver(gameBoard, squareCollector));
    }

}
