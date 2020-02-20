package com.example.mystop

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration

class MapFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = activity as Context

        //Set user agent to prevent getting banned from osm servers
        Configuration.getInstance().load(context,
            PreferenceManager.getDefaultSharedPreferences(context))

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
}