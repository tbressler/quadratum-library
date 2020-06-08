package de.tbressler.quadratum.utils;

import static de.tbressler.quadratum.utils.GameBoardUtils.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.sort;

/**
 * Utility class that helps calculating squares.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class SquareUtils {

    /* Internal constant for an empty array. */
    private static final int[] EMPTY_ARRAY = new int[0];


    /* Private constructor. */
    private SquareUtils() {}


    /**
     * Checks if the given indexes are forming a valid square.
     *
     * @param index1 The first index, between 0..63.
     * @param index2 The second index, between 0..63.
     * @param index3 The third index, between 0..63.
     * @param index4 The fourth index, between 0..63.
     * @return True if the pieces are forming a valid square or false.
     */
    public static boolean isSquare(int index1, int index2, int index3, int index4) {
        return isSquare(new int[]{index1, index2, index3, index4});
    }

    /**
     * Checks if the given indexes are forming a valid square.
     *
     * @param pieces The array with the 4 indexes of the edges of the square.
     * @return True if the pieces are forming a valid square or false.
     */
    public static boolean isSquare(int[] pieces) {
        sort(pieces);

        int[] possiblePieces = getPossiblePieces(pieces[0], pieces[1]);

        if (possiblePieces.length == 0) return false;

        // Check possible pieces with given indexes:
        return (possiblePieces[0] == pieces[2]) && (possiblePieces[1] == pieces[3])
                || (possiblePieces[1] == pieces[2]) && (possiblePieces[0] == pieces[3]);
    }

    /**
     * Returns an array with the two possible pieces that are forming a square with
     * the two given pieces. If the possible pieces are out of range, an empty array is
     * returned.
     *
     * @param index1 The index of the first piece (must be lower than index2).
     * @param index2 The index of the second piece (must be greater than index1).
     * @return An array with the two possible pieces or an empty array.
     */
    public static int[] getPossiblePieces(int index1, int index2) {
        if (index1 > index2) throw new AssertionError("index1 must be lower than index2!");

        // Calculate x and y difference and possible pieces.
        int dx = difX(index1, index2);
        int dy = difY(index1, index2);

        // Check if x and y difference > 0.
        if ((dx == 0) && (dy == 0)) return EMPTY_ARRAY;

        // Translate index of first two pieces to coords:
        int[] piece1 = toCoords(index1);
        int[] piece2 = toCoords(index2);

        int[] piece3 = new int[]{piece1[0] - ((dx > 0) ? dy : -dy), piece1[1] + ((dx > 0) ? dx : -dx)};
        int[] piece4 = new int[]{piece2[0] - ((dx > 0) ? dy : -dy), piece2[1] + ((dx > 0) ? dx : -dx)};

        // Check if pieces are in range:
        if ((piece3[0] < 0) || (piece3[0] > 7) || (piece3[1] < 0) || (piece3[1] > 7) ||
                (piece4[0] < 0) || (piece4[0] > 7) || (piece4[1] < 0) || (piece4[1] > 7)) return EMPTY_ARRAY;

        // Calculate index of possible pieces:
        return new int[]{toIndex(piece3[0], piece3[1]), toIndex(piece4[0], piece4[1])};
    }


    /**
     * Returns the score for the given square.
     *
     * @param pieces The array with the 4 indexes of the edges of the square.
     * @return The score for the square, between 1..64.
     */
    public static int score(int[] pieces) {
        return score(pieces[0], pieces[1], pieces[2], pieces[3]);
    }

    /**
     * Returns the score for the given square.
     *
     * @param index1 The first index, between 0..63.
     * @param index2 The second index, between 0..63.
     * @param index3 The third index, between 0..63.
     * @param index4 The fourth index, between 0..63.
     * @return The score for the square, between 1..64.
     */
    public static int score(int index1, int index2, int index3, int index4) {
        int minIndex = min(min(index1, index2), min(index3, index4));
        int maxIndex = max(max(index1, index2), max(index3, index4));
        int dx = difY(minIndex, maxIndex) + 1;
        return dx * dx;
    }

}
