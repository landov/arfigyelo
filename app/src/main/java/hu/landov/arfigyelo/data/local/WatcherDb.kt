package hu.landov.arfigyelo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.landov.arfigyelo.data.domain.Favorite

@Database(
    entities = [
        LocalCategory::class,
        LocalProduct::class,
        LocalPrice::class,
        Favorite::class
    ], version = 1
)
abstract class WatcherDb : RoomDatabase() {
    abstract val dao: WatcherDao
}
