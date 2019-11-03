package com.lwp.customviewtest.Utils;

import android.animation.TimeInterpolator;

public class MyInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float input) {
        return 1 - input;
    }
}
