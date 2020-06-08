package de.tbressler.quadratum.logic.players;

import de.tbressler.quadratum.logic.ILogicCallback;
import de.tbressler.quadratum.model.IReadOnlyGameBoard;
import de.tbressler.quadratum.model.Player;

import java.util.Random;

import static com.google.common.base.MoreObjects.toStringHelper;
import static de.tbressler.quadratum.utils.GameBoardUtils.assertIndex;
import static de.tbressler.quadratum.utils.SquareUtils.getPossiblePieces;
import static de.tbressler.quadratum.utils.SquareUtils.score;
import static java.util.Objects.requireNonNull;

/**
 * The implementation of the player logic interface for artificial intelligence players (bots).
 *
 * @author Tobias Bressler
 * @version 1.0
 */
public class BotPlayerLogic extends AbstractPlayerLogic {

    /** Enum for different bot strategies. */
    enum Strategy {
        /** Finds best index by adding all square scores to heat map. */
        LONG_TERM,
        /** Finds best index by using best square score for heat map. */
        SHORT_TERM
    }


    /* The strategy that should be used by the bot. */
    private final Strategy strategy;

    /* True if the moves of the player should be randomized. */
    private boolean randomizeMoves = true;

    /* Random number generator. */
    private Random random = new Random();


    /**
     * Creates the bot player logic.
     *
     * @param player The player, must not be null.
     * @param strategy The strategy, must not be null.
     */
    public BotPlayerLogic(Player player, Strategy strategy) {
        super(player);
        this.strategy = requireNonNull(strategy);
    }


    /**
     * Enables or disables randomization of moves. If randomization is enabled, the bot makes a random
     * decision which field he uses for his next move if the chance for a good score is the same.
     *
     * @param randomizeMoves True if moves should be randomized.
     */
    public void setRandomizeMoves(boolean randomizeMoves) {
        this.randomizeMoves = randomizeMoves;
    }


    /**
     * Sets the random number generator. This method should only be used for testing purposes.
     *
     * @param random The random number generator, must not be null.
     */
    void setRandom(Random random) {
        this.random = requireNonNull(random);
    }


    @Override
    public void requestMove(IReadOnlyGameBoard gameBoard, ILogicCallback callback) {
        requireNonNull(gameBoard);
        requireNonNull(callback);

        int[] playerHeatMap = new int[64];
        int[] opponentHeatMap = new int[64];

        Player[] pieces = new Player[4];
        int[] possible;

        int scoreForSquare;
        int playerScore;
        int opponentScore;
        int numberOfPlayerPieces;
        int numberOfOpponentPieces;

        // Create heat maps for player and opponent:
        for (int i = 0; i < 55; i++) {
            pieces[0] = gameBoard.getPiece(i);
            for (int j = i + 1; j < 64; j++) {
                pieces[1] = gameBoard.getPiece(j);

                possible = getPossiblePieces(i, j);
                if (possible.length != 2)
                    continue;

                pieces[2] = gameBoard.getPiece(possible[0]);
                pieces[3] = gameBoard.getPiece(possible[1]);

                numberOfPlayerPieces = 0;
                numberOfOpponentPieces = 0;

                for (int p = 0; p < 4; p++)
                    if (pieces[p] == getPlayer())
                        numberOfPlayerPieces++;
                    else if (pieces[p] != null)
                        numberOfOpponentPieces++;

                // Calculate possible score of square:
                scoreForSquare = score(i, j, possible[0], possible[1]);

                if ((numberOfOpponentPieces > 0) && (numberOfPlayerPieces == 0)) {
                    // ... square is not occupied by opponent and not yet blocked by player.

                    // Calculate chance for opponent to get this square.
                    opponentScore = scoreForSquare * (numberOfOpponentPieces+1);

                    // Update opponent heat map:
                    updateHeatMap(opponentHeatMap, i, opponentScore);
                    updateHeatMap(opponentHeatMap, j, opponentScore);
                    updateHeatMap(opponentHeatMap, possible[0], opponentScore);
                    updateHeatMap(opponentHeatMap, possible[1], opponentScore);

                } else if (numberOfOpponentPieces == 0) {
                    // ... square is not blocked by opponent.

                    // Calculate chance for player to get this square.
                    playerScore = scoreForSquare * (numberOfPlayerPieces+1);

                    // Update player heat map:
                    updateHeatMap(playerHeatMap, i, playerScore);
                    updateHeatMap(playerHeatMap, j, playerScore);
                    updateHeatMap(playerHeatMap, possible[0], playerScore);
                    updateHeatMap(playerHeatMap, possible[1], playerScore);

                }
            }
        }

        int value;
        int maxValue = -1;
        int indexWithMaxValue = -1;

        // Analyze heat map:
        for(int i = 0; i < 64; i++) {

            // Skip if field is not empty.
            if (!gameBoard.isFieldEmpty(i))
                continue;

            // Check chances to score:
            if (playerHeatMap[i] >= opponentHeatMap[i]) {
                // ... the chance for a player score is higher or equal.
                value = playerHeatMap[i];
            } else {
                // ... the chance for a opponent score is higher.
                value = opponentHeatMap[i];
            }

            // Check if chance is higher:
            if ((value > maxValue) ||
                    ((value == maxValue) && doRandomization())) {
                maxValue = value;
                indexWithMaxValue = i;
            }
        }

        assertIndex(indexWithMaxValue, "Bot logic error! Invalid field index.");

        callback.makeMove(indexWithMaxValue, getPlayer());
    }

    /* Updates the heat map at the given index with the score. */
    private void updateHeatMap(int[] heatMap, int index, int score) {
        heatMap[index] = calculateNewScore(heatMap[index], score);
    }

    /* Calculates the new score for the heat map.*/
    private int calculateNewScore(int heatMapValue, int currentScore) {
        switch (strategy) {
            case LONG_TERM:
                return heatMapValue + currentScore;
            case SHORT_TERM:
                return (currentScore > heatMapValue) ? currentScore : heatMapValue;
            default:
                throw new IllegalStateException("Unknown strategy!");
        }
    }

    /* Returns true, if the values should be randomized. */
    private boolean doRandomization() {
        return randomizeMoves && random.nextBoolean();
    }


    @Override
    public String toString() {
        return toStringHelper(this)
                .add("strategy", strategy)
                .add("randomizeMoves", randomizeMoves)
                .toString();
    }

}
