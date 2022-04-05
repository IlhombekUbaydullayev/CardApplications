package com.example.creditcart_inkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.creditcart_inkotlin.R
import com.example.creditcart_inkotlin.database.AppDatabase
import com.example.creditcart_inkotlin.model.Card
import com.example.creditcart_inkotlin.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private lateinit var cardRepository: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initViews()
    }

    private fun initViews(){
        cardRepository = AppDatabase(application)
        var size = cardRepository.postDao!!.getCards().size
        if (size == 0){
            apiLoadCards()
        } else{
            callMainActivity()
        }
    }

    private fun apiLoadCards() {
        RetrofitHttp.posterService.listPost().enqueue(object : Callback<ArrayList<Card>> {
            override fun onResponse(
                call: Call<ArrayList<Card>>,
                response: Response<ArrayList<Card>>,
            ) {
                saveCardsToDatabase(response.body()!!)
                Log.d("@@@", response.isSuccessful.toString())
            }

            override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                Log.d("@@@", t.localizedMessage!!)
            }

        })
    }

    private fun saveCardsToDatabase(items: ArrayList<Card>) {
        for (item in items){
            cardRepository.postDao!!.saveCard(item)
        }
        callMainActivity()
    }

    private fun callMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}