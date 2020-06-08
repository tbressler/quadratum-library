package de.tbressler.quadratum.utils;

import org.junit.Test;

import java.util.Arrays;

import static de.tbressler.quadratum.utils.SquareUtils.*;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Tests for class SquareUtils.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestSquareUtils {

    @Test
    public void isSquare_withValidSquare1_returnsTrue() {
        assertTrue(isSquare(new int[]{9, 19, 24, 34}));
    }

    @Test
    public void isSquare_withValidSquare2_returnsTrue() {
        assertTrue(isSquare(new int[]{36, 39, 60, 63}));
    }

    @Test
    public void isSquare_withValidSquare3_returnsTrue() {
        assertTrue(isSquare(new int[]{1, 15, 48, 62}));
    }

    @Test
    public void isSquare_withValidSquare4_returnsTrue() {
        assertTrue(isSquare(new int[]{9, 10, 17, 18}));
    }

    @Test
    public void isSquare_withValidSquare5_returnsTrue() {
        assertTrue(isSquare(new int[]{0, 1, 8, 9}));
    }

    @Test
    public void isSquare_withValidSquare6_returnsTrue() {
        assertTrue(isSquare(new int[]{1, 8, 10, 17}));
    }

    @Test
    public void isSquare_withValidUnsortedSquare_returnsTrue() {
        assertTrue(isSquare(new int[]{34, 24, 19, 9}));
    }

    @Test
    public void isSquare_withInvalidSquare1_returnsFalse() {
        assertFalse(isSquare(new int[]{1, 2, 3, 4}));
    }

    @Test
    public void isSquare_withInvalidSquare2_returnsFalse() {
        assertFalse(isSquare(new int[]{0, 0, 0, 0}));
    }

    @Test
    public void isSquare_withInvalidSquare3_returnsFalse() {
        assertFalse(isSquare(new int[]{0, 1, 0, 0}));
    }

    @Test
    public void isSquare_withInvalidSquare4_returnsFalse() {
        assertFalse(isSquare(new int[]{15, 15, 15, 15}));
    }

    @Test
    public void getPossiblePieces_with0and1_returns8and9() {
        assertTrue(Arrays.equals(new int[]{8, 9}, getPossiblePieces(0, 1)));
    }

    @Test
    public void getPossiblePieces_with9and20_returns32and43() {
        assertTrue(Arrays.equals(new int[]{32, 43}, getPossiblePieces(9, 20)));
    }

    @Test
    public void getPossiblePieces_with62and63_returnsEmptyArray() {
        assertTrue(Arrays.equals(new int[]{}, getPossiblePieces(62, 63)));
    }

    @Test(expected = AssertionError.class)
    public void getPossiblePieces_withWrongOrder_throwsException() {
        getPossiblePieces(7, 1);
    }

    @Test
    public void score_withBigObliqueSquare8x8_returns64() {
        assertEquals(64, score(new int[] {1, 15, 48, 62}));
    }

    @Test
    public void score_withMediumSquare4x4_returns16() {
        assertEquals(16, score(new int[] {1, 4, 25, 28}));
    }

    @Test
    public void score_withMediumObliqueSquare5x5_returns25() {
        assertEquals(25, score(new int[] {27, 32, 52, 57}));
    }

    @Test
    public void score_withBigSquare6x6_returns36() {
        assertEquals(36, score(new int[] {16, 21, 56, 61}));
    }

    @Test
    public void score_withBigSquare7x7_returns49() {
        assertEquals(49, score(new int[] {8, 14, 56, 62}));
    }

    @Test
    public void score_withBigDifferentObliqueSquare8x8_returns64() {
        assertEquals(64, score(new int[] {6, 8, 55, 57}));
    }

}
