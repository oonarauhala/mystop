package com.example.mystop

import android.os.Handler
import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.create
import java.lang.StringBuilder
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
        try {
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
            val result = StringBuilder()
            result.append(response.body()?.string())
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