package com.fernan.game.memory

object Constants {
    const val baseTime = 60
    const val baseTimeAdd = 40
    const val scoreXStar = 10
    val generateUniqueLevel = { apiUrl: String, number:Int -> "${apiUrl}_level=${number}" }


}