package com.fernan.game.memory.domain

import com.fernan.game.memory.data.Repository

class GetUseThemeImages(private val url: String) {
    private val repository = Repository()
    suspend operator fun invoke() = repository.getAllThemesImages(url)
}