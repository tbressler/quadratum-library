package de.tbressler.quadratum.utils;

/**
 * Utils for the game board.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class GameBoardUtils {


    /* Private constructor. */
    private GameBoardUtils() {}


    /**
     * Converts the given x-y coordinates to the corresponding index.
     *
     * @param x The x coordinate, between 0..7.
     * @param y The y coordinate, between 0..7.
     * @return The corresponding index, between 0..63.
     */
    public static int toIndex(int x, int y) {
        if ((x < 0) || (x > 7)) throw new AssertionError("x must be between 0..7!");
        if ((y < 0) || (y > 7)) throw new AssertionError("y must be between 0..7!");
        return (y * 8) + x;
    }

    /**
     * Converts the given index to the corresponding x-y coordinates.
     *
     * @param index The index, between 0..63.
     * @return The corresponding x and y coordinates in the form {x, y}.
     */
    public static int[] toCoords(int index) {
        assertIndex(index, "index must be between 0..63!");
        return new int[]{(index % 8), (index - (index % 8)) / 8};
    }

    /**
     * Returns the difference of the x coordinates.
     *
     * @param index1 The index of the first piece, between 0..63.
     * @param index2 The index of the second piece, between 0..63.
     * @return The difference of the x coordinates, between 0..7.
     */
    public static int difX(int index1, int index2) {
        assertIndex(index1, "index1 must be between 0..63!");
        assertIndex(index2, "index2 must be between 0..63!");
        int[] c1 = toCoords(index1);
        int[] c2 = toCoords(index2);
        return c2[0] - c1[0];
    }

    /**
     * Returns the difference of the y coordinates.
     *
     * @param index1 The index of the first piece, between 0..63.
     * @param index2 The index of the second piece, between 0..63.
     * @return The difference of the x coordinates, between 0..7.
     */
    public static int difY(int index1, int index2) {
        assertIndex(index1, "index1 must be between 0..63!");
        assertIndex(index2, "index2 must be between 0..63!");
        int[] c1 = toCoords(index1);
        int[] c2 = toCoords(index2);
        return c2[1] - c1[1];
    }

    /**
     * Asserts if index is between 0 and 63.
     *
     * @param index The index.
     * @param msg The error message.
     */
    public static void assertIndex(int index, String msg) {
        if ((index < 0) || (index > 63)) throw new AssertionError(msg);
    }

}
