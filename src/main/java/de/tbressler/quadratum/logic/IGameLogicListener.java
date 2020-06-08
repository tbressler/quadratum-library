package de.tbressler.quadratum.logic;

import de.tbressler.quadratum.model.Player;
import de.tbressler.quadratum.model.Square;

import java.util.Set;

/**
 * A listener for the game logic.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public interface IGameLogicListener {

    /**
     * Method is called when the game is over. The given parameter winner indicates which player
     * won the game. If the parameter is null, the game is a draw.
     *
     * @param winner The winner of the game or null if the game is a draw.
     */
    void onGameOver(Player winner);

    /**
     * Method is called when new squares were found.
     *
     * @param player The player, never null.
     * @param squares The new squares that were found, never null.
     */
    void onNewSquaresFound(Player player, Set<Square> squares);

    /**
     * Method is called when the active player changed.
     *
     * @param activePlayer The active player, never null.
     */
    void onActivePlayerChanged(Player activePlayer);

    /**
     * Method is called when a game was started.
     *
     * @param activePlayer The active player, never null.
     */
    void onGameStarted(Player activePlayer);

}
