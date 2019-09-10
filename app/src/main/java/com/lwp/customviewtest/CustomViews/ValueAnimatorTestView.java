package com.lwp.customviewtest.CustomViews;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lwp.customviewtest.R;
import com.lwp.customviewtest.Utils.MyEvaluator;

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
                startAnimationA();
            }
        });
    }

    private void startAnimationA() {
        ValueAnimator animator =  ValueAnimator.ofInt(0xffffff00, 0xff0000ff);
        animator.setEvaluator(new ArgbEvaluator());
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
