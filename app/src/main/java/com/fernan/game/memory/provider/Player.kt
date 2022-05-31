package com.fernan.game.memory.provider

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import com.fernan.game.memory.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Player {
    var PLAY = false
    fun correct(context: Context?) {
        if (!PLAY) {
            val player: MediaPlayer = MediaPlayer.create(context, R.raw.correct)
            player.setOnCompletionListener { mp ->
                mp?.reset()
                mp?.release()
            }
            player.start()
        }
    }

    fun start(context: Context?) {
        if (!PLAY) {
            val mp: MediaPlayer = MediaPlayer.create(context, R.raw.star)
            mp.setOnCompletionListener { mp ->
                mp.reset()
                mp.release()
            }
            mp.start()
        }
    }

    fun countdown(context: Context?,@RawRes music : Int)  {
        if (!PLAY) {
            val mp: MediaPlayer = MediaPlayer.create(context, music)
            mp.setOnCompletionListener { mp ->
                mp.reset()
                mp.release()
            }
            mp.start()
        }
    }




}