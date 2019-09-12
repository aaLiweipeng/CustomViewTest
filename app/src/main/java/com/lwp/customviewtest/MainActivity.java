package com.lwp.customviewtest;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lwp.customviewtest.CustomViews.CanvasTestView;
import com.lwp.customviewtest.CustomViews.ClipRgnView;
import com.lwp.customviewtest.CustomViews.CustomCircleView;
import com.lwp.customviewtest.CustomViews.LoadingImageView;
import com.lwp.customviewtest.CustomViews.SpiderView;
import com.lwp.customviewtest.CustomViews.ValueAnimatorTestView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_nextParent;
    private LinearLayout.LayoutParams layoutParams;

    private CanvasTestView canvasTestView;
    private int canvasDrawId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件和点击事件
        initViews();

        //为了方便调试，定义此方法，输入不同的id，显示不同的自定义View
        configCustomViews(6);
    }

    private void initViews() {

        canvasDrawId = 0;

        ll_nextParent = findViewById(R.id.ll_nextParent);

        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


    }


    private void configCustomViews(int drawId) {
        switch (drawId) {
            case 0:
                SpiderView spiderViewOri = new SpiderView(this);
                ll_nextParent.addView(spiderViewOri, layoutParams);
                break;

            case 1:
                canvasTestView = new CanvasTestView(this);
                ll_nextParent.addView(canvasTestView, layoutParams);
                break;

            case 2:
                CustomCircleView customCircleView = new CustomCircleView(this);
                ll_nextParent.addView(customCircleView, layoutParams);
                break;

            case 3:
                DisplayMetrics outMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                int widthPixels = outMetrics.widthPixels;
                int heightPixels = outMetrics.heightPixels;

                final ClipRgnView clipRgnView = new ClipRgnView(this);
                clipRgnView.setDecodeSize(300, 400);

                clipRgnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clipRgnView.clipWidth = 0;
                        clipRgnView.reDraw();
                    }
                });

                ll_nextParent.addView(clipRgnView, layoutParams);
                break;

            case 4:
                Button toTest = new Button(MainActivity.this);
                toTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ValueAnimatorTestView.class);
                        startActivity(intent);
                    }
                });
                ll_nextParent.addView(toTest, layoutParams);
                break;

            case 5:
                LoadingImageView loadingImageView = new LoadingImageView(this);

                ll_nextParent.addView(loadingImageView, layoutParams);
                break;

            case 6:
                ImageView cameraStretchView = new ImageView(this);
                cameraStretchView.setBackgroundResource(R.drawable.imagetest1);
                ll_nextParent.addView(cameraStretchView, layoutParams);

                ScaleAnimation scaleAnimation = getTheCameraStretchAnim();
                cameraStretchView.startAnimation(scaleAnimation);

                break;

            default:
        }
    }

    private ScaleAnimation getTheCameraStretchAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new BounceInterpolator());
        scaleAnimation.setDuration(6000);
        return scaleAnimation;
    }

    //这个方法在ll_nextParent.setOnClickListener的onClick中调用
    private void testTheAddViewsAndTheRemoveView() {
        //经过这个测试，这里的删除添加是毫秒级别的，
        // 添加之后删除的速度人眼分辨不出

//                Toast.makeText(MainActivity.this, "shanchu", Toast.LENGTH_SHORT).show();
//                ll_nextParent.removeView(spiderViewOri);
//
////                TextView test = new TextView(MainActivity.this);
////                test.setText("sdadasdasdada");
////                ll_nextParent.addView(test);
//
//                Toast.makeText(MainActivity.this, "tianjia", Toast.LENGTH_SHORT).show();
//                ll_nextParent.addView(spiderViewOri, layoutParams);
    }

    //这个方法加在initView中，测试canvas的存取
    private void testTheCanvas() {
        ll_nextParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasTestView.setDrawId(canvasDrawId);
                canvasDrawId++;
            }
        });

        ll_nextParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                canvasDrawId = 0;
                canvasTestView.setDrawId(canvasDrawId);
                return false;
            }
        });
    }

}
