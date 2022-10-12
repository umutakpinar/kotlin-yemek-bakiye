package com.umutakpinar.yemekbakiye

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.umutakpinar.yemekbakiye.databinding.RecycleColumnBinding

class CardAdapter(private val cardList : ArrayList<Card>) : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    private lateinit var connection : DbHelper
    //private var colorList = arrayListOf<String>("#9965f4","#aaf255","#df55f2","#ffaf49","#1976D2","#1DE9B6","#00E676","#F57C00")
    class CardHolder(val binding : RecycleColumnBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val binding = RecycleColumnBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardHolder(binding)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val selectedCard = cardList[position]
        holder.binding.textViewCardName.text = selectedCard.cardName
        holder.binding.textViewBalance.text = selectedCard.balance.toString()
        //colorList.shuffle()
        //holder.binding.layout.setBackgroundColor(Color.parseColor(colorList[selectedCard.cardId]))

        holder.itemView.setOnClickListener {
            connection = DbHelper(holder.itemView.context as Activity) //aktiviteye ulaşamadığımız için buradan aldım
            val intent = Intent(holder.itemView.context,CardDetails::class.java)
            intent.putExtra("cardId",selectedCard.cardId)
            holder.itemView.context.startActivity(intent)
            ActivityCompat.finishAffinity(holder.itemView.context as Activity) //açık tüm aktiviteleri sonlandır
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}