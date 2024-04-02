package com.example.apirequest

import com.google.gson.Gson
import android.util.Log
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class RemoteApi {
    private val baseUrl = "https://api.api-ninjas.com/v1/facts"

    fun getFacts(completion: (Array<Fact>) -> Unit) {
        // Запускаем новый поток
        Thread(Runnable {
            val connection = URL(baseUrl).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("X-Api-Key", AppConstants.API_KEY)
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.doInput = true

            try {
                val reader = InputStreamReader(connection.inputStream)
                reader.use { input ->
                    val response = StringBuilder()
                    val bufferReader = BufferedReader(input)

                    bufferReader.forEachLine {
                        response.append(it.trim())
                    }

                    val responseString = response.toString()
                    Log.d("TAG", "${responseString}")
                    val typeToken = object : TypeToken<Array<Fact>>() {}.type
                    val facts = Gson().fromJson<Array<Fact>>(responseString, typeToken)
                    Log.d("TAG", "${facts}")
//                    val responseArray = Json.decodeFromString<Array<JsonObject>>(responseString)
//                    Log.d("TAG", "${responseArray}")
//                    val responseFact = Json.decodeFromString<Fact>(responseArray[0].toString())
//                    Log.d("TAG", "${responseFact}")
//                    val responseArray = Json.decodeFromString<Map<String, Any>>(responseString)
//                    Log.d("TAG", "${responseArray}")
//                    val facts = Json { ignoreUnknownKeys = true }.decodeFromString<Array<Fact>>(responseJSON)
//                    Log.d("TAG", "${facts}")

                    completion(facts)
                }
            } catch(exception: Exception) {
                Log.d("TAG", "${exception.localizedMessage}")
            }

            connection.disconnect()
        }).start()
    }
}
