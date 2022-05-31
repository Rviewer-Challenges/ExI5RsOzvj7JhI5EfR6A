package com.fernan.game.memory.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernan.game.memory.data.model.GameBean
import com.fernan.game.memory.data.model.ThemeBean
import com.fernan.game.memory.domain.GetUseAllThemes
import com.fernan.game.memory.domain.GetUseThemeImages
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val themeListModel = MutableLiveData<List<ThemeBean>>()
    val isLoadingModel = MutableLiveData<Boolean>()

    private var getUseAllThemes = GetUseAllThemes()


    fun onCreate() {
        viewModelScope.launch {
            isLoadingModel.postValue(true)
            val result = getUseAllThemes()

            if(result.isNotEmpty()){
                themeListModel.postValue(result)
                isLoadingModel.postValue(false)
            }
        }
    }

    val themeImagesModel = MutableLiveData<List<GameBean>>()


    fun getThemeImages(url:String){
        viewModelScope.launch {
            val result = GetUseThemeImages(url).invoke()
            if(result.isNotEmpty()){
                themeImagesModel.postValue(result)
            } else {
                themeImagesModel.postValue(mutableListOf())

            }
        }
    }


}