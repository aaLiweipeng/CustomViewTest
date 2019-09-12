package com.lwp.customviewtest.CustomViews;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.lwp.customviewtest.R;

public class LoadingImageView extends ImageView {

    private int mTop;

    //当前动画图片索引
    private int mCurImgIndex = 0;

    //动画图片总张数
    private static int mImgCount = 3;

    public LoadingImageView(Context context) {
        super(context);
        init();
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mTop = top;
    }

    private void init() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100, 0);
        valueAnimator.setRepeatCount(valueAnimator.RESTART);
        valueAnimator.setRepeatCount(valueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer dx = (Integer) animation.getAnimatedValue();
                setTop(mTop - dx);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setImageDrawable(getResources(). getDrawable(R.drawable.imagetest1));
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mCurImgIndex++;
                switch (mCurImgIndex % mImgCount) {
                    case 0:
                        setImageDrawable(getResources(). getDrawable(R.drawable.imagetest1));
                        break;

                    case 1:
                        setImageDrawable(getResources(). getDrawable(R.drawable.imagetest2));
                        break;

                    case 2:
                        setImageDrawable(getResources(). getDrawable(R.drawable.imagetest3));
                        break;
                }
            }
        });

        valueAnimator.start();
    }
}
