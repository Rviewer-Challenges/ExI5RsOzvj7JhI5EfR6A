package com.fernan.game.memory.ui.fragments.data

sealed class FragmentName (){
    object Menu: FragmentName()
    object Theme: FragmentName()
    object Levels: FragmentName()
    object Game: FragmentName()
}
