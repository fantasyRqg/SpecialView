package bong.ginshell.com.myapplication;

import android.animation.LayoutTransition;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

import bong.ginshell.com.myapplication.databinding.ActivityMain2Binding;
import bong.ginshell.com.myapplication.drawable.BreathDrawable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ActivityMain2Binding main2Binding;

    boolean isChangeed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);

        ViewStub viewStub = main2Binding.viewStub.getViewStub();

        viewStub.setLayoutResource(R.layout.activity_nps);
        View inflate = viewStub.inflate();


        final Rect out = new Rect();

        LayoutTransition layoutTransition = main2Binding.mainViewgroup.getLayoutTransition();
        layoutTransition.setDuration(2000);
        Log.d(TAG, "onCreate: " + layoutTransition);
        layoutTransition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                Log.d(TAG, "startTransition() called with: transition = [" + transition + "], container = [" + container + "], view = [" + view + "], transitionType = [" + transitionType + "]");
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                Log.d(TAG, "endTransition() called with: transition = [" + transition + "], container = [" + container + "], view = [" + view + "], transitionType = [" + transitionType + "]");
            }
        });



        main2Binding.mainViewgroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        main2Binding.mainViewgroup.getWindowVisibleDisplayFrame(out);
                        Log.d(TAG, "onGlobalLayout: " + out);

                        if (out.bottom != 1280 && !isChangeed) {
                            isChangeed = true;

                            Log.d(TAG, "onGlobalLayout: isChangeed");
                            main2Binding.mainViewgroup.removeView(main2Binding.title);

//                            Animation animation = new Animation() {
//                                @Override
//                                protected void applyTransformation(float interpolatedTime, Transformation t) {
//                                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) main2Binding.title.getLayoutParams();
//                                    params.topMargin = (int) (params.topMargin * (1f - interpolatedTime));
////                        params.baselineToBaseline = main2Binding.left.getId();
//                                    main2Binding.title.setLayoutParams(params);
//                                }
//                            };
//
//                            animation.setDuration(500);
//
//                            main2Binding.title.startAnimation(animation);

                        }
                    }
                }
        );

//        main2Binding.left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });


    }


    public void buttonClick(View view) {
        LoadingDialog dialog = new LoadingDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }
}
