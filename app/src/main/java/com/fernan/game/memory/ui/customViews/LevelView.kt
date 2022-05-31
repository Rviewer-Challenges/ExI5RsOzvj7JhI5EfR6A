package com.fernan.game.memory.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.fernan.game.memory.data.model.LevelBean
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.fernan.game.memory.databinding.ViewLevelBinding
import com.fernan.game.memory.ui.events.ViewListener

class LevelView constructor(context: Context?, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    private var listener: ViewListener? = null
    fun setListener(listen: ViewListener?) {
        listener = listen
    }

    private var level: LevelBean? = null
    fun setLevel(level: LevelBean?) {
        this.level = level
        setStars(level!!.starts)
    }


    private fun setStars(stars: Int) {
        when (stars) {
            1 -> {
                bin.start1.isVisible = true
            }
            2 -> {
                bin.start1.isVisible = true
                bin.start2.isVisible = true
            }
            3 -> {
                bin.start1.isVisible = true
                bin.start2.isVisible = true
                bin.start3.isVisible = true
            }
            else -> {
                bin.start1.isVisible = false
                bin.start2.isVisible = false
                bin.start3.isVisible = false
                bin.textLevel.text = level?.name
                bin.textLevel.isVisible = true

            }
        }
    }

    private val bin: ViewLevelBinding
    private fun setActions() {
        bin.base.setOnClickListener { view ->
            if (listener != null) {
                listener?.onClicked(view, level!!)
            }
        }
    }

    init {
        // bin = ViewLevelBinding.bind(LayoutInflater.from(context).inflate(R.layout.view_level, this, true));
        bin = ViewLevelBinding.inflate(LayoutInflater.from(context), this, true)
        setActions()
    }
}