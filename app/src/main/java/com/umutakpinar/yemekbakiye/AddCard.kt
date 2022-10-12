package com.umutakpinar.yemekbakiye

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.umutakpinar.yemekbakiye.databinding.ActivityAddCardBinding

class AddCard : AppCompatActivity() {

    private lateinit var connection : DbHelper
    private lateinit var binding : ActivityAddCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        connection = DbHelper(this@AddCard)
    }

    fun saveCardClicked(view : View){
        val cardName = binding.editTextCardName.text.toString()
        val balance = binding.editTextBalance.text.toString()

        if(cardName.isEmpty() || balance.isEmpty()){
            Toast.makeText(this@AddCard,"Lütfen gerekli alanları doldurun!",Toast.LENGTH_SHORT).show()
        }else{
            connection.AddCard(cardName,balance.toInt())
            Toast.makeText(this@AddCard,"Kart eklendi!",Toast.LENGTH_SHORT).show()
            intent = Intent(this@AddCard,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        val alert = AlertDialog.Builder(this@AddCard)
        alert.setCancelable(false)
        alert.setTitle("Dou you want exit?")
        alert.setMessage("Your card has not been registered yet!")
        alert.setPositiveButton("Exit anyway"){ dialog, which ->
            val intent = Intent(this@AddCard,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        alert.setNegativeButton("Cancel"){ dialog, which ->

        }
        alert.show()
    }

}