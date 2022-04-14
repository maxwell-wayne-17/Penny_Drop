package dev.mwayne.pennydrop.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.mwayne.pennydrop.game.GameHandler
import dev.mwayne.pennydrop.game.TurnResult
import dev.mwayne.pennydrop.types.Player
import dev.mwayne.pennydrop.types.Slot
import dev.mwayne.pennydrop.types.clear

class GameViewModel: ViewModel() {
    private var players: List<Player> = emptyList()

    val slots = MutableLiveData(
        (1..6).map { slotNum -> Slot(slotNum, slotNum != 6) }
    )

    val currentPlayer = MutableLiveData<Player?>()

    val canRoll = MutableLiveData(false)
    val canPass = MutableLiveData(false)

    val currentTurnText = MutableLiveData("")
    val currentStandingsText = MutableLiveData("")

    fun startGame(playersForNewGame: List<Player>) {
        this.players = playersForNewGame
        this.currentPlayer.value = this.players.firstOrNull().apply {
                this?.isRolling = true
            }
        this.canRoll.value = true
        this.canPass.value = false

        slots.value?.clear()
        slots.notifyChange()

        currentTurnText.value = "The game has begun!\n"
        currentStandingsText.value = generateCurrentStandings(this.players)
    }

    fun roll() {
        slots.value?.let { currentSlots ->
            // Comparing against true saves us a null check
            val currentPlayer = players.firstOrNull { it.isRolling }
            if (currentPlayer != null && canRoll.value == true) {
                updateFromGameHandler(
                    GameHandler.roll(players, currentPlayer, currentSlots)
                )
            }
        }
    }

    fun pass() {
        val currentPlayer = players.firstOrNull { it.isRolling }
        if (currentPlayer != null && canPass.value == true) {
            updateFromGameHandler(GameHandler.pass(players, currentPlayer))
        }
    }

    fun updateFromGameHandler(turnResult: TurnResult) {

    }

    // Mutable live data will only notify a change if the value of it is changed, not if something
    // WITHIN the value is changed.  This effectively sends a notification without changing the value,
    // intended to be used after we change something INSIDE the value and need UI update
    private fun <T> MutableLiveData<List<T>>.notifyChange() {
        this.value = this.value
    }

    private fun generateCurrentStandings(players: List<Player>,
                                         headerText: String = "Current Standings:") =
        players.sortedBy { it.pennies }.joinToString( separator = "\n", prefix = "$headerText\n") {
            "\t${it.playerName} - ${it.pennies} pennies"
        }
}