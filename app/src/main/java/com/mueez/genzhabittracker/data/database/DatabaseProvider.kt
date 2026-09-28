
package com.mueez.genzhabittracker.data.database

import android.content.Context

object DatabaseProvider {

    lateinit var database: HabitDatabase
        private set

    fun initialize(context: Context) {
        database = HabitDatabase.getDatabase(context)
    }
}

