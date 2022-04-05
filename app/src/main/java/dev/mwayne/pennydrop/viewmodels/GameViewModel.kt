package dev.mwayne.pennydrop.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.mwayne.pennydrop.types.Player

class GameViewModel: ViewModel() {
    private var players: List<Player> = emptyList()

    fun startGame(playersForNewGame: List<Player>) {
        this.players = playersForNewGame
        // More logic will be added here later
    }
}