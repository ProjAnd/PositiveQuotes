package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var databaseReference : DatabaseReference
    private lateinit var databaseReference2 : DatabaseReference
    private lateinit var database:FirebaseDatabase
    lateinit var tvquote:TextView
    lateinit var btnadd :Button
    lateinit var name:String
    var userId =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvquote = findViewById<TextView>(R.id.tv_quote)
        btnadd = findViewById<Button>(R.id.buttonddQuote)
        name = "main"

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("quoteData")
        databaseReference2 = database.reference

        //databaseReference = database.getReference("allQuotes")

        if(name=="main"){
            btnadd.visibility = View.VISIBLE
        }else{
            btnadd.visibility = View.GONE
        }

        getData()
    }

    private fun getData() {
        databaseReference.child("quote")
            .addValueEventListener(object :ValueEventListener{
            override fun onDataChange(it: DataSnapshot) {
                val data = it.value
                tvquote.text = "$data"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "Error while fetching real time data")
            }

        })

    }

    fun addQuote(view: View){
        startActivity(Intent(this, WriteQuoteActivity::class.java))
    }

    fun saveQuote(view:View){
            val text  =  tvquote.text.toString()
            if(text.isNullOrEmpty()){
                Toast.makeText(this, "Add Text", Toast.LENGTH_SHORT).show()
            }else{

                addDataToFirebase(text)
            }

    }

    private fun addDataToFirebase(text: String) {
        val namequotes = NameQuotes(name, text)
        databaseReference2.child("allNameQuotes")
            .child(name).child(userId.toString())
            .setValue(namequotes).
        addOnSuccessListener {
            Toast.makeText(this, "Data Saved ", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(this, "Error while Saving Data", Toast.LENGTH_SHORT).show()

        }
        userId++
    }

    fun getAllQuotes(view:View){
        startActivity(Intent(this, AllNameQuotes::class.java))
    }
}