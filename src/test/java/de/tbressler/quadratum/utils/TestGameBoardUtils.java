package de.tbressler.quadratum.utils;

import org.junit.Test;

import java.util.Arrays;

import static de.tbressler.quadratum.utils.GameBoardUtils.*;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Tests for class GameBoardUtils.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class TestGameBoardUtils {

    @Test
    public void toIndex_withX0AndY0_returns0() {
        assertEquals(0, toIndex(0, 0));
    }

    @Test
    public void toIndex_withX7AndY7_return63() {
        assertEquals(63, toIndex(7, 7));
    }

    @Test
    public void toIndex_withX3AndY4_returns35() {
        assertEquals(35, toIndex(3, 4));
    }

    @Test
    public void toIndex_withX4AndY3_returns28() {
        assertEquals(28, toIndex(4, 3));
    }

    @Test(expected = AssertionError.class)
    public void toIndex_withXLowerThan0_throwsException() {
        toIndex(-1, 3);
    }

    @Test(expected = AssertionError.class)
    public void toIndex_withXGreaterThan7_throwsException() {
        toIndex(8, 3);
    }

    @Test(expected = AssertionError.class)
    public void toIndex_withYLowerThan0_throwsException() {
        toIndex(3, -1);
    }

    @Test(expected = AssertionError.class)
    public void toIndex_withYGreaterThan7_throwsException() {
        toIndex(3, 8);
    }

    @Test
    public void toCoords_with0_returns0and0() {
        assertTrue(Arrays.equals(new int[]{0, 0}, toCoords(0)));
    }

    @Test
    public void toCoords_with28_returns4and3() {
        assertTrue(Arrays.equals(new int[]{4, 3}, toCoords(28)));
    }

    @Test
    public void toCoords_with35_returns3and4() {
        assertTrue(Arrays.equals(new int[]{3, 4}, toCoords(35)));
    }

    @Test
    public void toCoords_with63_return7and7() {
        assertTrue(Arrays.equals(new int[]{7, 7}, toCoords(63)));
    }

    @Test(expected = AssertionError.class)
    public void toCoords_withLowerThan0_throwsException() {
        toCoords(-1);
    }

    @Test(expected = AssertionError.class)
    public void toCoords_withGreaterThan63_throwsException() {
        toCoords(64);
    }

    @Test
    public void difX_withSameIndexes_returns0() {
        assertEquals(0, difX(9, 9));
    }

    @Test
    public void difX_with9And10_returns1() {
        assertEquals(1, difX(9, 10));
    }

    @Test
    public void difX_with9And20_returns3() {
        assertEquals(3, difX(9, 20));
    }

    @Test
    public void difX_with9And17_returns0() {
        assertEquals(0, difX(9, 17));
    }

    @Test
    public void difY_with9and10_returns0() {
        assertEquals(0, difY(9, 10));
    }

    @Test
    public void difY_with9And17_returns1() {
        assertEquals(1, difY(9, 17));
    }

    @Test
    public void difY_with9And36_returns3() {
        assertEquals(3, difY(9, 36));
    }

}
