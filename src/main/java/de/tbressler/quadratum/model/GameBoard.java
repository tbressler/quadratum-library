package de.tbressler.quadratum.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;
import static de.tbressler.quadratum.utils.GameBoardUtils.assertIndex;
import static java.util.Objects.requireNonNull;

/**
 * A game board (without any game logic).
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class GameBoard implements IReadOnlyGameBoard {

    /* Player one. */
    private final Player player1;

    /* Player two. */
    private final Player player2;

    /* The fields of the game board. */
    private int[] board = new int[64];

    /* The game board listeners. */
    private List<IGameBoardListener> listeners = new ArrayList<>();


    /**
     * Creates a game board with the two given players.
     *
     * @param player1 Player one, must not be null.
     * @param player2 Player two, must not be null or equal to player one.
     */
    public GameBoard(Player player1, Player player2) {
        if (Objects.equals(player1, player2))
            throw new AssertionError("player1 must not be equal to player2!");
        this.player1 = requireNonNull(player1);
        this.player2 = requireNonNull(player2);
    }


    @Override
    public Player getPlayer1() {
        return player1;
    }


    @Override
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Clears the game board.
     */
    public void clear() {
        for (int i = 0; i < 64; i++)
            board[i] = 0;
        fireOnGameBoardCleared();
    }

    /* Notifies listeners that the game board was cleared. */
    private void fireOnGameBoardCleared() {
        for(IGameBoardListener listener : listeners)
            listener.onGameBoardCleared();
    }


    /**
     * Place a piece on the game board.
     *
     * @param index The field index, between 0 and 63.
     * @param player The player, must not be null.
     */
    public void placePiece(int index, Player player) {
        checkPlacePiecePrecondition(index, player);
        board[index] = (player.equals(player1)) ? 1 : 2;
        fireOnPiecePlaced(index, player);
    }

    /* Checks the preconditions for placing a piece on the game board. */
    private void checkPlacePiecePrecondition(int index, Player player) {
        checkFieldIndex(index);
        if (!(requireNonNull(player).equals(player1) ||
                requireNonNull(player).equals(player2)))
            throw new AssertionError("Player is unknown at the game board!");
        if (!isFieldEmpty(index))
            throw new AssertionError("The given field index is not empty!");
    }

    /* Notifies all listeners that a piece was placed on the game board. */
    private void fireOnPiecePlaced(int index, Player player) {
        for(IGameBoardListener listener : listeners)
            listener.onPiecePlaced(index, player);
    }


    @Override
    public boolean isFieldEmpty(int index) {
        checkFieldIndex(index);
        return (board[index] == 0);
    }

    /* Checks if index is in range. */
    private void checkFieldIndex(int index) {
        assertIndex(index, "Index must be between 0 and 63!");
    }


    @Override
    public Player getPiece(int index) {
        checkFieldIndex(index);
        if (board[index] == 0)
            return null;
        return (board[index] == 1) ? player1 : player2;
    }


    @Override
    public void addGameBoardListener(IGameBoardListener listener) {
        listeners.add(requireNonNull(listener));
    }

    @Override
    public void removeGameBoardListener(IGameBoardListener listener) {
        listeners.remove(requireNonNull(listener));
    }


    @Override
    public String toString() {
        return toStringHelper(this)
                .add("player1", player1)
                .add("player2", player2)
                .toString();
    }

}
