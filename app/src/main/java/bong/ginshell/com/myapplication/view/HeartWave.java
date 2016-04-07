package bong.ginshell.com.myapplication.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.TimeUnit;

import bong.ginshell.com.myapplication.R;

/**
 * @author rqg
 * @date 1/20/16.
 */
public class HeartWave extends View {
    public final static String TAG = HeartWave.class.getSimpleName();

    private Paint mWavePaint;
    private int mWaveColor = Color.RED;

    private float mWidth, mHeight;

    private float mCurrPos = 0f;

    private String mHeartWave;

    private AnimatorSet mAnimatorSet = new AnimatorSet();

    private Bitmap mBitmap;

    public HeartWave(Context context) {
        super(context);
    }

    public HeartWave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeartWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initPaint(int width) {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "icomoon.ttf");
        mHeartWave = getContext().getString(R.string.icon_heart_rate_curve);
        mHeartWave = mHeartWave + mHeartWave;

        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setTextSize(width);
        mWavePaint.setTextAlign(Paint.Align.LEFT);
        mWavePaint.setColor(mWaveColor);
        mWavePaint.setTypeface(tf);


        mBitmap = Bitmap.createBitmap((int) mWidth * 2, (int) mHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mBitmap);

        c.drawText(mHeartWave, 0, mHeight, mWavePaint);
        startAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec);

        float perW = width / 3f;
        float perH = height / 2f;
        float per = Math.min(perH, perW);
        width = (int) (per * 3);
        height = (int) (per * 2);


        mWidth = width;
        mHeight = height;

        initPaint(width);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mCurrPos, 0, mWavePaint);
    }

    public float getMCurrPos() {
        return mCurrPos;
    }

    int mFrameCount = 0;

    public void setMCurrPos(float cp) {
        if (mCurrPos != cp) {
            mCurrPos = cp;
            if (mFrameCount % 2 == 0) {
                invalidate();
            }
        }
        mFrameCount++;
    }

    public void startAnimation() {

        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }

        ObjectAnimator a1 = ObjectAnimator.ofFloat(this, "mCurrPos", -2f * mWidth, -mWidth);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(this, "mCurrPos", -mWidth, 0f);

        a2.setRepeatMode(ObjectAnimator.RESTART);
        a2.setRepeatCount(ObjectAnimator.INFINITE);

//        a1.setInterpolator(new TimeInterpolator() {
//            @Override
//            public float getInterpolation(float input) {
//                return input;
//            }
//        });
//        a2.setInterpolator(new TimeInterpolator() {
//            @Override
//            public float getInterpolation(float input) {
//                return input;
//            }
//        });
//
//        a2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.d(TAG, "onAnimationUpdate " + animation.getAnimatedValue());
//            }
//        });

        a1.setDuration(TimeUnit.SECONDS.toMillis(2));
        a2.setDuration(TimeUnit.SECONDS.toMillis(2));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(a1, a2);

//        animatorSet.play(a2);
//
        animatorSet.start();
    }


}
