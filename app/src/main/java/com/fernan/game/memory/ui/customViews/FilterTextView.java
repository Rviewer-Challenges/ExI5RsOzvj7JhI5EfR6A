package com.fernan.game.memory.ui.customViews;

import android.content.Context;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.fernan.game.memory.R;

public class FilterTextView extends androidx.appcompat.widget.AppCompatTextView {
    private MaskFilter mFilter;

    public FilterTextView(Context context) {
        super(context);
        setTypeface(context);
        applyFilter();

    }

    public FilterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
        applyFilter();
    }

    public FilterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
        applyFilter();
    }

    public void updateFilter(float[] light, float ambient, float reflection, float blur) {
        this.mFilter = new EmbossMaskFilter(light, ambient, reflection, blur);
        this.getPaint().setMaskFilter(this.mFilter);
        invalidate();
    }

    private void applyFilter() {


        setSoftwareLayerType();

        this.mFilter = new EmbossMaskFilter(new float[] { 0f, -1f, 0.5f }, 0.5f, 15f, 1f);
        /*
        Example
        direction of the light source (x,y,z)   new float[]{1,5,1},
        ambient light between 0 to 1            0.5f,
        specular highlights                     10,
        blur before applying lighting           7.5f


        emboss      new float[] { 0f, 1f, 0.5f }, 0.8f, 3f, 3f
        deboss      new float[] { 0f, -1f, 0.5f }, 0.8f, 15f, 1f

         */
        this.getPaint().setMaskFilter(mFilter);
        // BlurMaskFilter filter = new BlurMaskFilter(2f,BlurMaskFilter.Blur.SOLID);
        // this.getPaint().setMaskFilter(filter);
        this.setGravity(Gravity.CENTER);
        this.setPadding(25,10,25,15);

        // invalidate();
    }

    /**

     EmbossMaskFilter no está disponible en la aceleración de hardware

     */
    private void setSoftwareLayerType() {
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void setTypeface(Context context)
    {
        /*
        Typeface tf = Typeface.createFromAsset(
                context.getAssets(),
                "font/dimbo.ttf");
         */
        final Typeface tf = ResourcesCompat.getFont(context, R.font.grobold);
        this.setTypeface(tf);
    }


}
