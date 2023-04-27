package com.example.myassignment

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class ApiClient {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    private val gson = GsonBuilder().create()

    fun getCatFact(callback: (CatFact?, String?) -> Unit) {
        val request = Request.Builder()
            .url("https://catfact.ninja/fact")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback(null, e.message)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val catFactJson = response.body?.string()
                    val catFact = gson.fromJson(catFactJson, CatFact::class.java)
                    callback(catFact, null)
                } else {
                    callback(null, "Error fetching cat fact")
                }
            }
        })
    }

    data class CatFact(val fact: String)
}
