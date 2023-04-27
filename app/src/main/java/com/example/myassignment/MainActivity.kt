package com.example.myassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var factTextView: TextView
    private lateinit var refreshButton: Button

    private val apiClient = ApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        factTextView = findViewById(R.id.factTextView)
        refreshButton = findViewById(R.id.refreshButton)

        refreshButton.setOnClickListener {
            fetchCatFact()
        }

        fetchCatFact()
    }

    private fun fetchCatFact() {
        apiClient.getCatFact { catFact, error ->
            runOnUiThread {
                if (error != null) {
                    factTextView.text = "Error fetching cat fact: $error"
                } else if (catFact != null) {
                    factTextView.text = catFact.fact
                }
            }
        }
    }
}
