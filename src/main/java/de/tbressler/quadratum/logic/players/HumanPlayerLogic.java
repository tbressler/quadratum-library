package de.tbressler.quadratum.logic.players;

import de.tbressler.quadratum.logic.ILogicCallback;
import de.tbressler.quadratum.model.IReadOnlyGameBoard;
import de.tbressler.quadratum.model.Player;

import java.util.concurrent.locks.ReentrantLock;

import static de.tbressler.quadratum.utils.GameBoardUtils.assertIndex;
import static java.util.Objects.requireNonNull;

/**
 * The player logic for a human player. This logic can be used by the user interface of the
 * application, which accepts the user input.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class HumanPlayerLogic extends AbstractPlayerLogic {

    /* True if the player is active. */
    private boolean isPlayerActive = false;

    /* Lock. */
    private final ReentrantLock lock = new ReentrantLock(true);

    /* The logic callback. */
    private ILogicCallback logicCallback = null;


    /**
     * Creates the human player logic.
     *
     * @param player The player, must not be null.
     */
    public HumanPlayerLogic(Player player) {
        super(player);
    }

    @Override
    public void requestMove(IReadOnlyGameBoard gameBoard, ILogicCallback callback) {
        try {
            lock.lock();

            this.logicCallback = requireNonNull(callback);

            isPlayerActive = true;

        } finally {
            lock.unlock();
        }
    }


    /**
     * Returns true if the player is active or false if the opponent is active.
     *
     * @return True if the player is active.
     */
    public boolean isPlayerActive() {
        return isPlayerActive;
    }


    /**
     * Place a piece on the game board.
     *
     * @param index The field index, between 0..63.
     * @return True if the piece was placed successfully.
     */
    public boolean placePiece(int index) {
        assertIndex(index, "Index must be between 0 and 63!");
        try {
            lock.lock();

            if (!isPlayerActive)
                return false;

            isPlayerActive = false;

            return logicCallback.makeMove(index, getPlayer());

        } finally {
            lock.unlock();
        }
    }

}
