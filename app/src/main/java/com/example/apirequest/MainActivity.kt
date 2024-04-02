package com.example.apirequest

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private val networkChecker by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkChecker(getSystemService(ConnectivityManager::class.java))
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }
    private val remoteApi = RemoteApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val textView: TextView = findViewById(R.id.textView)
        remoteApi.getFacts { facts ->
            runOnUiThread {
                textView.text = facts.map { it.fact }.joinToString()
            }
        }
    }
}