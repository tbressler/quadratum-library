package de.tbressler.quadratum;

import de.tbressler.quadratum.logic.GameLogic;
import de.tbressler.quadratum.logic.IGameLogicListener;
import de.tbressler.quadratum.logic.players.HumanPlayerLogic;
import de.tbressler.quadratum.model.GameBoard;
import de.tbressler.quadratum.model.IGameBoardListener;
import de.tbressler.quadratum.model.Player;
import de.tbressler.quadratum.model.Square;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

/**
 * Integration tests.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class IntTestLibrary {

    // Mocks for listeners:
    private IGameBoardListener boardListener = mock(IGameBoardListener.class, "boardListener");
    private IGameLogicListener logicListener = mock(IGameLogicListener.class, "logicListener");

    // Capture:
    private ArgumentCaptor<Set> square = forClass(Set.class);


    @Test
    public void integrationTest_withTwoHumanPlayers() {

        int[] movesOfPlayer1 = new int[] { 1, 15, 48, 62, 10, 12, 14, 26, 30, 28};
        int[] movesOfPlayer2 = new int[] {34, 59, 54, 29, 36, 38, 52, 18, 20, 36};

        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        GameBoard gameBoard = new GameBoard(player1, player2);
        gameBoard.addGameBoardListener(boardListener);

        HumanPlayerLogic playerLogic1 = new HumanPlayerLogic(player1);
        HumanPlayerLogic playerLogic2 = new HumanPlayerLogic(player2);

        GameLogic gameLogic = new GameLogic(gameBoard, playerLogic1, playerLogic2);
        gameLogic.addGameLogicListener(logicListener);

        gameLogic.startGame(player1);

        verify(logicListener, times(1)).onGameStarted(player1);

        for(int i = 0; i < movesOfPlayer1.length; i++) {

            verify(logicListener, times(1 + i)).onActivePlayerChanged(player1);

            playerLogic1.placePiece(movesOfPlayer1[i]);

            verify(boardListener, times(1)).onPiecePlaced(movesOfPlayer1[i], player1);
            verify(logicListener, times(1 + i)).onActivePlayerChanged(player2);

            playerLogic2.placePiece(movesOfPlayer2[i]);

            verify(boardListener, times(1)).onPiecePlaced(movesOfPlayer2[i], player2);
        }

        // Check if listener notifications are correct:
        verify(logicListener, times(2)).onNewSquaresFound(eq(player1), square.capture());
        verify(logicListener, times(3)).onNewSquaresFound(eq(player2), square.capture());
        Set foundSquares = new HashSet();
        for (Set squareSet : square.getAllValues())
            foundSquares.addAll(squareSet);
        assertSetOfSquares(foundSquares, player1, player2);

        // Check if game logic is correct:
        Set squaresFromLogic = gameLogic.getSquares();
        assertSetOfSquares(squaresFromLogic, player1, player2);
    }

    /* Assert if the set of squares contains the correct squares. */
    private void assertSetOfSquares(Set squares, Player player1, Player player2) {

        assertEquals(7, squares.size());

        assertTrue(squares.contains(new Square(new int[] { 1, 15, 48, 62}, player1)));
        assertTrue(squares.contains(new Square(new int[] {10, 12, 26, 28}, player1)));
        assertTrue(squares.contains(new Square(new int[] {12, 14, 28, 30}, player1)));

        assertTrue(squares.contains(new Square(new int[] {29, 34, 54, 59}, player2)));
        assertTrue(squares.contains(new Square(new int[] {36, 38, 52, 54}, player2)));
        assertTrue(squares.contains(new Square(new int[] {18, 20, 34, 36}, player2)));
        assertTrue(squares.contains(new Square(new int[] {20, 34, 38, 52}, player2)));
    }

}
