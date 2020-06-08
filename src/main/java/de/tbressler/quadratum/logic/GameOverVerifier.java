package de.tbressler.quadratum.logic;

import de.tbressler.quadratum.model.IReadOnlyGameBoard;
import de.tbressler.quadratum.model.Player;

import static de.tbressler.quadratum.logic.GameOverVerifier.GameOverState.*;
import static de.tbressler.quadratum.logic.GameOverVerifier.PossibleMoves.*;
import static de.tbressler.quadratum.utils.SquareUtils.getPossiblePieces;
import static java.util.Objects.requireNonNull;

/**
 * This class is used by the game logic in order to check, if the game is over and who won the game.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class GameOverVerifier {

    /** The game over state. */
    enum GameOverState {
        /** The game is not over. */
        NOT_OVER,
        /** The game is over and player 1 won. */
        PLAYER1_WON,
        /** The game is over and player 2 won. */
        PLAYER2_WON,
        /** The game is over and the game is a draw. */
        GAME_DRAW
    }

    /** Possible moves. */
    enum PossibleMoves {
        /** Both players can do squares. */
        BOTH_PLAYERS,
        /** Only player 1 can do more squares. */
        ONLY_PLAYER1,
        /** Only player 2 can do more squares. */
        ONLY_PLAYER2,
        /** No player can do more squares. */
        NO_PLAYER
    }

    /* The minimum score for a player to win the game. */
    private final int minScore;

    /* The minimum difference between the player scores to win the game. */
    private final int minDifference;


    /**
     * Creates the game over verifier.
     *
     * @param minScore The minimum score to win, must be > 0 (suggested 150).
     * @param minDifference The minimum difference between score, must be > 0 (suggested 15).
     */
    public GameOverVerifier(int minScore, int minDifference) {
        if (minScore < 1) throw new AssertionError("minScore must be > 0!");
        if (minDifference < 1) throw new AssertionError("minDifference must be > 0!");
        this.minScore = minScore;
        this.minDifference = minDifference;
    }

    /**
     * Checks if the game is over.
     *
     * @param gameBoard The game board, must not be null.
     * @param squareCollector The current squares, must not be null.
     * @return The game over state, never null.
     */
    public GameOverState isGameOver(IReadOnlyGameBoard gameBoard, SquareCollector squareCollector) {
        requireNonNull(gameBoard);
        requireNonNull(squareCollector);

        int scorePlayer1 = squareCollector.getScore(gameBoard.getPlayer1());
        int scorePlayer2 = squareCollector.getScore(gameBoard.getPlayer2());

        // Check if one player has won the game:
        if ((scorePlayer1 >= minScore) || (scorePlayer2 >= minScore)) {

            int dif = scorePlayer1 - scorePlayer2;

            if (dif >= minDifference) {
                return PLAYER1_WON;
            } else if (dif <= -minDifference) {
                return PLAYER2_WON;
            }
        }

        // Check if more squares are possible:
        switch (canPlayersDoMoreSquares(gameBoard)) {
            case BOTH_PLAYERS:
                return NOT_OVER;
            case NO_PLAYER:
                return getGameDrawState(scorePlayer1, scorePlayer2);
            case ONLY_PLAYER1:
                return (scorePlayer1 > scorePlayer2) ? PLAYER1_WON : NOT_OVER;
            case ONLY_PLAYER2:
                return (scorePlayer2 > scorePlayer1) ? PLAYER2_WON : NOT_OVER;
            default:
                throw new IllegalStateException("Unknown state!");
        }
    }

    /* Returns the game draw state. */
    private GameOverState getGameDrawState(int scorePlayer1, int scorePlayer2) {
        if (scorePlayer1 > scorePlayer2)
            return PLAYER1_WON;
        else if (scorePlayer2 > scorePlayer1)
            return PLAYER2_WON;
        return GAME_DRAW;
    }

    /* Checks if the players can do more squares on the game board. */
    private PossibleMoves canPlayersDoMoreSquares(IReadOnlyGameBoard gameBoard) {
        Player player1 = gameBoard.getPlayer1();

        int[] possible;
        Player[] pieces = new Player[4];

        boolean hasPlayer1;
        boolean hasPlayer2;
        boolean hasEmpty;

        boolean player1CanDoMoreSquares = false;
        boolean player2CanDoMoreSquares = false;

        // Go through the game board and check for possible squares:
        for (int i = 0; i < 55; i++) {

            pieces[0] = gameBoard.getPiece(i);

            for (int j = i + 1; j < 64; j++) {

                pieces[1] = gameBoard.getPiece(j);

                possible = getPossiblePieces(i, j);
                if (possible.length != 2)
                    continue;

                pieces[2] = gameBoard.getPiece(possible[0]);
                pieces[3] = gameBoard.getPiece(possible[1]);

                hasPlayer1 = false;
                hasPlayer2 = false;
                hasEmpty = false;

                for (int m = 0; m < 4; m++)
                    if (pieces[m] == null)
                        hasEmpty = true;
                    else if (pieces[m] == player1)
                        hasPlayer1 = true;
                    else
                        hasPlayer2 = true;

                if (hasPlayer1 && !hasPlayer2 && hasEmpty)
                    player1CanDoMoreSquares = true;
                else if (hasPlayer2 && !hasPlayer1 && hasEmpty)
                    player2CanDoMoreSquares = true;
                else if (!hasPlayer1 && !hasPlayer2 && hasEmpty)
                    return BOTH_PLAYERS;

                if (player1CanDoMoreSquares && player2CanDoMoreSquares)
                    return BOTH_PLAYERS;
            }
        }

        // Check if player 1 or 2 can do more squares:
        if (player1CanDoMoreSquares)
            return ONLY_PLAYER1;
        else if (player2CanDoMoreSquares)
            return ONLY_PLAYER2;

        return NO_PLAYER;
    }

}
