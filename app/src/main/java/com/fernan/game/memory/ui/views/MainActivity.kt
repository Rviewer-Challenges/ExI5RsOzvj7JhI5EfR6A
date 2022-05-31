package com.fernan.game.memory.ui.views

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fernan.game.memory.R
import com.fernan.game.memory.data.model.GameBean
import com.fernan.game.memory.data.model.LevelBean
import com.fernan.game.memory.data.model.ThemeBean
import com.fernan.game.memory.databinding.ActivityMainBinding
import com.fernan.game.memory.ui.fragments.*
import com.fernan.game.memory.ui.fragments.data.FragmentListener
import com.fernan.game.memory.ui.fragments.data.FragmentName
import com.fernan.game.memory.ui.viewmodels.MainViewModel
import com.fernan.game.memory.utils.Utils.loadFrom
import com.fernan.game.memory.utils.Utils.loadGifFromAsset
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var bin : ActivityMainBinding

    // Fragments
    private lateinit var menuFragment : MenuFragment
    private lateinit var themeFragment: ThemeFragment
    private lateinit var levelFragment: LevelsFragment
    private lateinit var gameFragment: GameFragment

    private var fragmentName : FragmentName = FragmentName.Menu

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var themeList : ArrayList<ThemeBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bin.root)



        // background
        bin.imageBackground.loadFrom(R.drawable.background)
        bin.loading.loadGifFromAsset("loading_flash")

        mainViewModel.onCreate()
        mainViewModel.themeListModel.observe(this, Observer {
            themeList = it as ArrayList<ThemeBean>
        })
        mainViewModel.isLoadingModel.observe(this,Observer{
            if(!it){
                bin.loading.isVisible = false
                bin.fragmentLayout.isVisible = true
                bin.imageBack.isVisible = true

                initViews()
                initListeners()
                initActions()
                Log.d("mirarList",Gson().toJson(themeList))
            }
        })


    }



    private fun initViews(){

        menuFragment = MenuFragment.newInstance()
        themeFragment = ThemeFragment.newInstance(themeList)
        levelFragment  = LevelsFragment.newInstance()
        gameFragment  = GameFragment.newInstance()
        // onlyFirts
        changeFragment(menuFragment,fragmentName = fragmentName);


    }
    private fun initListeners(){
        menuFragment.setListener(object : FragmentListener{
            override fun onChange(name: FragmentName, any: Any?) {
                //menuFragment.resumeAnim()
                changeFragment(fragment = themeFragment,fragmentName = name)
            }
        })
        themeFragment.setListener(object : FragmentListener{
            override fun onChange(name: FragmentName, any: Any?) {

                val themeBean :ThemeBean= any as ThemeBean
                Glide.with(this@MainActivity)
                    .load(themeBean.background)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(2000))
                    .placeholder(R.drawable.background)
                    .into(bin.imageBackground)

                bin.loading.isVisible = true

                var gameBeanList : MutableList<GameBean>
                mainViewModel.getThemeImages(themeBean.apiUrl)
                mainViewModel.themeImagesModel.observe(this@MainActivity,Observer{
                    gameBeanList = it as MutableList<GameBean>
                    bin.loading.isVisible = false

                    changeFragment(fragment = levelFragment,fragmentName =name)
                    bin.imageBackgroundGradient.isVisible = true

                    Log.d("mirando",Gson().toJson(gameBeanList))
                    if(gameBeanList.isEmpty()){
                        return@Observer
                    }
                    levelFragment.onSetViews(themeBean,gameBeanList)
                })


            }
        })



        levelFragment.setListener(object : FragmentListener{
            override fun onChange(name: FragmentName, any: Any?) {
                changeFragment(fragment = gameFragment,fragmentName =name)
                bin.imageBackgroundGradient.isVisible = true
                gameFragment.onSetViews(any as LevelBean)
            }
        })
        gameFragment.setListener(object : FragmentListener{
            override fun onChange(name: FragmentName, any: Any?) {
                changeFragment(levelFragment,fragmentName =name)
                levelFragment.onFromGameFragment()
            }
        })
    }
    private fun initActions(){
        bin.imageBack.setOnClickListener{
            showFragment()
        }
    }


    private fun showFragment(){
        when(fragmentName){
            FragmentName.Menu -> {
                MaterialAlertDialogBuilder(this@MainActivity, com.fernan.game.memory.R.style.AlertDialogTheme)
                    .setTitle("")
                    .setMessage("Seguro que quieres salir")
                    .setPositiveButton("No") {
                            dialogInterface, i -> dialogInterface.dismiss() }
                    .setNeutralButton(
                        "Si"
                    ) { dialogInterface, i -> finish() }
                    .show()
            }

            FragmentName.Theme -> {
                changeFragment(menuFragment,FragmentName.Menu)
                menuFragment.resumeAnim()

            }
            FragmentName.Levels -> {
                changeFragment(themeFragment,FragmentName.Theme)
            }
            FragmentName.Game -> {
                gameFragment.stopTimer()
                changeFragment(levelFragment,FragmentName.Levels)
                levelFragment.onFromGameFragment()


            }
        }

    }
    private fun changeFragment(fragment: Fragment, fragmentName:FragmentName) {
        this.fragmentName = fragmentName
        val nameFragment: String =  /*name.ifEmpty { */fragment.javaClass.simpleName /*}*/

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val currentFragment = fragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }
        var fragmentTemp = fragmentManager.findFragmentByTag(nameFragment)
        if (fragmentTemp == null) {
            fragmentTemp = fragment
            fragmentTransaction.add(com.fernan.game.memory.R.id.fragmentLayout, fragmentTemp, nameFragment)
        } else {
            fragmentTransaction.show(fragmentTemp)
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
    }


    override fun onBackPressed() {
        showFragment()
    }
}