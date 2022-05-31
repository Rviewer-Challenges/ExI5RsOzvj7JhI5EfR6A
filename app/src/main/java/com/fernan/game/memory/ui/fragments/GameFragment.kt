package com.fernan.game.memory.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fernan.game.memory.Constants
import com.fernan.game.memory.R
import com.fernan.game.memory.data.model.LevelBean
import com.fernan.game.memory.databinding.FragmentGameBinding
import com.fernan.game.memory.provider.Player
import com.fernan.game.memory.provider.SharedManager
import com.fernan.game.memory.ui.adapters.GameAdapter
import com.fernan.game.memory.ui.events.State
import com.fernan.game.memory.ui.fragments.data.BaseFragment
import com.fernan.game.memory.ui.fragments.data.FragmentListener
import com.fernan.game.memory.ui.fragments.data.FragmentName
import com.fernan.game.memory.utils.Utils
import com.fernan.game.memory.utils.Time
import com.google.gson.GsonBuilder


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragmentListen.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment(), BaseFragment {
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


    private lateinit var bin: FragmentGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bin = FragmentGameBinding.inflate(inflater, container, false)
        return bin.root
    }

    private lateinit var gameAdapter: GameAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var levelBean: LevelBean
    fun onSetViews(level: LevelBean) {
        this.levelBean = level
        Log.d("itemTesting", "isCallFragment")
        Log.d(
            "itemTestingLevel",
            "level ${GsonBuilder().disableHtmlEscaping().create().toJson(level)}"
        )

        // time
        bin.timeBarText.text = Time.getMinutes(level.time)


        // gridLayoutManager = GridLayoutManager(requireActivity(),2,RecyclerView.VERTICAL,false)


        gridLayoutManager = GridLayoutManager(
            activity,
            (level.files),
            RecyclerView.VERTICAL,
            false
        )
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        // Detecting is horinzon
        val isLand = requireActivity().resources.getBoolean(R.bool.isLand)
        val sizeWidht = (Utils.screenWidth(requireActivity()) - 200)
        val sizeHeight = (Utils.screenHeight(requireActivity()) - 200)
        var baseWidht = sizeWidht
        var baseHeight = sizeHeight
        if (!isLand) {
            baseWidht = sizeHeight
            baseHeight = sizeWidht
        }

        var sizeItem = baseWidht / level.files
        Log.d("TestingSize", "sizeItem init ${sizeItem}")

        // Cheking
        val row = level.listGame.size / level.files

        if ((sizeItem * row) > baseHeight) {
            sizeItem = baseHeight / level.files
        }

        Log.d("TestingSize", "screenHeight ${(Utils.screenHeight(requireActivity()) - 200)}")
        Log.d("TestingSize", "screenWidth ${(Utils.screenWidth(requireActivity()) - 200)}")

        Log.d("TestingSize", "sizeItem end ${sizeItem}")
        Log.d("TestingSize", "level.files ${level.files}")




        bin.gamesRecycler.layoutManager = gridLayoutManager

        gameAdapter = GameAdapter(level.listGame, requireActivity(), sizeItem, object : State {
            override fun onFoundAlls(sizeUserTaps: Int, sizeTotalTaps: Int) {
                val countX = sizeTotalTaps / 2
                val factor = levelBean.time / Constants.baseTime
                val scoreTime = timePast * factor
                when {
                    sizeUserTaps < (sizeTotalTaps + countX) -> {
                        levelBean.starts = 3
                    }
                    sizeUserTaps < (sizeTotalTaps * 2) -> {
                        levelBean.starts = 2
                    }
                    else -> {
                        levelBean.starts = 1
                    }

                }
                levelBean.score = scoreTime + (levelBean.starts * Constants.scoreXStar)


                isFoundAlls = true
                timer?.cancel()
                showDialog()
            }
        })
        bin.gamesRecycler.adapter = gameAdapter

        bin.gamesRecycler.setHasFixedSize(true)

        initClock(level.time)
    }

    private var timer: CountDownTimer? = null
    private fun initClock(time: Int) {
        Log.d("Timer", "time ${time}")


        var isRemainigInit = false
        timer = object : CountDownTimer((time * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = (millisUntilFinished / 1000).toInt()
                timePast++
                Log.d("Timer", "onTick " + timeRemaining)
                bin.timeBarText.text = Time.getMinutes(timeRemaining)
                if (timeRemaining == 10) {
                    if (!isRemainigInit) {
                        isRemainigInit = true
                        if (!isFoundAlls) {
                            Player.countdown(requireActivity(), R.raw.countdown_11)
                        }
                    }
                }

            }

            override fun onFinish() {
                showDialog()

            }
        }
        timer?.start()


    }

    var isFoundAlls = false
    var dialog: Dialog? = null
    var timePast = 0
    fun showDialog() {
        saveData()
        //if (dialog == null) {
            //     <style name="Theme.FerMemoryGame" parent="Theme.MaterialComponents.DayNight.NoActionBar">
            //      android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth
            dialog = Dialog(requireActivity()/*, android.R.style.Theme_Material_Dialog_MinWidth*/)
            dialog?.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                // set background transparent
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_game_finish)

                findViewById<TextView>(R.id.time).text = ("   ${Time.getMinutes(timePast)}")
                findViewById<TextView>(R.id.score).text = ("   ${levelBean.score}")
                findViewById<View>(R.id.start1).apply {
                    isVisible = true
                }
                val start2 = findViewById<View>(R.id.start2)
                val start3 = findViewById<View>(R.id.start3)

                when (levelBean.starts) {
                    3 -> {
                        start2.isVisible = true
                        start3.isVisible = true
                    }
                    2 -> {
                        start2.isVisible = true
                    }
                }

                findViewById<ImageView>(R.id.button_previous).setOnClickListener {
                    dismiss()
                    dialog = null
                    timer = null
                    timePast = 0
                    isFoundAlls = false
                    levelBean.listGame.forEach {
                        it.state = com.fernan.game.memory.data.model.State.HIDE
                    }
                    onSetViews(levelBean)
                }

                findViewById<ImageView>(R.id.button_next).setOnClickListener {
                    dismiss()
                    listen?.onChange(FragmentName.Levels)
                }


                show()
            }

        //}


    }

    fun stopTimer() {
        super.onPause()
        timer?.cancel()
    }


    private fun saveData() {
        val sharedManager = SharedManager(requireActivity())
        sharedManager.setInt(levelBean.id, levelBean.starts)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}