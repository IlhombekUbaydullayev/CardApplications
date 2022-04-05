package com.example.creditcart_inkotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val credit_number: String,
    val explorin_data: String,
    val holder_name: String,
    val cvv: String? = null,
    var isLoaded: Boolean = true
) : Serializable