package dev.mwayne.pennydrop.game

import dev.mwayne.pennydrop.types.Player
import dev.mwayne.pennydrop.types.Slot
import kotlin.random.Random

object GameHandler {

    fun roll(players: List<Player>,
             currentPlayer: Player,
             slots: List<Slot>): Any = // TODO - this should be TurnResult instead of any
        rollDie().let { lastRoll ->
            slots.getOrNull(lastRoll - 1)?.let { slot ->
            if (slot.isFilled) {
                TurnResult( // Try adding a return statement before creating the TurnResult, I want to see if it is returned automatically
                    lastRoll,
                    coinChangeCount = slots.count { it.isFilled },
                    clearSlots = true,
                    turnEnd = TurnEnd.Bust,
                    previousPlayer = currentPlayer,
                    currentPlayer = nextPlayer(players, currentPlayer),
                    playerChanged = true,
                    canRoll = true,
                    canPass = false
                )

            } else {
                if (!currentPlayer.penniesLeft(true)) {
                    // Player wins
                } else{
                    // Game continues
                }
            }
        } ?: TurnResult(isGameOver = true)
    }

    fun pass(
        players: List<Player>,
        currentPlayer: Player
    ): TurnResult { }

    private fun rollDie(sides: Int = 6) = Random.nextInt(1, sides + 1)

    private fun nextPlayer(
        players: List<Player>,
        currentPlayer: Player): Player? {
            val currentIndex = players.indexOf(currentPlayer)
            val nextIndex = (currentIndex + 1) % players.size

            return players[nextIndex]
    }

}