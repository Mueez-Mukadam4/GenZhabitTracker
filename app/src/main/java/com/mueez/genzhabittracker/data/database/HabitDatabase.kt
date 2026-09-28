
package com.mueez.genzhabittracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mueez.genzhabittracker.data.dao.HabitDao
import com.mueez.genzhabittracker.data.model.Habit

@Database(
    entities = [Habit::class],
    version = 1,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {

        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "genzhabit_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}

