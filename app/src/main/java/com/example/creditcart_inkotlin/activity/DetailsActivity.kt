package com.example.creditcart_inkotlin.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.creditcart_inkotlin.R
import com.example.creditcart_inkotlin.utils.Utils
import com.example.creditcart_inkotlin.database.AppDatabase
import com.example.creditcart_inkotlin.model.Card
import com.example.creditcart_inkotlin.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private lateinit var cardRepository: AppDatabase

    private lateinit var tv_card_number: TextView
    private lateinit var tv_holder_name: TextView
    private lateinit var tv_expires_date: TextView

    private lateinit var edt_card_number: EditText
    private lateinit var edt_holder_name: EditText
    private lateinit var edt_expires_date_month: EditText
    private lateinit var edt_expires_date_year: EditText
    private lateinit var edt_cvv: EditText
    private lateinit var clear: ImageView

    private lateinit var b_create_card: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        cardRepository = AppDatabase(application)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        tv_card_number = findViewById(R.id.tv_card_number)
        tv_holder_name = findViewById(R.id.tv_holder_name)
        tv_expires_date = findViewById(R.id.tv_expires_date)

        edt_card_number = findViewById(R.id.edt_card_number)
        edt_holder_name = findViewById(R.id.edt_holder_name)
        edt_expires_date_month = findViewById(R.id.edt_expire_date_month)
        edt_expires_date_year = findViewById(R.id.edt_expire_date_year)
        edt_cvv = findViewById(R.id.edt_cvv)
        clear = findViewById(R.id.clear)

        clear.setOnClickListener {
            finish()
        }

        b_create_card = findViewById(R.id.b_create_card)

        loadData()

        b_create_card.setOnClickListener {
            if (edt_card_number.text.isNotEmpty()
                && edt_expires_date_month.text.isNotEmpty()
                && edt_expires_date_year.text.isNotEmpty()
                && edt_cvv.text.isNotEmpty()
                && edt_holder_name.text.isNotEmpty()
            ) {
                if (Utils.isInternetAvailable(applicationContext)){
                    val card = Card(credit_number = edt_card_number.text.toString(),
                        explorin_data = "${edt_expires_date_month.text.toString()}/${edt_expires_date_year.text.toString()}",
                        cvv = edt_cvv.text.toString(),
                        holder_name = edt_holder_name.text.toString(),
                        isLoaded = true)

                    saveCardToServer(card)
                } else {
                    val card = Card(credit_number = edt_card_number.text.toString(),
                        explorin_data = "${edt_expires_date_month.text.toString()}/${edt_expires_date_year.text.toString()}",
                        cvv = edt_cvv.text.toString(),
                        holder_name = edt_holder_name.text.toString(),
                        isLoaded = false)
                    saveCardToLocal(card)
                }


            }
            Log.d("RRR", Utils.isInternetAvailable(applicationContext).toString())
        }

    }

    private fun backToFinish(){
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun saveCardToServer(card: Card) {
        RetrofitHttp.posterService.createPost(card).enqueue(object : Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                saveCardToLocal(card)
                Log.d("Post", response.body().toString())
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Log.d("Post", t.localizedMessage!!)
            }

        })
    }

    private fun saveCardToLocal(card: Card) {
        cardRepository.postDao!!.saveCard(card)
        backToFinish()
    }


    private fun loadData() {
        edt_card_number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_card_number.text = edt_card_number.text.toString()
                if (count % 4 == 0 && count > 0) {
                    edt_card_number.text.insert(0, "$s ")
                    Log.d("@@@", "$count --> ${edt_card_number.text.toString()}")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        edt_expires_date_month.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT && edt_expires_date_month.text.isNotEmpty()) {
                tv_expires_date.text =
                    if (edt_expires_date_month.text.length == 1) "0${edt_expires_date_month.text.toString()}/YY" else "${edt_expires_date_month.text.toString()}/YY"
            }
            false
        })
        edt_expires_date_year.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT && edt_expires_date_year.text.isNotEmpty() && edt_expires_date_month.text.isNotEmpty()) {
//                tv_expires_date.text = if (edt_expires_date_month.text.length == 1) "0${edt_expires_date_month.text.toString()}/YY" else "${edt_expires_date_month.text.toString()}/YY"
                tv_expires_date.text =
                    "${edt_expires_date_month.text.toString()}/${edt_expires_date_year.text.toString()}"
            }
            false
        })
        edt_cvv.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT && edt_cvv.text.isNotEmpty()) {

            }
            false
        })

        edt_holder_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_holder_name.text = edt_holder_name.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}
