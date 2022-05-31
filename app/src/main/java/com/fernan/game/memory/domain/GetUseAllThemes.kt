package com.fernan.game.memory.domain

import com.fernan.game.memory.data.Repository
import com.fernan.game.memory.ui.views.MainActivity

class GetUseAllThemes {
    private val repository = Repository()
    suspend operator fun invoke() = repository.getAllThemes()

}
