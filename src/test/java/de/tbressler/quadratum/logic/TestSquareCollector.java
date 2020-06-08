package de.tbressler.quadratum.logic;

import de.tbressler.quadratum.model.GameBoard;
import de.tbressler.quadratum.model.Player;
import de.tbressler.quadratum.model.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for class SquareCollector.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestSquareCollector {

    // Class under test:
    private SquareCollector squareCollector;

    // Mocks:
    private Player player1 = mock(Player.class, "player1");
    private Player player2 = mock(Player.class, "player2");

    private GameBoard gameBoard = mock(GameBoard.class, "gameBoard");


    @Before
    public void setUp() {
        when(gameBoard.getPlayer1()).thenReturn(player1);
        when(gameBoard.getPlayer2()).thenReturn(player2);
        squareCollector = new SquareCollector();
    }

    @Test
    public void getDetectedSquares_afterConstruction_returnsEmptySet() {
        assertTrue(squareCollector.getDetectedSquares().isEmpty());
    }

    @Test
    public void getDetectedSquares_afterReset_clearsSquares() {
        when(gameBoard.getPiece(0)).thenReturn(player1);
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(8)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);

        squareCollector.reset();
        assertTrue(squareCollector.getDetectedSquares().isEmpty());
    }

    @Test
    public void getDetectedSquares_afterSquareWasDetected_returnsSetWithSquare() {
        when(gameBoard.getPiece(0)).thenReturn(player2);
        when(gameBoard.getPiece(1)).thenReturn(player2);
        when(gameBoard.getPiece(8)).thenReturn(player2);
        when(gameBoard.getPiece(9)).thenReturn(player2);

        squareCollector.detect(gameBoard, player2);
        Set<Square> result = squareCollector.getDetectedSquares();

        assertEquals(1, result.size());
        assertTrue(result.contains(new Square(new int[]{0,1,8,9}, player2)));
    }

    @Test
    public void getDetectedSquares_withSquareForPlayer1and2_returnsSetWithBothSquares() {
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(15)).thenReturn(player1);
        when(gameBoard.getPiece(48)).thenReturn(player1);
        when(gameBoard.getPiece(62)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player2);
        when(gameBoard.getPiece(19)).thenReturn(player2);
        when(gameBoard.getPiece(24)).thenReturn(player2);
        when(gameBoard.getPiece(34)).thenReturn(player2);
        squareCollector.detect(gameBoard, player1);
        squareCollector.detect(gameBoard, player2);

        Set<Square> result = squareCollector.getDetectedSquares();

        assertEquals(2, result.size());
        assertTrue(result.contains(new Square(new int[]{1,15,48,62}, player1)));
        assertTrue(result.contains(new Square(new int[]{9,19,24,34}, player2)));
    }

    @Test(expected = NullPointerException.class)
    public void detect_withNullGameBoard_throwsException() {
        squareCollector.detect(null, player1);
    }

    @Test(expected = NullPointerException.class)
    public void detect_withNullPlayer_throwsException() {
        squareCollector.detect(gameBoard, null);
    }

    @Test
    public void detect_withValidSquareOnGameBoardForPlayer1_returnsSetWithSquare() {
        when(gameBoard.getPiece(0)).thenReturn(player1);
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(8)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player1);

        Set<Square> result = squareCollector.detect(gameBoard, player1);

        assertEquals(1, result.size());
        assertTrue(result.contains(new Square(new int[]{0,1,8,9}, player1)));
    }

    @Test
    public void detect_withValidSquareOnGameBoardForPlayer2_returnsSetWithSquare() {
        when(gameBoard.getPiece(0)).thenReturn(player2);
        when(gameBoard.getPiece(1)).thenReturn(player2);
        when(gameBoard.getPiece(8)).thenReturn(player2);
        when(gameBoard.getPiece(9)).thenReturn(player2);

        Set<Square> result = squareCollector.detect(gameBoard, player2);

        assertEquals(1, result.size());
        assertTrue(result.contains(new Square(new int[]{0,1,8,9}, player2)));
    }

    @Test
    public void detect_withSquareForPlayer1and2_returnsSetWithSquareForPlayer1() {
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(15)).thenReturn(player1);
        when(gameBoard.getPiece(48)).thenReturn(player1);
        when(gameBoard.getPiece(62)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player2);
        when(gameBoard.getPiece(19)).thenReturn(player2);
        when(gameBoard.getPiece(24)).thenReturn(player2);
        when(gameBoard.getPiece(34)).thenReturn(player2);
        Set<Square> result = squareCollector.detect(gameBoard, player1);

        assertEquals(1, result.size());
        assertTrue(result.contains(new Square(new int[]{1,15,48,62}, player1)));
    }

    @Test
    public void detect_withEmptyGameBoard_returnsEmptySetForPlayer1() {
        Set<Square> result = squareCollector.detect(gameBoard, player1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void detect_withEmptyGameBoard_returnsEmptySetForPlayer2() {
        Set<Square> result = squareCollector.detect(gameBoard, player2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void detect_withInvalidSquareOnGameBoard1_returnsEmptySet() {
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(15)).thenReturn(player1);
        when(gameBoard.getPiece(40)).thenReturn(player1);
        when(gameBoard.getPiece(62)).thenReturn(player1);

        Set<Square> result = squareCollector.detect(gameBoard, player1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void detect_withInvalidSquareOnGameBoard2_returnsEmptySet() {
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(15)).thenReturn(player1);
        when(gameBoard.getPiece(48)).thenReturn(player2);
        when(gameBoard.getPiece(62)).thenReturn(player1);

        Set<Square> result = squareCollector.detect(gameBoard, player1);
        assertTrue(result.isEmpty());
    }

    @Test
    public void detect_calledSecondTimeForSameSquareOnGameBoard_returnsEmptySet() {
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(15)).thenReturn(player1);
        when(gameBoard.getPiece(48)).thenReturn(player1);
        when(gameBoard.getPiece(62)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);

        Set<Square> result = squareCollector.detect(gameBoard, player1);
        assertTrue(result.isEmpty());
    }


    @Test
    public void getScore_withPlayer1AfterNew_returns0() {
        assertEquals(0, squareCollector.getScore(player1));
    }

    @Test
    public void getScore_withPlayer2AfterNew_returns0() {
        assertEquals(0, squareCollector.getScore(player2));
    }

    @Test
    public void getScore_withPlayer1AndOneValidSquare_returns9() {
        when(gameBoard.getPiece(36)).thenReturn(player1);
        when(gameBoard.getPiece(39)).thenReturn(player1);
        when(gameBoard.getPiece(60)).thenReturn(player1);
        when(gameBoard.getPiece(63)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);

        assertEquals(16, squareCollector.getScore(player1));
    }

    @Test
    public void getScore_withTwoSquares_returns10() {
        when(gameBoard.getPiece(36)).thenReturn(player1);
        when(gameBoard.getPiece(39)).thenReturn(player1);
        when(gameBoard.getPiece(60)).thenReturn(player1);
        when(gameBoard.getPiece(63)).thenReturn(player1);
        when(gameBoard.getPiece(0)).thenReturn(player1);
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(8)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);

        assertEquals(20, squareCollector.getScore(player1));
    }

    @Test
    public void getScore_withPlayer1AndOneSquareOfPlayer2_returns0() {
        when(gameBoard.getPiece(36)).thenReturn(player2);
        when(gameBoard.getPiece(39)).thenReturn(player2);
        when(gameBoard.getPiece(60)).thenReturn(player2);
        when(gameBoard.getPiece(63)).thenReturn(player2);
        squareCollector.detect(gameBoard, player2);

        assertEquals(0, squareCollector.getScore(player1));
    }

    @Test
    public void getScore_afterReset_returns0() {
        when(gameBoard.getPiece(36)).thenReturn(player1);
        when(gameBoard.getPiece(39)).thenReturn(player1);
        when(gameBoard.getPiece(60)).thenReturn(player1);
        when(gameBoard.getPiece(63)).thenReturn(player1);
        when(gameBoard.getPiece(0)).thenReturn(player1);
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(8)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);
        squareCollector.reset();

        assertEquals(0, squareCollector.getScore(player1));
    }


    @Test
    public void getSquareCount_withPlayer1AfterNew_returns0() {
        assertEquals(0, squareCollector.getSquareCount(player1));
    }

    @Test
    public void getSquareCount_withPlayer2AfterNew_returns0() {
        assertEquals(0, squareCollector.getSquareCount(player2));
    }

    @Test
    public void getSquareCount_withPlayer1AndOneValidSquares_returns1() {
        when(gameBoard.getPiece(36)).thenReturn(player1);
        when(gameBoard.getPiece(39)).thenReturn(player1);
        when(gameBoard.getPiece(60)).thenReturn(player1);
        when(gameBoard.getPiece(63)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);

        assertEquals(1, squareCollector.getSquareCount(player1));
    }

    @Test
    public void getSquareCount_withPlayer1AndOneSquareOfPlayer2_returns0() {
        when(gameBoard.getPiece(36)).thenReturn(player2);
        when(gameBoard.getPiece(39)).thenReturn(player2);
        when(gameBoard.getPiece(60)).thenReturn(player2);
        when(gameBoard.getPiece(63)).thenReturn(player2);
        squareCollector.detect(gameBoard, player2);

        assertEquals(0, squareCollector.getSquareCount(player1));
    }

    @Test
    public void getSquareCount_withTwoSquares_returns2() {
        when(gameBoard.getPiece(36)).thenReturn(player1);
        when(gameBoard.getPiece(39)).thenReturn(player1);
        when(gameBoard.getPiece(60)).thenReturn(player1);
        when(gameBoard.getPiece(63)).thenReturn(player1);
        when(gameBoard.getPiece(0)).thenReturn(player1);
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(8)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);

        assertEquals(2, squareCollector.getSquareCount(player1));
    }

    @Test
    public void getSquareCount_afterReset_returns0() {
        when(gameBoard.getPiece(36)).thenReturn(player1);
        when(gameBoard.getPiece(39)).thenReturn(player1);
        when(gameBoard.getPiece(60)).thenReturn(player1);
        when(gameBoard.getPiece(63)).thenReturn(player1);
        when(gameBoard.getPiece(0)).thenReturn(player1);
        when(gameBoard.getPiece(1)).thenReturn(player1);
        when(gameBoard.getPiece(8)).thenReturn(player1);
        when(gameBoard.getPiece(9)).thenReturn(player1);
        squareCollector.detect(gameBoard, player1);
        squareCollector.reset();

        assertEquals(0, squareCollector.getSquareCount(player1));
    }
}
