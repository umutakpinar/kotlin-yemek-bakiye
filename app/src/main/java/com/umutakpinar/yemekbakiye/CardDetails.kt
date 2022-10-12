package com.umutakpinar.yemekbakiye

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.umutakpinar.yemekbakiye.databinding.ActivityCardDetailsBinding

class CardDetails : AppCompatActivity() {
    private lateinit var binding : ActivityCardDetailsBinding
    private lateinit var connection : DbHelper
    private lateinit var selectedCard : Card
    private val moneySign = " ₺"
    private var balance : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent = intent
        val selectedId = intent.getIntExtra("cardId",0)

        connection = DbHelper(this@CardDetails)
        selectedCard = connection.getCard(selectedId)
        this.balance = selectedCard.balance
        binding.textViewCardName.text = selectedCard.cardName
        setBalanceText(balance)
    }

    fun deleteCardClicked(view : View){
        val alert = AlertDialog.Builder(this@CardDetails)
        alert.setCancelable(false)
        alert.setTitle("Delete card?")
        alert.setMessage("Deleting the card cannot be undone.")
        alert.setPositiveButton("Yes, delete!") { dialog, which ->
            connection.DeleteCard(selectedCard.cardId)
            makeShortToast("Delete succesfull")
            intent = Intent(this@CardDetails,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        alert.setNegativeButton("Cancel"){dialog, which ->
            makeShortToast("Nothing deleted")
        }
        alert.show()
    }

    fun decreaseFour(view: View){ //Burada kontrol yapılmalı işelm sonucu sıfırdan küçükse işlem gerçekleşmemeli!
        val transaction = balance - 4
        if(!isTransactionMakeBalanceNegative(transaction)){
            balance = transaction
            setBalanceText(balance)
            connection.UpdateCard(selectedCard.cardId,selectedCard.cardName,balance)
            makeShortToast("Bakiye : -4₺")
        }else{
            makeToastTransactionError()
        }

    }

    fun increaseAmount(view : View){
        val amount = getAmount()
        val transaction = balance + amount
        if(!isTransactionMakeBalanceNegative(transaction)){
            balance = transaction
            connection.UpdateCard(selectedCard.cardId,selectedCard.cardName,balance)
            setBalanceText(balance)
            makeShortToast("Bakiye güncellendi")
        }else{
            makeShortToast("Oops! Balance never lower than 0")
        }
    }

    fun decreaseAmount(view : View){
        val amount = getAmount()
        val transaction = balance - amount
        if(!isTransactionMakeBalanceNegative(transaction)){
            balance = transaction
            connection.UpdateCard(selectedCard.cardId,selectedCard.cardName,balance!!)
            setBalanceText(balance)
            makeShortToast("Bakiye güncellendi")
        }else{
            makeToastTransactionError()
        }
    }


    //HELPER METHODS
    private fun setBalanceText(balance : Int){
        binding.textViewBalance.text = balance.toString() + moneySign
    }

    private fun makeShortToast(text : String){
        Toast.makeText(this@CardDetails,text,Toast.LENGTH_SHORT).show()
    }

    private fun getAmount() : Int{
        var amount = binding.editTextNumberSigned.text.toString()
        if(amount.isEmpty()){
            amount = 0.toString()
        }
        return amount.toInt()
    }

    override fun onBackPressed() {
        intent = Intent(this@CardDetails,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isTransactionMakeBalanceNegative(transaction : Int) : Boolean{
        return transaction < 0
    }

    private fun makeToastTransactionError(){
        makeShortToast("Oops! Balance never lower than 0")
    }
}