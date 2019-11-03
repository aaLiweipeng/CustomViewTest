package com.lwp.customviewtest.Utils;

import android.animation.TypeEvaluator;
import android.graphics.Point;

public class FallingBallEvaluator implements TypeEvaluator<Point> {

    private Point point = new Point();

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        point.x = (int) (startValue.x + fraction * (endValue.x - startValue.x));

        //y为二倍速
        if (fraction * 2 <= 1) {

            //y二倍速，若未完成，继续二倍刷新
            point.y = (int) (startValue.y + fraction * 2 * (endValue.y - startValue.y));
        } else {
            //如果完成，则不变
            point.y = endValue.y;
        }

        return point;
    }
}
