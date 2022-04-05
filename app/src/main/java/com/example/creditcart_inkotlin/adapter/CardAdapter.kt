package com.example.creditcart_inkotlin.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.creditcart_inkotlin.R
import com.example.creditcart_inkotlin.model.Card

class CardAdapter(val context: Context, ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: ArrayList<Card> = ArrayList()

    @JvmName("setItems1")
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Card>){
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_list, parent, false)
        return DemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DemoViewHolder)
            holder.bind(position)
    }

    override fun getItemCount() = items.size

    inner class DemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tv_card_number: TextView = view.findViewById(R.id.tv_card_number)
        val tv_holder_name: TextView = view.findViewById(R.id.tv_holder_name)
        val tv_expires_date: TextView = view.findViewById(R.id.tv_expires_date)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int) {
            val card = items[position]

            tv_card_number.text = card.credit_number
            tv_holder_name.text = card.holder_name
            tv_expires_date.text = card.explorin_data
        }
    }
}