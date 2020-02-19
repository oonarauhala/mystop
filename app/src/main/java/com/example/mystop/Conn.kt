package com.example.mystop

import android.os.Handler
import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.create
import java.net.URL


class Conn(mHand: Handler): Runnable {

    private val myHandler = mHand
    private val myURL = URL("https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql")
    private val graphql = """{
  stop(id: "HSL:1173434") {
    name
    lat
    lon
  }
}"""

    override fun run() {
        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType = MediaType.parse("application/graphql")
        val body: RequestBody = create(
            mediaType, graphql
        )
        val request: Request = Request.Builder()
            .url(myURL)
            .method("POST", body)
            .addHeader("Content-Type", "application/graphql")
            .build()
        val response: Response = client.newCall(request).execute()

        Log.d("TAG", response.body()?.string())
        Log.d("TAG", response.toString())
        try {

        } catch (e: Exception) {
            Log.d("TAG", "Exception: ${e.message}")
        }
    }


    /*
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

     */
}