package de.tbressler.quadratum.logic.players;

import de.tbressler.quadratum.logic.IPlayerLogic;
import de.tbressler.quadratum.model.Player;

import static java.util.Objects.requireNonNull;

/**
 * An abstract implementation for the player logic interface.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public abstract class AbstractPlayerLogic implements IPlayerLogic {

    /* The player. */
    private final Player player;


    /**
     * Creates the abstract player logic.
     *
     * @param player The player, must not be null.
     */
    public AbstractPlayerLogic(Player player) {
        this.player = requireNonNull(player);
    }


    @Override
    public Player getPlayer() {
        return player;
    }

}
