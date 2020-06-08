package de.tbressler.quadratum.logic;

import de.tbressler.quadratum.model.IReadOnlyGameBoard;
import de.tbressler.quadratum.model.Player;
import de.tbressler.quadratum.model.Square;

import java.util.HashSet;
import java.util.Set;

import static de.tbressler.quadratum.utils.SquareUtils.getPossiblePieces;
import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;

/**
 * Detects and manages squares on the game board.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class SquareCollector {

    /* A set of squares found by this detector. */
    private final Set<Square> squares = new HashSet<>();


    /**
     * Detect new squares of the given player on the game board.
     *
     * @param gameBoard The game board, must not be null.
     * @param player The player, must not be null.
     * @return A set of the new detected squares for the player, never null.
     */
    public Set<Square> detect(IReadOnlyGameBoard gameBoard, Player player) {

        Set<Square> foundSquares = new HashSet<>();

        findNewSquares(requireNonNull(gameBoard), requireNonNull(player), foundSquares);

        if (foundSquares.isEmpty())
            return emptySet();

        squares.addAll(foundSquares);

        return foundSquares;
    }

    /* Finds new squares for the given player. */
    private void findNewSquares(IReadOnlyGameBoard gameBoard, Player player, Set<Square> found) {
        int[] possible;
        Square square;

        for (int i = 0; i < 55; i++) {

            // Skip if field is empty or piece is not from given player
            if (gameBoard.getPiece(i) != player)
                continue;

            for (int j = i + 1; j < 64; j++) {

                // Skip if field is empty or piece is not from given player
                if (gameBoard.getPiece(j) != player)
                    continue;

                possible = getPossiblePieces(i, j);

                if (possible.length != 2) continue;

                // Check for possible square edges.
                if ((gameBoard.getPiece(possible[0]) == player) &&
                        (gameBoard.getPiece(possible[1]) == player)) {

                    square = new Square(new int[]{i,j,possible[0],possible[1]}, player);

                    // Skip if square is well-known
                    if (squares.contains(square))
                        continue;

                    found.add(square);
                }
            }
        }
    }


    /**
     * Returns all the squares found by this detector.
     *
     * @return A set of the squares, never null.
     */
    public Set<Square> getDetectedSquares() {
        return squares;
    }


    /**
     * Returns the current number of squares for the given player.
     *
     * @param player The player, must not be null.
     * @return The current number of squares
     */
    public int getSquareCount(Player player) {
        requireNonNull(player);
        int count = 0;
        for (Square square : squares)
            if (square.getPlayer() == player)
                count++;
        return count;
    }


    /**
     * Returns the current score for the given player.
     *
     * @param player The player, must not be null.
     * @return The current score of the player
     */
    public int getScore(Player player) {
        requireNonNull(player);
        int score = 0;
        for (Square square : squares)
            if (square.getPlayer() == player)
                score += square.getScore();
        return score;
    }


    /**
     * Resets the square detector and clears all found squares.
     */
    public void reset() {
        squares.clear();
    }

}
