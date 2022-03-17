package com.example.creditcart_inkotlin.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.creditcart_inkotlin.R
import com.example.creditcart_inkotlin.adapter.CardAdapter
import com.example.creditcart_inkotlin.database.AppDatabase
import com.example.creditcart_inkotlin.model.Card
import com.example.creditcart_inkotlin.model.CardResp
import com.example.creditcart_inkotlin.network.RetrofitHttp
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var appDatabase: AppDatabase
    lateinit var circle_imageView : CircleImageView
    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        appDatabase = AppDatabase.getInstance(this)
        circle_imageView = findViewById(R.id.circle_imageView)

        circle_imageView.setOnClickListener {
            openDetailActivity()
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(this, 1))

        RetrofitHttp.posterService.listPost().enqueue(object : Callback<ArrayList<Card>?>{
            override fun onResponse(
                call: Call<ArrayList<Card>?>,
                response: Response<ArrayList<Card>?>,
            ) {
                Log.d("success","response" + response.body())
                response.body()?.let { refreshAdapter(it) }
            }

            override fun onFailure(call: Call<ArrayList<Card>?>, t: Throwable) {

            }

        })

    }

    private fun refreshAdapter(body: ArrayList<Card>) {

        val adapter = CardAdapter(this, body)
        recyclerView.setAdapter(adapter)
    }

    var detail = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result->
        if (result.resultCode == Activity.RESULT_OK){
            var user = result.data
            var user1  = user!!.getSerializableExtra("result")
            appDatabase.postDao().getPosts().toString()
        }
    }



    fun openDetailActivity(){
        var intent = Intent(this,DetailsActivity::class.java);
        detail.launch(intent)
    }
}