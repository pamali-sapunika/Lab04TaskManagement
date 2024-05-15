package com.example.thetaskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thetaskapp.model.Task
import java.util.concurrent.locks.Lock


@Database(entities = [Task::class], version = 1)
//Abstract base class for Room Database
abstract class TaskDatabase:RoomDatabase() {

    abstract fun getTaskDao():TaskDao //Allowing access to database operations

    companion object{ //Method for creating an instance
        @Volatile
        private var instance:TaskDatabase? = null //Singleton pattern
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: //DataBase instance
        synchronized(Lock){
            instance ?:
            createDatabase(context).also{
                instance = it
            }

        }
        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db"

            ).build()



    }
}