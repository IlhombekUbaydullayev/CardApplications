package com.example.creditcart_inkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.creditcart_inkotlin.R
import com.example.creditcart_inkotlin.activity.MainActivity
import com.example.creditcart_inkotlin.model.Card
import com.example.creditcart_inkotlin.model.CardResp

class CardAdapter(var activity: MainActivity, var items: ArrayList<Card>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_list, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val poster: Card = items!![position]
        if (holder is PosterViewHolder) {
            val credit_number = holder.credit_number
            val explorin_data = holder.explorin_data
            val holder_name = holder.holder_name
            val CVV = holder.CVV

            credit_number.setText(poster.credit_number)
            explorin_data.setText(poster.explorin_data)
            holder_name.setText(poster.holder_name)
            CVV.setText(poster.CVV)

        }
    }

    inner class PosterViewHolder(var view: View) : RecyclerView.ViewHolder(
        view
    ) {
        var credit_number: TextView
        var explorin_data: TextView
        var holder_name: TextView
        var CVV: TextView

        init {
            credit_number = view.findViewById(R.id.credit_number)
            explorin_data = view.findViewById(R.id.explorin_data)
            holder_name = view.findViewById(R.id.holder_name)
            CVV = view.findViewById(R.id.CVV)
        }
    }

    init {
        this.activity = activity
        this.items = items
    }
}