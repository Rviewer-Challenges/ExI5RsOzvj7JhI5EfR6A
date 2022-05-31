package com.fernan.game.memory.data.network
import com.fernan.game.memory.data.model.GameBean
import com.fernan.game.memory.data.model.ThemeBean
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiClient {
    @GET("/FernanApps/FerMemoryGame/master/api/alls.json")
    suspend fun getAllThemes(): Response<MutableList<ThemeBean>>

    @GET
    suspend fun getThemeImages(@Url url: String): Response<MutableList<GameBean>>
}