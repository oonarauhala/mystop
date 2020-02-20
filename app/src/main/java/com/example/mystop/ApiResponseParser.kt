package com.example.mystop

import android.util.Log
import com.google.gson.Gson

object ApiResponseParser{

    fun parse(apiResponse:String): Stop  {
        val gson = Gson().fromJson(apiResponse, Base::class.java)
        Log.d("TAG", "ApiResponseParser: "+gson)
        return gson.data.stops[0]
    }
}