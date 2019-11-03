package com.lwp.customviewtest.CustomViews;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lwp.customviewtest.R;
import com.lwp.customviewtest.Utils.FallingBallEvaluator;

public class FallingBallActivity extends AppCompatActivity {

    private Button startAnim;
    private ImageView ballImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falling_ball);

        initViews();
    }

    private void initViews() {
        startAnim = findViewById(R.id.start_fallingBallAnim);
        ballImg = findViewById(R.id.ball_img);

        startAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //设置自定义Evaluator， 以及初始值终止值（皆为Point类型）
                ValueAnimator animator = ValueAnimator.ofObject(new FallingBallEvaluator(),
                        new Point(0, 0), new Point(500, 500));

                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Point mCurPoint = (Point) animation.getAnimatedValue();

                        //以函数x，y值为左上坐标，加以自身宽高为右下坐标
                        ballImg.layout(mCurPoint.x, mCurPoint.y, mCurPoint.x + ballImg.getWidth(),
                                mCurPoint.y + ballImg.getHeight());
                    }
                });
                animator.setDuration(2000);
                animator.start();
            }
        });
    }
}
