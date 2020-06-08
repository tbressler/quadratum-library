package de.tbressler.quadratum.model;

import de.tbressler.quadratum.utils.SquareUtils;

import java.util.Arrays;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.MoreObjects.toStringHelper;
import static de.tbressler.quadratum.utils.SquareUtils.score;
import static java.util.Arrays.sort;
import static java.util.Objects.requireNonNull;

/**
 * A square, which consists of 4 pieces of one player.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class Square {

    /* The pieces of the square. */
    private final int[] pieces;

    /* The score of the square. */
    private final int score;

    /* The player that scored the square. */
    private final Player player;


    /**
     * Creates a square.
     *
     * @param pieces The 4 pieces of the square, must not be null or empty.
     * @param player  The player that scored this square, must not be null.
     */
    public Square(int[] pieces, Player player) {
        if (requireNonNull(pieces).length != 4)
            throw new AssertionError("pieces array must contain 4 elements!");
        if (!SquareUtils.isSquare(pieces))
            throw new AssertionError("pieces must form a square!");

        sort(pieces);
        this.pieces = pieces;
        this.score = score(pieces);

        this.player = requireNonNull(player);
    }


    /**
     * Returns the field indexes of the pieces as a sorted array.
     *
     * @return The pieces as sorted array, never null.
     */
    public int[] getSortedPieces() {
        return pieces;
    }


    /**
     * Returns the score of the square.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }


    /**
     * Returns the player who scored the square.
     *
     * @return The player, never null.
     */
    public Player getPlayer() {
        return player;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Square square = (Square) o;

        if (!player.equals(square.player)) return false;

        return Arrays.equals(pieces, square.pieces);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(pieces);
        result = 31 * result + player.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return toStringHelper(this)
                .add("player", player)
                .add("pieces", on(",").join(pieces[0],pieces[1],pieces[2],pieces[3]))
                .add("score", score)
                .toString();
    }

}
