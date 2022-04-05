package com.example.creditcart_inkotlin.database.dao

import androidx.room.*
import com.example.creditcart_inkotlin.model.Card


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCard(card: Card)

    @Query("SELECT * FROM posts")
    fun getCards(): List<Card>

    @Query("SELECT * FROM posts where isLoaded=0")
    fun getOfflineCards(): List<Card>

    @Update
    fun update(card: Card)

}