package com.example.creditcart_inkotlin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.creditcart_inkotlin.R
import com.example.creditcart_inkotlin.utils.Utils
import com.example.creditcart_inkotlin.adapter.CardAdapter
import com.example.creditcart_inkotlin.database.AppDatabase
import com.example.creditcart_inkotlin.model.Card
import com.example.creditcart_inkotlin.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var cardRepository: AppDatabase
    private lateinit var fm_loading: FrameLayout

    private lateinit var rv_cards: RecyclerView
    private lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        adapter = CardAdapter(this)
        cardRepository = AppDatabase(application)

        fm_loading = findViewById(R.id.fm_loading)
        rv_cards = findViewById(R.id.rv_cards)
        rv_cards.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_cards.adapter = adapter

        val iv_add_card: ImageView = findViewById(R.id.iv_add_card)
        iv_add_card.setOnClickListener {
            openCreateActivity()
        }

        loadCardsFromDatabase()
        offlineCardsFromDatabase()
    }

    private fun openCreateActivity() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            loadCardsFromDatabase()

            Log.d("onFinish", " Done ${cardRepository.postDao!!.getCards().toString()}")


        } else
            Log.d("onFinish", "$requestCode $requestCode")
    }


    private fun offlineCardsFromDatabase() {
        val data = cardRepository.postDao!!.getOfflineCards()

        if (Utils.isInternetAvailable(applicationContext) && data.isNotEmpty()){

            var index = 0
            saveCardToServer(index, data)

        }

        Log.d("getOfflineCards", data.toString())
    }

    private fun saveCardToServer(index: Int, cards: List<Card>) {
        var i = index
        var card = cards[index]
        card.isLoaded = true
        RetrofitHttp.posterService.createPost(card).enqueue(object : Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                Log.d("Post", response.body().toString())

                cardRepository.postDao!!.update(card)
                i++
                if (response.isSuccessful && i < cards.size-1){
                    saveCardToServer(i, cards)
                }

            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Log.d("Post", t.localizedMessage!!)
            }

        })
    }

    private fun getCardsFromServer() {
        RetrofitHttp.posterService.listPost().enqueue(object : Callback<ArrayList<Card>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<Card>>,
                response: Response<ArrayList<Card>>,
            ) {
                if (response.isSuccessful) {
                    adapter.items.clear()
                    adapter.items.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                }

                Log.d("Cards", "GET -> ${response.body()}")
            }

            override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                Log.d("Cards", "GET -> ${t.localizedMessage}")
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadCardsFromDatabase() {
        adapter.items.clear()
        adapter.items.addAll(cardRepository.postDao!!.getCards())
        adapter.notifyDataSetChanged()
    }
}