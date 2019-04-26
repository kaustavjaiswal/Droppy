package com.shehabic.droppy_samples

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView


open class DefaultExampleActivity : Activity() {

    protected lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selector)
        initListView()
    }

    private fun initListView() {
        listView = findViewById<ListView>(R.id.activity_selector)
        val classes = mutableListOf<Class<*>>()
        val items = mutableListOf<String>()
        classes.add(MainActivity::class.java)
        items.add("ActionBar Normal Activity")

        classes.add(ActivityWithFragment::class.java)
        items.add("Fragment Activity")

        classes.add(FullscreenActivity::class.java)
        items.add("Fullscreen Activity")

        val adapter = ActivitySelectorAdapter(this, items)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val i = Intent(this@DefaultExampleActivity, classes[position])
            startActivity(i)
        }
    }

    internal open inner class ActivitySelectorAdapter(protected var ctx: Context, protected var items: List<String>) : BaseAdapter() {

        override
        fun getItem(position: Int): String {
            return items[position]
        }

        override
        fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override
        fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (convertView == null) {
                // inflate the layout
                val inflater = (ctx as Activity).getLayoutInflater()
                convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
            }

            val textViewItem = convertView!!.findViewById(android.R.id.text1) as TextView
            textViewItem.setText(items[position])
            textViewItem.setTag(getItemId(position))

            return convertView
        }

        override fun getCount(): Int {
            return items.size
        }

    }
}

