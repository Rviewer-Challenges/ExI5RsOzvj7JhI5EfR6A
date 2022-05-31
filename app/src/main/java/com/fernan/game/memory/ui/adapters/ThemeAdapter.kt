package com.fernan.game.memory.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import com.fernan.game.memory.data.model.ThemeBean
import com.fernan.game.memory.databinding.ItemThemeBinding
import com.fernan.game.memory.utils.Animator
import com.fernan.game.memory.utils.Utils.loadFrom


class ThemeAdapter(var list: List<ThemeBean>) : BaseAdapter() {

    interface onListen {
        fun onClicled(position: Int, themeBean: ThemeBean)
    }

    private var listener: onListen? = null
    fun setListener(listener: onListen) {
        this.listener = listener
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater =
            parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bin = ItemThemeBinding.inflate(inflater, parent, false)

        bin.title.text = list[position].title
        when (list[position].starts) {
            1 -> bin.start1.isVisible = true
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
            }


        }

        bin.imageBackground.loadFrom(list[position].thumbnail)
        /*
        Glide.with(parent.context)
            .load(list[position].thumbnail)
            .apply(RequestOptions().override(
            250,235))
            .into(bin.imageBackground);    .thumbnail(0.1f)
            */


        bin.startGameButton.setOnClickListener {
            listener?.onClicled(position, list[position])
        }

        Animator.animateShow(bin.root)
        return bin.root
    }
}