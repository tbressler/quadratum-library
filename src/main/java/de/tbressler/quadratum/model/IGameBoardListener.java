package de.tbressler.quadratum.model;

/**
 * A listener for the game board.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public interface IGameBoardListener {

    /**
     * This method is called, if a piece was placed on the game board.
     *
     * @param index The index where the piece was placed.
     * @param player The player who has placed the piece.
     */
    void onPiecePlaced(int index, Player player);

    /**
     * This method is called, if the game board was cleared.
     */
    void onGameBoardCleared();

}
