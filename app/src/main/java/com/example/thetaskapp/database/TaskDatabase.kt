package com.example.thetaskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thetaskapp.model.Task
import java.util.concurrent.locks.Lock

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase:RoomDatabase() {

    abstract fun getTaskDao():TaskDao

    companion object{
        @Volatile
        private var instance:TaskDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?:
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