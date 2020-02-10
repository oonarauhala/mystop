package com.example.mystop

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.list_element.view.*

class TimetableListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        val activity = activity as Context
        val view = inflater.inflate(R.layout.fragment_timetable, container, false)
        val listView = view.findViewById<ListView>(R.id.timetableList)
        listView.adapter = MyCustomAdapter(activity)
        return view
    }

    internal inner class MyCustomAdapter(context: Context):BaseAdapter() {
        private val mContext: Context = context

        val dummyList = listOf("Dog", "Cat", "Bird")

        //number of list rows
        override fun getCount(): Int {
            return dummyList.size
        }

        //position of each row
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //data on each row
        override fun getItem(position: Int): Any {
            return dummyList[position]
        }

        //specify list element
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val row = layoutInflater.inflate(R.layout.list_element, parent, false)
            val currentItem = dummyList[position]
            row.lineNumber.text = currentItem
            return row
        }
    }
}