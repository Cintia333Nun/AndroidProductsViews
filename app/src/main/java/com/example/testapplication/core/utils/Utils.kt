package com.example.testapplication.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class Utils {
    companion object {
        fun startGooglePing(ctx: Context): Boolean {
            var isSuccess = false
            try {
                if (isNetworkEnable(ctx)) {
                    try {
                        val url = URL("https://www.google.com:443/")
                        val urlc = url.openConnection() as HttpURLConnection
                        urlc.setRequestProperty(
                            "User-Agent",
                            "Android Application: PingTest"
                        )
                        urlc.setRequestProperty("Connection", "close")
                        urlc.connectTimeout = 5 * 1000
                        urlc.connect()
                        if (urlc.responseCode < 500 || urlc.responseCode > 504) {
                            isSuccess = true
                        }
                    }
                    catch (ex: java.net.SocketTimeoutException) {
                        ex.printStackTrace()
                    }
                    catch (ex2: IOException) {
                        ex2.printStackTrace()
                    }
                }
            }
            catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return isSuccess
        }

        private fun isNetworkEnable(context: Context): Boolean {
            val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            var isMobileDataEnabled = false

            try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val cmClass = Class.forName(cm.javaClass.name)
                val method = cmClass.getDeclaredMethod("getMobileDataEnabled")
                method.isAccessible = true
                isMobileDataEnabled = method.invoke(cm) as Boolean
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            return wifi.isWifiEnabled || isMobileDataEnabled
        }
    }
}