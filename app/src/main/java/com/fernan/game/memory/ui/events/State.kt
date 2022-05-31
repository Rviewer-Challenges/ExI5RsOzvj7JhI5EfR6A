package com.fernan.game.memory.ui.events

interface State {
    fun onStarted(){}
    fun onFinish(){}
    fun onTick(){}
    fun onBackGround(){}
    fun onMainThread(){}
    fun onFoundAlls(sizeUserTaps: Int,sizeTotalTaps: Int){}
}