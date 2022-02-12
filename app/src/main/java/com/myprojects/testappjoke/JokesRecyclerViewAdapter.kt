package com.myprojects.testappjoke

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.myprojects.testappjoke.pogo.Joke


class JokesRecyclerViewAdapter internal constructor(context: Context?, data:Joke) :
    RecyclerView.Adapter<JokesRecyclerViewAdapter.ViewHolder>() {
    private val mData: Joke
    private val mInflater: LayoutInflater

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_row, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = mData.value[position]
        if (position!=0) {
            holder.myTextView.text = data.joke
            Log.d("Position_debag", "Шутка " + data.joke + "Позиция " + position)

        }

    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.value.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myTextView: TextView


        init {
            myTextView = itemView.findViewById(R.id.tvJoke)

        }
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
    }

}