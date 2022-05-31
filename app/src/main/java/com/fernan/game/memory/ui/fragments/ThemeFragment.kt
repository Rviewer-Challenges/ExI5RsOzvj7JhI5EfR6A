package com.fernan.game.memory.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fernan.game.memory.data.model.ThemeBean
import com.fernan.game.memory.databinding.FragmentThemeBinding
import com.fernan.game.memory.provider.SharedManager
import com.fernan.game.memory.ui.adapters.ThemeAdapter
import com.fernan.game.memory.ui.fragments.data.BaseFragment
import com.fernan.game.memory.ui.fragments.data.FragmentListener
import com.fernan.game.memory.ui.fragments.data.FragmentName
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThemeFragmentListen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThemeFragment : Fragment(), BaseFragment {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var adapter: ThemeAdapter? = null

    private var themeList: ArrayList<ThemeBean>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            themeList = it.getParcelableArrayList(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override var listen: FragmentListener? = null


    private lateinit var bin: FragmentThemeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentThemeBinding.inflate(inflater, container, false)
        initViews()
        initActions()
        return bin.root
    }

    private fun initViews() {
        val sharedManager = SharedManager(requireActivity())
        adapter = (if (themeList == null) emptyList() else {
            themeList?.onEach {
                if (sharedManager.contains(it.apiUrl)) {
                    it.starts = sharedManager.getInt(it.apiUrl)
                }
            }

            //themeList
        })?.let { ThemeAdapter(it) }
        bin.gridView.adapter = adapter
        adapter?.setListener(object : ThemeAdapter.onListen {
            override fun onClicled(position: Int, themeBean: ThemeBean) {
                listen?.onChange(FragmentName.Levels, themeBean)

            }

        })

    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    private fun initActions() {


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThemeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(list: ArrayList<ThemeBean>, param2: String = "") =
            ThemeFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM1, list)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}