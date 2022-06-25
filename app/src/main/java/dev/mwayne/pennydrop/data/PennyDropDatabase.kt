package dev.mwayne.pennydrop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.mwayne.pennydrop.types.Player
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Game::class, Player::class, GameStatus::class],
    version = 1,
    exportSchema = false
)

abstract class PennyDropDatabase: RoomDatabase() {
    abstract fun pennyDropDao(): PennyDropDao

    companion object {
        @Volatile
        private var instance: PennyDropDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope):
                PennyDropDatabase = this.instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                PennyDropDatabase::class.java,
                "PennyDropDatabase"
            ).build()
            this.instance = instance

            instance // This is returned from the synchronized block
        }
    }
}