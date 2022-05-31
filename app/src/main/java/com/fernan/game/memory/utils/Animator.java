package com.fernan.game.memory.utils;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;

public class Animator {
    public static void Alpha(View view) {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f});
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }
    public static void GoOut(
            @NonNull Context context,
                        View mTitle,
                        View mStartButtonLights,
                        View mTooltip,
                        View mSettingsGameButton,
                        View mGooglePlayGameButton,
                        View mStartGameButton,
            AnimatorListenerAdapter adapter) {
        // title
        // 120dp + 50dp + buffer(30dp)
        ObjectAnimator titleAnimator = ObjectAnimator.ofFloat(mTitle, "translationY", Utils.px(context,-200));
        titleAnimator.setInterpolator(new AccelerateInterpolator(2));
        titleAnimator.setDuration(300);

        // lights
        ObjectAnimator lightsAnimatorX = ObjectAnimator.ofFloat(mStartButtonLights, "scaleX", 0f);
        ObjectAnimator lightsAnimatorY = ObjectAnimator.ofFloat(mStartButtonLights, "scaleY", 0f);

        // tooltip
        ObjectAnimator tooltipAnimator = ObjectAnimator.ofFloat(mTooltip, "alpha", 0f);
        tooltipAnimator.setDuration(100);

        // settings button
        ObjectAnimator settingsAnimator = ObjectAnimator.ofFloat(mSettingsGameButton, "translationY", Utils.px(context,120));
        settingsAnimator.setInterpolator(new AccelerateInterpolator(2));
        settingsAnimator.setDuration(300);

        // google play button
        ObjectAnimator googlePlayAnimator = ObjectAnimator.ofFloat(mGooglePlayGameButton, "translationY", Utils.px(context,120));
        googlePlayAnimator.setInterpolator(new AccelerateInterpolator(2));
        googlePlayAnimator.setDuration(300);

        // start button
        ObjectAnimator startButtonAnimator = ObjectAnimator.ofFloat(mStartGameButton, "translationY", Utils.px(context,130));
        startButtonAnimator.setInterpolator(new AccelerateInterpolator(2));
        startButtonAnimator.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(titleAnimator, lightsAnimatorX, lightsAnimatorY, tooltipAnimator, settingsAnimator, googlePlayAnimator, startButtonAnimator);
        animatorSet.addListener(adapter);
        animatorSet.start();
    }

    public static void GoInto(
            @NonNull Context context,
            View mTitle,
            View mStartButtonLights,
            View mTooltip,
            View mSettingsGameButton,
            View mGooglePlayGameButton,
            View mStartGameButton,
            AnimatorListenerAdapter adapter) {
        // title
        // 120dp + 50dp + buffer(30dp)
        ObjectAnimator titleAnimator = ObjectAnimator.ofFloat(mTitle, "translationY", Utils.px(context,0));
        titleAnimator.setInterpolator(new AccelerateInterpolator(2));
        titleAnimator.setDuration(300);

        // lights
        ObjectAnimator lightsAnimatorX = ObjectAnimator.ofFloat(mStartButtonLights, "scaleX", 1f);
        ObjectAnimator lightsAnimatorY = ObjectAnimator.ofFloat(mStartButtonLights, "scaleY", 1f);

        // tooltip
        ObjectAnimator tooltipAnimator = ObjectAnimator.ofFloat(mTooltip, "alpha", 1f);
        tooltipAnimator.setDuration(100);

        // settings button
        ObjectAnimator settingsAnimator = ObjectAnimator.ofFloat(mSettingsGameButton, "translationY", 0);
        settingsAnimator.setInterpolator(new AccelerateInterpolator(2));
        settingsAnimator.setDuration(300);

        // google play button
        ObjectAnimator googlePlayAnimator = ObjectAnimator.ofFloat(mGooglePlayGameButton, "translationY", 0);
        googlePlayAnimator.setInterpolator(new AccelerateInterpolator(2));
        googlePlayAnimator.setDuration(300);

        // start button
        ObjectAnimator startButtonAnimator = ObjectAnimator.ofFloat(mStartGameButton, "translationY",0);
        startButtonAnimator.setInterpolator(new AccelerateInterpolator(2));
        startButtonAnimator.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(titleAnimator, lightsAnimatorX, lightsAnimatorY, tooltipAnimator, settingsAnimator, googlePlayAnimator, startButtonAnimator);
        animatorSet.addListener(adapter);
        animatorSet.start();
    }

    public static void animateShow(View view) {
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(animatorScaleX, animatorScaleY);
        animatorSet.setInterpolator(new DecelerateInterpolator(2));
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();
    }
}
