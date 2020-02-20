package com.example.mystop

import android.os.Handler
import android.util.Log
import java.net.URL

class Conn(mHand: Handler): Runnable {

    private val myHandler = mHand
    private val myURL = URL("https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql")
    private var query = ""
    private val graphql = """{stop(id: "HSL:1173434") {name}}"""

    override fun run() {
        try {
            //make request to server
            if (query != "") {
                val requestResponseString = RequestMaker().makeRequest(myURL, query)
                val message = myHandler.obtainMessage()
                message.what = 0
                message.obj = requestResponseString
                myHandler.sendMessage(message)
            }
            else {
                Log.d("TAG", "Set query parameters first")
            }
        } catch (e: Exception) {
            Log.d("TAG", "Error making api request: ${e.message}")
        }
    }

    fun createQueryWithNumber(number: Int){
        query = """{stops(name: "$number") {name lat lon}}"""
    }
}