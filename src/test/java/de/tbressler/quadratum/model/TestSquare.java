package de.tbressler.quadratum.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for the class Square.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestSquare {

    // Class under test:
    private Square square;

    // Mocks:
    private Player player;


    @Before
    public void setUp() {
        player = mock(Player.class, "player");
    }


    @Test(expected = NullPointerException.class)
    public void new_withNullPieces_throwsException() {
        new Square(null, player);
    }

    @Test(expected = AssertionError.class)
    public void new_withEmptyPieces_throwsException() {
        new Square(new int[0], player);
    }

    @Test(expected = AssertionError.class)
    public void new_withLessThan4Pieces_throwsException() {
        new Square(new int[3], player);
    }

    @Test(expected = AssertionError.class)
    public void new_withMoreThan4Pieces_throwsException() {
        new Square(new int[5], player);
    }

    @Test(expected = NullPointerException.class)
    public void new_withNullPlayer_throwsException() {
        new Square(new int[]{0, 1, 8, 9}, null);
    }

    @Test(expected = AssertionError.class)
    public void new_withInvalidSquare_throwsException() {
        new Square(new int[]{0, 1, 2, 3}, player);
    }


    @Test
    public void getSortedPieces_withSortedPieces_returnsSortedPieces() {
        Square square = new Square(new int[]{0, 1, 8, 9}, player);
        assertTrue(Arrays.equals(square.getSortedPieces(), new int[]{0, 1, 8, 9}));
    }

    @Test
    public void getSortedPieces_withUnsortedPieces_returnsSortedPieces() {
        Square square = new Square(new int[]{9, 0, 8, 1}, player);
        assertTrue(Arrays.equals(square.getSortedPieces(), new int[]{0, 1, 8, 9}));
    }


    @Test
    public void getPlayer_returnsPlayer() {
        Square square = new Square(new int[]{9, 0, 8, 1}, player);
        assertEquals(player, square.getPlayer());
    }


    @Test
    public void getScore_withObliqueSquare_returns9() {
        Square square = new Square(new int[]{9, 19, 24, 34}, player);
        assertEquals(16, square.getScore());
    }

    @Test
    public void getScore_withNormalSquare_returns9() {
        Square square = new Square(new int[]{36, 39, 60, 63}, player);
        assertEquals(16, square.getScore());
    }

    @Test
    public void getScore_withSmallestSquare_returns1() {
        Square square = new Square(new int[]{0, 1, 8, 9}, player);
        assertEquals(4, square.getScore());
    }

    @Test
    public void getScore_withBiggestSquare_returns49() {
        Square square = new Square(new int[]{0, 7, 56, 63}, player);
        assertEquals(64, square.getScore());
    }

    @Test
    public void getScore_withBiggestObliqueSquare_returns49() {
        Square square = new Square(new int[]{1, 15, 48, 62}, player);
        assertEquals(64, square.getScore());
    }

    @Test
    public void getScore_withSmallestObliqueSquare_returns4() {
        Square square = new Square(new int[]{1, 8, 10, 17}, player);
        assertEquals(9, square.getScore());
    }


    @Test
    public void equals_withEqualSquares_returnsTrue() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 8, 10, 17}, player);
        assertTrue(square1.equals(square2));
    }

    @Test
    public void equals_withUnequalPieces_returnsFalse() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 15, 48, 62}, player);
        assertFalse(square1.equals(square2));
    }

    @Test
    public void equals_withUnequalPiecesAndUnequalPlayer_returnsFalse() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 15, 48, 62}, mock(Player.class, "player2"));
        assertFalse(square1.equals(square2));
    }

    @Test
    public void equals_withUnequalPlayer_returnsFalse() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 8, 10, 17}, mock(Player.class, "player2"));
        assertFalse(square1.equals(square2));
    }

    @Test
    public void equals_withEqualSquaresButUnsorted_returnsTrue() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{8, 1, 17, 10}, player);
        assertTrue(square1.equals(square2));
    }


    @Test
    public void hashCode_withEqualSquares_returnsEqualHashCode() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 8, 10, 17}, player);
        assertEquals(square1.hashCode(), square2.hashCode());
    }

    @Test
    public void hash_withUnequalPieces_returnsUnequalHashCode() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 15, 48, 62}, player);
        assertNotSame(square1.hashCode(), square2.hashCode());
    }

    @Test
    public void hashCode_withUnequalPiecesAndUnequalPlayer_returnsUnequalHashCode() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 15, 48, 62}, mock(Player.class, "player2"));
        assertNotSame(square1.hashCode(), square2.hashCode());
    }

    @Test
    public void hashCode_withUnequalPlayer_returnsUnequalHashCode() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{1, 8, 10, 17}, mock(Player.class, "player2"));
        assertNotSame(square1.hashCode(), square2.hashCode());
    }

    @Test
    public void hashCode_withEqualSquaresButUnsorted_returnsEqualHashCode() {
        Square square1 = new Square(new int[]{1, 8, 10, 17}, player);
        Square square2 = new Square(new int[]{8, 1, 17, 10}, player);
        assertEquals(square1.hashCode(), square2.hashCode());
    }

}
