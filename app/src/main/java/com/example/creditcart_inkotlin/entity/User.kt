package com.example.creditcart_inkotlin.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val credit_number : String,
    val explorin_data : String,
    val holder_name : String
)