
package com.mueez.genzhabittracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

import com.mueez.genzhabittracker.data.dao.HabitCompletionDao
import com.mueez.genzhabittracker.data.dao.HabitDao
import com.mueez.genzhabittracker.data.model.Habit
import com.mueez.genzhabittracker.data.model.HabitCompletion

@Database(
    entities = [
        Habit::class,
        HabitCompletion::class
    ],
    version = 2,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    abstract fun habitCompletionDao(): HabitCompletionDao

    companion object {

        @Volatile
        private var INSTANCE: HabitDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(
                database: SupportSQLiteDatabase
            ) {

                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS habit_completions (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        habitId INTEGER NOT NULL,
                        completedDate TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): HabitDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "genzhabit_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}

