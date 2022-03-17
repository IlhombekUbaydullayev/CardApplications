package com.example.creditcart_inkotlin.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.creditcart_inkotlin.R
import com.example.creditcart_inkotlin.database.AppDatabase
import com.example.creditcart_inkotlin.entity.User
import com.example.creditcart_inkotlin.model.Card
import com.example.creditcart_inkotlin.model.CardResp
import com.example.creditcart_inkotlin.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {
    lateinit var appDatabase: AppDatabase
    lateinit var card_number : EditText
    lateinit var holder_name : EditText
    lateinit var save : Button
    lateinit var svv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initViews()
    }

    private fun initViews() {
        appDatabase = AppDatabase.getInstance(this)
        card_number = findViewById(R.id.card_number)
        holder_name = findViewById(R.id.holder_name)
        save = findViewById(R.id.save)
        svv = findViewById(R.id.svv)

        save.setOnClickListener {
            backToFinish()
        }
    }

    fun backToFinish(){

        var card = Card(0,card_number.text.toString(),"24.45.67",holder_name.text.toString(),svv.text.toString())
        var user = User(0,card_number.text.toString(),"24.45.67",holder_name.text.toString())
        var intent = Intent()
        intent.putExtra("result",card)
        setResult(Activity.RESULT_OK,intent)
        appDatabase.postDao().createPost(user)
        RetrofitHttp.posterService.createPost(card).enqueue(object : Callback<Card>{
            override fun onResponse(call: Call<Card>, response: Response<Card>) {

            }

            override fun onFailure(call: Call<Card>, t: Throwable) {

            }


        })


        finish()
    }
}