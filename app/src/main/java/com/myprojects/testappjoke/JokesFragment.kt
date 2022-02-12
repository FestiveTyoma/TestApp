package com.myprojects.testappjoke

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myprojects.testappjoke.pogo.Joke
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JokesFragment : Fragment(), View.OnClickListener {

   private lateinit var adapter: JokesRecyclerViewAdapter
    private val JOKES_ARRAY_KEY = "1"
    private var numberOfJokes = 0
    private var editText: EditText? = null
   private var recyclerView: RecyclerView? = null
    var jokesArray: ArrayList<String>?=null
    lateinit var joke:Joke



    companion object {

            var mInstance: JokesFragment? = null

        fun getInstance(): JokesFragment? {
            if (mInstance == null) {
                mInstance = JokesFragment()
            }
            return mInstance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            jokesArray = savedInstanceState.getStringArrayList(JOKES_ARRAY_KEY)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_jokes, container, false)
        val reloadButton = layout.findViewById<Button>(R.id.reloadButton)
        recyclerView = layout.findViewById(R.id.recyclerView)
        reloadButton.setOnClickListener(this)
        editText = layout.findViewById(R.id.editTextNumber)
        return layout
    }

    override fun onStart() {
        super.onStart()
        if (jokesArray!=null) {
            fillListWithJokes()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList(JOKES_ARRAY_KEY, jokesArray)
    }

    override fun onClick(v: View?) {
        numberOfJokes = 0
        val text = editText!!.text.toString()
        if (text != "") {
            numberOfJokes = text.toInt()
            if (numberOfJokes > 574) {
                Toast.makeText(activity, "Max amount of jokes is 574", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            Toast.makeText(activity, "Please, enter a number of jokes", Toast.LENGTH_SHORT).show()
            return
        }
        //get Json data and set it in ArrayList
        NetWorkService.instance?.aPI?.getRandomJokesWithCount(numberOfJokes)?.enqueue(object : Callback<Joke?> {
            override fun onResponse(call: Call<Joke?>, response: Response<Joke?>) {
                jokesArray=ArrayList()
                joke=response.body()!!
                for (i in 0 until numberOfJokes) {

                    jokesArray!!.add(joke.value[i].joke)
                }
                fillListWithJokes()
            }

            override fun onFailure(call: Call<Joke?>, t: Throwable) {
                Toast.makeText(activity, "Error occurred while getting request",
                    Toast.LENGTH_SHORT).show()
                Log.e("Tag", "Вот эта коварная ошибка $t")
            }
        })
    }

    //set data to listview using the adapter
    private fun fillListWithJokes() {
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        adapter = JokesRecyclerViewAdapter(activity, jokesArray!!)
        recyclerView?.adapter=adapter
    }
}


