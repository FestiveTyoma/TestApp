package com.myprojects.testappjoke

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JokesFragment : Fragment(), View.OnClickListener {

    var adapter: MyRecyclerViewAdapter? = null
    val JOKES_ARRAY_KEY = "1"
    private var numberOfJokes = 0
    var editText: EditText? = null
    var recyclerView: RecyclerView? = null
    var jokesArray: ArrayList<String>? = null
    var mInstance: JokesFragment? = null


    fun getInstance(): JokesFragment? {
        if (mInstance == null) {
            mInstance = JokesFragment()
        }
        return mInstance
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            jokesArray = savedInstanceState.getStringArrayList(JOKES_ARRAY_KEY)
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
        if (jokesArray != null) {
            fillListWithJokes()
        }
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        outState.putStringArrayList(JOKES_ARRAY_KEY, jokesArray)
    }

    override fun onClick(v: View?) {
        numberOfJokes = 0
        val text = editText!!.text.toString()
        if (text != "") {
            numberOfJokes = text.toInt()
            //TODO if I enter more than 574 jokes i get indexOfBoundException ArrayList 574, i didn't understand why
            if (numberOfJokes > 574) {
                Toast.makeText(activity, "Max amount of jokes is 574", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            Toast.makeText(activity, "Please, enter a number of jokes", Toast.LENGTH_SHORT).show()
            return
        }
        //get Json data and set it in ArrayList
        NetworkService.getInstance().getAPI().getRandomJokesWithCount(numberOfJokes).enqueue(object : Callback<Joke?> {
            override fun onResponse(@NonNull call: Call<Joke?>, @NonNull response: Response<Joke?>) {
                jokesArray = ArrayList()
                val listValue: List<Value>
                val jokeClass: Joke? = response.body()
                listValue = jokeClass.getValue()
                for (i in 0 until numberOfJokes) {
                    jokesArray!!.add(listValue[i].getJoke())
                }
                fillListWithJokes()
            }

            override fun onFailure(@NonNull call: Call<Joke?>, @NonNull t: Throwable) {
                Toast.makeText(activity, "Error occurred while getting request!", Toast.LENGTH_SHORT).show()
                Log.d("Tag", t.toString())
                //                t.printStackTrace();
            }
        })
    }

    //set data to listview using the adapter
    private fun fillListWithJokes() {
        recyclerView.setLayoutManager(LinearLayoutManager(activity))
        adapter = MyRecyclerViewAdapter(activity, jokesArray)
        recyclerView.setAdapter(adapter)
    }


}