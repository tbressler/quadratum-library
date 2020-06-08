package de.tbressler.quadratum.model;

/**
 * Interface for the game board. This interface is used for readonly-access to the game board.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public interface IReadOnlyGameBoard {

    /**
     * Returns the first player.
     *
     * @return The first player, never null.
     */
    Player getPlayer1();

    /**
     * Returns the second player.
     *
     * @return The second player, never null.
     */
    Player getPlayer2();

    /**
     * Returns true if the field is empty. Otherwise this method returns false.
     *
     * @param index The field index, between 0 and 63.
     * @return True if the field is empty or false.
     */
    boolean isFieldEmpty(int index);

    /**
     * Returns the player who placed the piece on the game board or null if no piece was placed
     * on the given field.
     *
     * @param index The field index, between 0 and 63.
     * @return The player who placed the piece or null if no piece was placed.
     */
    Player getPiece(int index);

    /**
     * Adds a listener to the game board.
     *
     * @param listener The listener, must not be null.
     */
    void addGameBoardListener(IGameBoardListener listener);

    /**
     * Removes a listener from the game board.
     *
     * @param listener The listener, must not be null.
     */
    void removeGameBoardListener(IGameBoardListener listener);

}
