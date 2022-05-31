package com.fernan.game.memory.utils

import android.os.CountDownTimer
import android.util.Log
import com.fernan.game.memory.ui.events.State
import kotlinx.coroutines.*

class Clock {

    companion object {
        val INSTANCE: Clock by lazy {
            Clock()
        }
    }

    private var listen : State? = null
    private var delayMillis = 0L
    private var repeatMillis = 20000L
    fun onListener(delayMillis : Long, repeatMillis : Long,listener: State): Clock {
        this.listen = listener
        this.delayMillis = delayMillis
        this.repeatMillis = repeatMillis
        return INSTANCE
    }

    fun Test(){
        val timer : CountDownTimer= object: CountDownTimer(20000, 1000) {
            override fun onFinish() {
                TODO("Not yet implemented")
            }

            override fun onTick(p0: Long) {
                TODO("Not yet implemented")
            }
        }
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, action: () -> Unit) =
        scope.launch(Dispatchers.IO) {
            delay(delayMillis)
            if (repeatMillis > 0) {
                while (true) {
                    action()
                    delay(repeatMillis)
                }
            } else {
                action()
            }
    }


    private val timer: Job = startCoroutineTimer(delayMillis = delayMillis, repeatMillis = repeatMillis) {

        listen?.onBackGround()
        scope.launch(Dispatchers.Main) {
            listen?.onMainThread()
        }
    }

    fun startTimer() {
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }
}
