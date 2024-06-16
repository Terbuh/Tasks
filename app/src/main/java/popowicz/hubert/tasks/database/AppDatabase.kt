package popowicz.hubert.tasks.database

import androidx.room.Database
import androidx.room.RoomDatabase
import popowicz.hubert.tasks.model.Task


@Database(entities = [Task::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}