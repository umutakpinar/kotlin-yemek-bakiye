package com.umutakpinar.yemekbakiye

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf

class DbHelper(private val activity : Activity) {
    private lateinit var database : SQLiteDatabase
    init {
        try {
            database = activity.openOrCreateDatabase("CardBakiyeDb",
                AppCompatActivity.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS cards (id INTEGER PRIMARY KEY, cardName VARCHAR, balance INTEGER)")
        }catch (e : java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun AddCard(cardName : String, balance: Int){
        try {
            val contentVal = ContentValues() //Burada size belirtmek gerekiyor olabilir içeisinde parametre olarak hata alırsan buradan gelebilir
            contentVal.put("cardName",cardName)
            contentVal.put("balance",balance)
            database.insert("cards",null, contentVal) //burada null ne bilmiyoruz oradan da hata gelebilir ha dikkat

        }catch(e : java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun DeleteCard(cardId : Int){
        try {
            database.delete("cards","id = ?", arrayOf(cardId.toString())) //Burada where cümleciği direkt oalrak bu şekilde mi yazılıyor acaba ?
        }catch(e : java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun UpdateCard(cardId : Int, cardName : String, balance : Int){
        try {
            val contenVal = ContentValues()
            contenVal.put("cardName",cardName)
            contenVal.put("balance",balance)
            database.update("cards",contenVal,"id = ?",arrayOf(cardId.toString()))
        }catch(e : java.lang.Exception){
            e.printStackTrace()
        }
    }

    fun getCard(cardId: Int) : Card{
        var card : Card = Card(0,"",0)
        try {
            val cursor : Cursor = database.rawQuery("SELECT * FROM cards WHERE id = ?", arrayOf(cardId.toString()))
            val cardIdIndex = cursor.getColumnIndex("id")
            val cardNameIndex = cursor.getColumnIndex("cardName")
            val balanceIndex = cursor.getColumnIndex("balance")

            while(cursor.moveToNext()){
                val newCard = Card(cursor.getInt(cardIdIndex),cursor.getString(cardNameIndex),cursor.getInt(balanceIndex))
                card = newCard
            }
            cursor.close()
        }catch(e : java.lang.Exception){
            e.printStackTrace()
        }
        return card
    }

    fun getAllCards() : ArrayList<Card>{
        val cardsArraylist = ArrayList<Card>()
        try {
            val cursor : Cursor = database.rawQuery("SELECT id, cardName, balance FROM cards",null)

            val cardIdIndex = cursor.getColumnIndex("id")
            val cardNameIndex = cursor.getColumnIndex("cardName")
            val balanceIndex = cursor.getColumnIndex("balance")

            while (cursor.moveToNext()){
                val card = Card(cursor.getInt(cardIdIndex),cursor.getString(cardNameIndex),cursor.getInt(balanceIndex))
                cardsArraylist.add(card)
            }
            cursor.close()
        }catch(e : java.lang.Exception){
            e.printStackTrace()
        }
        return cardsArraylist
    }

    fun closeConnection(){
        database.close()
    }
}