package com.fernan.game.memory.data.network

import android.util.Log
import com.fernan.game.memory.core.ApiHelper
import com.fernan.game.memory.data.model.GameBean
import com.fernan.game.memory.data.model.ThemeBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class Service {


    private val retrofit = ApiHelper.getRetrofit()
    suspend fun getAllThemes(): List<ThemeBean> {
        return withContext(Dispatchers.IO) {
            delay(3500)
            val response = retrofit.create(ApiClient::class.java).getAllThemes()
            response.body() ?: emptyList()
        }
    }

    suspend fun getThemeImages(url:String): List<GameBean> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getThemeImages(url)
            response.body() ?: emptyList()
        }
    }


}