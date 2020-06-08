package de.tbressler.quadratum.model;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

/**
 * A player.
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class Player {

    /* The name of the player. */
    private final String name;


    /**
     * Creates a player.
     *
     * @param name The name of the player, must not be null.
     */
    public Player(String name) {
        this.name = requireNonNull(name);
    }


    /**
     * Returns the name of the player.
     *
     * @return The name of the player, never null.
     */
    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .toString();
    }

}
