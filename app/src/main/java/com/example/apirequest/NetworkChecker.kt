package com.example.apirequest

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkChecker(val connectivityManager: ConnectivityManager) {
    fun performAction(action: () -> Unit) {
        if (hasValidInternetConnection()) {
            action()
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }


    private fun hasValidInternetConnection(): Boolean {
        val network = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork
        } else {
            return false
        }

        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        val hasWiFi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val hasCellular = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        val hasVPN = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        return hasWiFi || hasCellular || hasVPN
    }
}