package com.lwp.customviewtest.CustomViews;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.lwp.customviewtest.R;
import com.lwp.customviewtest.Utils.CharEvaluator;
import com.lwp.customviewtest.Utils.MyEvaluator;
import com.lwp.customviewtest.Utils.MyInterpolator;

public class ValueAnimatorTestView extends AppCompatActivity {

    private Button btn_start_animation;

    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator_test_view);

        btn_start_animation = findViewById(R.id.btn_start_animation);
        tv_text = findViewById(R.id.tv_text);

        btn_start_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAnimation();
//                startAnimationArgbEvaluator();
//                startAnimationTestInterpolator();
                startAnimationTestCharEvaluator();
            }
        });
    }

    private void startAnimationTestCharEvaluator() {
        ValueAnimator animator = ValueAnimator.ofObject(new CharEvaluator(), 'A', 'Z');

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char text = (Character) animation.getAnimatedValue();
                tv_text.setText(String.valueOf(text));
            }
        });

        animator.setDuration(10000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void startAnimationTestInterpolator() {
        ValueAnimator animator =  ValueAnimator.ofInt(0, 400);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                tv_text.layout(tv_text.getLeft(),curValue,
                        tv_text.getRight(), curValue+tv_text.getHeight());
            }
        });

        animator.setDuration(1000);
        animator.setInterpolator(new MyInterpolator());
        animator.start();
    }


    private void startAnimationArgbEvaluator() {
        ValueAnimator animator =  ValueAnimator.ofInt(0xffffff00, 0xff0000ff);
        animator.setEvaluator(new ArgbEvaluator());//设置Evaluator
        animator.setDuration(3000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (Integer) animation.getAnimatedValue();
                tv_text.setBackgroundColor(curValue);
            }
        });
        animator.start();
    }

    private void startAnimation() {

        ValueAnimator animator =  ValueAnimator.ofInt(0, 400);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                tv_text.layout(tv_text.getLeft(),curValue,
                        tv_text.getRight(), curValue+tv_text.getHeight());
            }
        });

        animator.setDuration(1000);
        animator.setEvaluator(new MyEvaluator());
        animator.start();
    }

}
