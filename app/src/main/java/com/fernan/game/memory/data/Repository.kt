package com.fernan.game.memory.data

import com.fernan.game.memory.data.model.GameBean
import com.fernan.game.memory.data.model.ThemeBean
import com.fernan.game.memory.data.network.Service

class Repository {

    private val api = Service()

    suspend fun getAllThemes(): List<ThemeBean> {
        return api.getAllThemes()
    }
    suspend fun getAllThemesImages(url:String): List<GameBean> {
        return api.getThemeImages(url)
    }
}