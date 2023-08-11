package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllNameQuotes : AppCompatActivity() {
    private lateinit var databaseReference : DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_name_quotes)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("allNameQuotes")
        textView = findViewById<TextView>(R.id.textView)

        getNameData()

    }

    private fun getNameData() {
        databaseReference.child("main").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list :ArrayList<NameQuotes?> = ArrayList()

                for(i in snapshot.children){
                    list.add(i.getValue(NameQuotes::class.java))
                }

                textView.text = list.toString()

            }

            override fun onCancelled(error: DatabaseError) {
               //- Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()

            }

        })
    }

}