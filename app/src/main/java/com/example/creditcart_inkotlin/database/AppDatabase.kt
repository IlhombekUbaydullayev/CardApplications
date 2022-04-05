package com.example.creditcart_inkotlin.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.creditcart_inkotlin.database.dao.PostDao
import com.example.creditcart_inkotlin.managers.RoomManager
import com.example.creditcart_inkotlin.model.Card


class AppDatabase(application: Application) {
    var postDao: PostDao? = null

    init {
        val db = RoomManager.getInstance(application)
        this.postDao = db.postDao()
    }
}