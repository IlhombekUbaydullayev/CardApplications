package com.example.creditcart_inkotlin.managers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.creditcart_inkotlin.database.dao.PostDao
import com.example.creditcart_inkotlin.model.Card

@Database(entities = [Card::class], version = 1)
abstract class RoomManager: RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object{
        private var instance: RoomManager? = null

        fun getInstance(context: Context): RoomManager{
            if (instance == null){
                instance = Room.databaseBuilder(context, RoomManager::class.java, "database.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }
}