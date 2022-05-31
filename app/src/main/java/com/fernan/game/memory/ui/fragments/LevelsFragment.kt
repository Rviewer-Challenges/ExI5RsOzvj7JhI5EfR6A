package com.fernan.game.memory.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fernan.game.memory.Constants
import com.fernan.game.memory.data.model.GameBean
import com.fernan.game.memory.data.model.LevelBean
import com.fernan.game.memory.data.model.State
import com.fernan.game.memory.data.model.ThemeBean
import com.fernan.game.memory.databinding.FragmentLevelsBinding
import com.fernan.game.memory.provider.SharedManager
import com.fernan.game.memory.ui.customViews.LevelView
import com.fernan.game.memory.ui.events.ViewListener
import com.fernan.game.memory.ui.fragments.data.BaseFragment
import com.fernan.game.memory.ui.fragments.data.FragmentListener
import com.fernan.game.memory.ui.fragments.data.FragmentName
import com.google.gson.Gson
import com.google.gson.GsonBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LevelsFragmentListen.newInstance] factory method to
 * create an instance of this fragment.
 */
class LevelsFragment : Fragment(), BaseFragment {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override var listen: FragmentListener? = null


    private lateinit var bin: FragmentLevelsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        this.bin = FragmentLevelsBinding.inflate(inflater, container, false)
        listViewsLevels.add(bin.level1)
        listViewsLevels.add(bin.level2)
        listViewsLevels.add(bin.level3)
        listViewsLevels.add(bin.level4)
        return bin.root
    }


    private val listLevels: MutableList<LevelBean> = mutableListOf()
    private val listViewsLevels: MutableList<LevelView> = mutableListOf()
    private var themeBean: ThemeBean? = null
    fun onSetViews(theme: ThemeBean, gameBeanList: MutableList<GameBean>) {

        val sharedManager = SharedManager(requireActivity())
        this.themeBean = theme;

        for (i in listViewsLevels.indices) {

            val level = LevelBean(
                Constants.generateUniqueLevel(theme.apiUrl, i + 1),
                "Nivel ${i + 1}"
            )

            if (sharedManager.contains(level.id)) {
                level.starts = sharedManager.getInt(level.id)
            } else {
                level.starts = 0
            }


            /**

            level 1 = 5
            level 3 = 10
            level 3 = 15
            level 4 = 25

             */
            val isLand =
                requireActivity().resources.getBoolean(com.fernan.game.memory.R.bool.isLand)

            val gameBeanListEnd = mutableListOf<GameBean>()
            when (i) {
                0 -> {
                    val xGameBeanList = gameBeanList.take(4) as MutableList<GameBean>
                    gameBeanListEnd.addAll(xGameBeanList)
                    gameBeanListEnd.addAll(xGameBeanList)

                    level.listGame = gameBeanListEnd.shuffled() as MutableList<GameBean>
                    level.files = if (isLand) 4 else 2
                    level.time = Constants.baseTime

                }

                1 -> {
                    val xGameBeanList = gameBeanList.take(8) as MutableList<GameBean>
                    gameBeanListEnd.addAll(xGameBeanList)
                    gameBeanListEnd.addAll(xGameBeanList)

                    level.listGame = gameBeanListEnd.shuffled() as MutableList<GameBean>
                    level.files = if (isLand) 4 else 3

                    level.time = Constants.baseTime + Constants.baseTimeAdd
                }

                2 -> {
                    val xGameBeanList = gameBeanList.take(12) as MutableList<GameBean>
                    gameBeanListEnd.addAll(xGameBeanList)
                    gameBeanListEnd.addAll(xGameBeanList)

                    level.listGame = gameBeanListEnd.shuffled() as MutableList<GameBean>
                    level.files = if (isLand) 6 else 3
                    level.time = Constants.baseTime + (Constants.baseTimeAdd * 2)
                }

                3 -> {
                    val xGameBeanList = gameBeanList.take(16) as MutableList<GameBean>
                    gameBeanListEnd.addAll(xGameBeanList)
                    gameBeanListEnd.addAll(xGameBeanList)

                    level.listGame = gameBeanListEnd.shuffled() as MutableList<GameBean>
                    level.files = if (isLand) 8 else 3
                    level.time = Constants.baseTime + (Constants.baseTimeAdd * 3)

                }

            }


            val levelView = listViewsLevels[i]
            levelView.setLevel(level)
            levelView.setListener(object : ViewListener {
                override fun onClicked(v: View, any: Any) {
                    listen?.onChange(FragmentName.Game, any)
                }
            })

            listLevels.add(level)

        }

    }


    fun onFromGameFragment() {
        update()
    }

    private fun update() {
        val sharedManager = SharedManager(requireActivity())

        for (i in listViewsLevels.indices) {
            val levelView = listViewsLevels[i]
            listLevels[i].listGame.forEach { level ->
                level.state = State.HIDE
            }
            if (sharedManager.contains(listLevels[i].id)) {
                listLevels[i].starts = sharedManager.getInt(listLevels[i].id)
            } else {
                listLevels[i].starts = 0
            }
            levelView.setLevel(listLevels[i])
            levelView.setListener(object : ViewListener {
                override fun onClicked(v: View, any: Any) {
                    listen?.onChange(FragmentName.Game, any)
                }
            })
        }

        // Now Starts Theme

        var startsCount = 0
        Log.d("updateLevls", GsonBuilder().disableHtmlEscaping().create().toJson(listLevels))
        listLevels.onEach { level ->
            startsCount += level.starts
        }


        /*

            12 -----> 3
            4 ------> ?


       */


        sharedManager.setInt(themeBean?.apiUrl, startsCount * 3 / (listLevels.size * 3))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LevelsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
            LevelsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}