package com.lwp.customviewtest.Utils;

import android.animation.TypeEvaluator;

public class MyEvaluator implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (200 + startInt + fraction * (endValue - startInt));
    }
}
