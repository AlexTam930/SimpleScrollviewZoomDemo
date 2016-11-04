package com.alextam.simplescrollviewzoomdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alextam.simplescrollviewzoomdemo.widgets.CustomScrollView;

/**
 * @author AlexTam
 */
public class MainActivity extends AppCompatActivity
    implements CustomScrollView.ScrollViewListener,
        View.OnTouchListener{

    // scrollView
    private CustomScrollView scl_body;
    private LinearLayout ll_frag_video_course_details_root;
    private FrameLayout fly_container_act_main;

    private float minHeightPlayer;
    private float maxHeightPlayer;
    private float scrollHeightPlayer;

    private float downY;
    private float scrollScale = 1.0f;

    private boolean ifZoom = true;
    private boolean ifScrollTop = true;
    private boolean ifScrollDownTouch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scl_body = (CustomScrollView) findViewById(R.id.scl_act_main);
        fly_container_act_main = (FrameLayout) findViewById(R.id.fly_container_act_main);

        scl_body.setScrollViewListener(this);
        scl_body.setOnTouchListener(this);
    }


    @Override
    public void onScrollChanged(CustomScrollView scrollViewExt, int l, int t, int oldl, int oldt) {
        ifScrollTop = t <= 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ViewGroup.LayoutParams lp = fly_container_act_main.getLayoutParams();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                ifScrollDownTouch = true;

                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY() - downY;
                if (ifScrollTop && ifScrollDownTouch) {
                    if (scrollHeightPlayer <= minHeightPlayer) {
                        if (moveY <= 0) {
                            // 上滑
                            ifZoom = false;
                            return false;

                        } else {
                            // 下滑
                            downY = event.getY();
                            // 3
                            scrollHeightPlayer = 3 * scrollScale + scrollHeightPlayer;
                            lp.height = (int) scrollHeightPlayer;
                            fly_container_act_main.setLayoutParams(lp);

                            ifZoom = true;
                            return true;
                        }

                    } else if (scrollHeightPlayer > minHeightPlayer
                            && scrollHeightPlayer < maxHeightPlayer) {
                        if (moveY <= 0) {
                            // 上滑
                            float scrollHeightY = moveY * scrollScale + scrollHeightPlayer;
                            if (scrollHeightY < minHeightPlayer) {
                                scrollHeightY = minHeightPlayer;
                            }
                            scrollHeightPlayer = scrollHeightY;
                            lp.height = (int) scrollHeightPlayer;
                            fly_container_act_main.setLayoutParams(lp);

                            ifZoom = true;
                            return true;

                        } else {
                            // 下滑
                            float scrollHeightY = moveY * scrollScale + scrollHeightPlayer;
                            if (scrollHeightY > maxHeightPlayer) {
                                scrollHeightY = maxHeightPlayer;
                            }
                            scrollHeightPlayer = scrollHeightY;
                            lp.height = (int) scrollHeightPlayer;
                            fly_container_act_main.setLayoutParams(lp);

                            ifZoom = true;
                            return true;
                        }

                    } else {
                        if (moveY <= 0) {
                            float scrollHeightY = moveY * scrollScale + scrollHeightPlayer;
                            if (scrollHeightY < minHeightPlayer) {
                                scrollHeightY = minHeightPlayer;
                            }
                            scrollHeightPlayer = scrollHeightY;
                            lp.height = (int) scrollHeightPlayer;
                            fly_container_act_main.setLayoutParams(lp);

                            ifZoom = true;
                            return true;
                        } else {
                            downY = event.getY();
                            ifZoom = true;
                            return true;
                        }
                    }

                } else {
                    ifZoom = false;
                    return false;
                }


            case MotionEvent.ACTION_UP:
                ifScrollDownTouch = false;
                break;
        }


        if (ifZoom) {
            return true;
        } else {
            return false;
        }
    }


}
