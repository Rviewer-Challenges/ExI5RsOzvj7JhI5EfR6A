package com.fernan.game.memory.ui.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fernan.game.memory.databinding.ActivitySplashBinding
import com.fernan.game.memory.utils.Animator
import com.fernan.game.memory.utils.Utils.loadGifFromAsset
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var bin: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bin.root)

        initViews()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 3500)


    }

    private fun initViews(){
        bin.imageView.loadGifFromAsset("loading_500")
        // Animation Title
        Animator.Alpha(bin.textView)
    }
}