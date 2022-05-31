package com.fernan.game.memory.utils

import com.fernan.game.memory.core.ApiHelper

class App {
    companion object {
        lateinit var instance: App
        private set
    }



    fun onFuction() {
        instance = this
    }

}