package com.fernan.game.memory.data.model

import com.google.gson.annotations.SerializedName

data class GameBean(
    @SerializedName("download_url") val image: String,
    var show:Boolean = false,
    var state:State = State.HIDE
)
sealed class State {
    object HIDE : State()
    object SHOW : State()
    object FOUND : State()
}
