package com.fernan.game.memory.data.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThemeBean (
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("background") val background: String,
    @SerializedName("apiUrl") val apiUrl: String,
    var starts : Int = 0,
    val timeBest : String = ""
): Parcelable