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
                val requestResponse = ApiRequestMaker().makeRequest(myURL, query)
                Log.d("TAG", requestResponse)

                //parsing
                try {
                    val requestResponseObject = ApiResponseParser.parse(requestResponse)
                    Log.d("TAG", "Object test (name: "+requestResponseObject.name)
                    Log.d("TAG", "Object test (zone: "+requestResponseObject.zoneId)
                } catch(e:Exception) {
                    Log.d("TAG", "Parsing error")
                }

                val message = myHandler.obtainMessage()
                message.what = 0
                message.obj = requestResponse
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
        query = """{stops(name: "$number") {gtfsId name lat lon zoneId wheelchairBoarding}}"""
    }

    fun createQueryWithName(name: String){
        query = """{stops(name: "$name") {gtfsId name lat lon zoneId wheelchairBoarding}}"""
    }

    fun createQueryWithLocation(lat: Float, lon: Float, radius: Int){
        //TODO location query
    }
}