package com.example.creditcart_inkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.creditcart_inkotlin.dao.PostDao
import com.example.creditcart_inkotlin.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao
    companion object{
        private var instance : AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase{
            if (instance == null){
                instance = Room.databaseBuilder(context,
                    AppDatabase::class.java,"post.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}