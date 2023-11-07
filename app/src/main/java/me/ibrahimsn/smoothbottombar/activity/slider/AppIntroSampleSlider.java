package me.ibrahimsn.smoothbottombar.activity.slider;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class AppIntroSampleSlider extends androidx.fragment.app.Fragment {
    //Layout id
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static androidx.fragment.app.Fragment newInstance(int layoutResId) {
        AppIntroSampleSlider sampleSlide = new AppIntroSampleSlider();

        Bundle bundleArgs = new Bundle();
        bundleArgs.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(bundleArgs);

        return sampleSlide;
    }

    private int layoutResId;

    public AppIntroSampleSlider() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }

}
