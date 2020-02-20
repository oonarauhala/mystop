package com.example.mystop

import okhttp3.*
import java.lang.StringBuilder
import java.net.URL

class ApiRequestMaker {

    private var contentType = "application/graphql"

    fun makeRequest(url: URL, query: String): String {
        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType = MediaType.parse(contentType)
        val body: RequestBody = RequestBody.create(
            mediaType, query
        )
        val request: Request = Request.Builder()
            .url(url)
            .method("POST", body)
            .addHeader("Content-Type", contentType)
            .build()
        val response: Response = client.newCall(request).execute()
        val responseResultText = StringBuilder()
        responseResultText.append(response.body()?.string())
         return responseResultText.toString()
    }
}