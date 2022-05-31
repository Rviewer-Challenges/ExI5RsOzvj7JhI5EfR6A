package com.fernan.game.memory.data.model

import com.fernan.game.memory.Constants

data class LevelBean (
    val id : String,
    val name : String,
    var time: Int = Constants.baseTime,
    var files: Int = 5,
    var starts : Int = 0,
    var score : Int = 0,
    var listGame : MutableList<GameBean> = mutableListOf()

) {

}
