package com.umutakpinar.yemekbakiye

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnAttach
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umutakpinar.yemekbakiye.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var width : Int = 0
    private var height : Int = 0
    private lateinit var recylerView : View
    private lateinit var connection : DbHelper
    private lateinit var cardsArray : ArrayList<Card>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ////////////////////////////////////////////////////////////////////////back butonuna basılmasını engellemek gerek
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recylerView = binding.recylerView
        cardsArray = ArrayList()
        connection = DbHelper(this@MainActivity)

        cardsArray = getAllCardsFromDb(connection)

        val cardAdapter = CardAdapter(cardsArray)
        binding.recylerView.layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false) //(this@MainActivity,1) // bu kısmı gridlayouta çevirebiliriz!
        binding.recylerView.adapter = cardAdapter
        println("Database Size : $cardsArray.size")
    }

    override fun onStart() {
        super.onStart()

        getDefaultDimensions()
        if(this.width > 1600){
            recylerView.layoutParams.width = this.width * 75/100
        }else if (this.width >= 1600){
            recylerView.layoutParams.width = this.width * 50/100
        }
    }

    private fun getDefaultDimensions(){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        this.height = displayMetrics.heightPixels
        this.width = displayMetrics.widthPixels
        //Toast.makeText(this@MainActivity,"Window Height $height & Width : $width",Toast.LENGTH_LONG).show()
    }

    fun addCardClicked(view : View){
        val intent = Intent(this@MainActivity,AddCard::class.java)
        startActivity(intent)
        finish()
    }

    private fun getAllCardsFromDb(connection : DbHelper) : ArrayList<Card>{
        return connection.getAllCards()
    }

}