package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WriteQuoteActivity : AppCompatActivity() {
    private val  TAG = "WriteQuoteActivity"

    lateinit var etquote: EditText
    private lateinit var dataBase: FirebaseDatabase
    private lateinit var databaseReference:DatabaseReference
    private lateinit var databaseReference2:DatabaseReference
    var userId =0
    lateinit var list : ArrayList<QuoteModel?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_quote)
        etquote = findViewById(R.id.editTextTextMultiLine)

        dataBase = FirebaseDatabase.getInstance()
        databaseReference = dataBase.getReference("quoteData")
        databaseReference2 = dataBase.reference
       // getAllQuoteData()

    }

    private fun getAllQuoteData() {
        //getting data from child "allQuotes" in firebase
        databaseReference2.child("allQuotes").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list=ArrayList()
                for (postSnapshot in snapshot.children) {
                    
                    val quoteModel: QuoteModel? = postSnapshot.getValue(QuoteModel::class.java)
                    list.add(quoteModel)
                }
                userId = (list.size)+1
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    fun writeQuote(view: View){
        val text  =  etquote.text.toString()
        if(text.isNullOrEmpty()){
            Toast.makeText(this, "Add Text", Toast.LENGTH_SHORT).show()
        }else{

            addDataToFirebase(text)
        }
        
    }

    private fun addDataToFirebase(text: String) {
                databaseReference.child("quote").setValue(text).
                addOnSuccessListener {
                    Toast.makeText(this, "Data Added ", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener {
                    Toast.makeText(this, "Error while Adding Data", Toast.LENGTH_SHORT).show()

                }
                 val quoteInfo = QuoteModel(text)

                databaseReference2.child("allQuotes").child(userId.toString()).setValue(quoteInfo).
                addOnSuccessListener {
                    Toast.makeText(this, " allQuotesData Added ", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener {
                    Toast.makeText(this, "Error while Adding allQuotes Data", Toast.LENGTH_SHORT).show()

                }
                userId++
                Log.i(TAG, userId.toString())

    }
    fun updateQuote(view : View){

    }

}