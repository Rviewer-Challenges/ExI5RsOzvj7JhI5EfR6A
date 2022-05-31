package com.fernan.game.memory.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // Testing lazy Mode
    object RetrofitClient {
        val getRetrofit by lazy {
            return@lazy Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
    /*
    companion object {
        val INSTANCE: ApiHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiHelper()
        }
    }

     */

}