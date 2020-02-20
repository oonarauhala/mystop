package com.example.mystop

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

class MapFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        val context = activity as Context

        //set user agent to prevent getting banned from osm servers
        Configuration.getInstance().load(context,
            PreferenceManager.getDefaultSharedPreferences(context))
        //map.setTileSource(TileSourceFactory.MAPNIK)

        return inflater.inflate(R.layout.fragment_map, container, false)
    }
}