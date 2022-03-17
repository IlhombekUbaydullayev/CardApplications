package com.example.creditcart_inkotlin.model

import java.io.Serializable

data class Card(val id: Int,
                val credit_number: String,
                val explorin_data: String,
                val holder_name: String,
                val CVV: String ):Serializable