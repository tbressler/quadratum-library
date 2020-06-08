package de.tbressler.quadratum.logic;

import de.tbressler.quadratum.model.Player;

/**
 * This interface is used as a callback between the game logic and the player logic.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public interface ILogicCallback {

    /**
     * Make the move on the game board.
     *
     * @param index The field index, between 0..63.
     * @param player The player, must not be null.
     * @return True if the move was successful or false if not.
     */
    boolean makeMove(int index, Player player);

}
