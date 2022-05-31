package com.fernan.game.memory.ui.fragments.data

interface BaseFragment {
    var listen: FragmentListener?
    fun setListener(listener: FragmentListener){
        this.listen = listener
    }
}
/*

abstract class BaseFragment() : Fragment() {
    abstract fun setListener(listener: FragmentListener)
    // abstract val listener : FragmentListener

}

 */