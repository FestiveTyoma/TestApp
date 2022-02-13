package com.myprojects.testappjoke

import com.myprojects.testappjoke.pogo.Joke
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class NetWorkService private constructor() {
    private val mRetrofit: Retrofit

    interface APIService {
        @GET("/jokes/random/{count}")
        fun getRandomJokesWithCount(@Path("count") count: Int): Call<Joke?>?
    }

    val aPI: APIService
        get() = mRetrofit.create(APIService::class.java)

    companion object {
        private var mInstance: NetWorkService? = null
        private const val BASE_URL = "https://api.icndb.com"

        //Singleton for class
        val instance: NetWorkService?
            get() {
                if (mInstance == null) {
                    mInstance = NetWorkService()
                }
                return mInstance
            }
    }

    // declare and initialize Retrofit
    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}