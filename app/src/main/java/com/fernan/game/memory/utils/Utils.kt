package com.fernan.game.memory.utils

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fernan.game.memory.R
import java.util.*


object Utils {
    @JvmStatic
    fun px(context: Context, dp: Int): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

    fun screenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun screenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun View.setSizeToDp(height: Int, width: Int){
        apply {
            if (layoutParams != null) {
                val p = layoutParams
                p.height = dpToPx(context.resources, height.toFloat()).toInt()
                p.width = dpToPx(context.resources, width.toFloat()).toInt()
                layoutParams = p
                requestLayout()
            }
        }
    }
    fun View.setSize(height: Int, width: Int){
        apply {
            if (layoutParams != null) {
                val p = layoutParams
                p.height = height
                p.width = width
                layoutParams = p
                requestLayout()
            }
        }
    }

    fun dpToPx(resources: Resources, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }


    fun ImageView.loadGifFromAsset(name:String){
        Glide.with(this).asGif().load("file:///android_asset/${name}.gif")
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }

    fun ImageView.loadFrom(@NonNull @DrawableRes drawable:Int){
        Glide.with(this).load(drawable).centerCrop().into(this)

    }
    fun ImageView.loadFrom(url:String, wight:Int=250, height: Int=235){
        if(url.isNotEmpty()){
            val thumbnail: RequestBuilder<Drawable> = Glide.with(this.context)
                .asDrawable().sizeMultiplier(0.1f)
            Glide.with(this.context)
                .load(url)
                .thumbnail(thumbnail)
                .apply(
                    RequestOptions().override(
                        wight,height))
                .into(this)
        }


    }



    fun getRandomColor(){
        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
    //
    fun showDialogWorking(context: Context){
        Dialog(context).apply{
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            // set background transparent
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_progress)


            findViewById<ImageView>(R.id.baseBackground).apply {
                loadGifFromAsset("working_300")
            }
            this.setOnCancelListener{
                dismiss()
            }
            show()
        }





    }


    // Extra Logger

    fun String.printLog(name: String){
        Log.d(name, this)
    }
    fun Int.printLog(name: String){
        Log.d(name,this.toString())
    }
}