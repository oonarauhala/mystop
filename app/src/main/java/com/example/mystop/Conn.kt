package com.example.mystop

import android.os.Handler
import android.util.Log
import java.io.InputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class Conn(mHand: Handler): Runnable {

    private val myHandler = mHand
    //private val myURL = URL("https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql")
    private val myURL = URL("https://worldtimeapi.org/api/timezone/Europe/Helsinki.txt")

    override fun run() {
        try {
            val myConn = myURL.openConnection() as HttpURLConnection
            val iStream: InputStream = myConn.inputStream
            val allText = iStream.bufferedReader().use {
                it.readText()
            }
            val result = StringBuilder()
            result.append(allText)
            val str = result.toString()
            val msg = myHandler.obtainMessage()
            msg.what = 0
            msg.obj = str
            myHandler.sendMessage(msg)
        } catch (e: Exception) {
            Log.d("TAG", "Exception: ${e.message}")
        }
    }
}