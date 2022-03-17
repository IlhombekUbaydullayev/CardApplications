package com.example.creditcart_inkotlin.model

import com.google.gson.annotations.SerializedName

data class CardResp(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("credit_number")
    val credit_number: String? = null,
    @SerializedName("explorin_data")
    val explorin_data: String? = null,
    @SerializedName("holder_name")
    val holder_name: String? = null,
    @SerializedName("CVV")
    val CVV: String? = null
)